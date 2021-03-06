package cfcc.com.shouChi.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cfcc.com.shouChi.Myappcation;
import cfcc.com.shouChi.R;
import cfcc.com.shouChi.adapter.BoxRecyclerAdapter;
import cfcc.com.shouChi.api.RecyclerViewLongClick;
import cfcc.com.shouChi.base.Basepresenter;
import cfcc.com.shouChi.base.MvpBaseActivity;
import cfcc.com.shouChi.db.BaoCun;
import cfcc.com.shouChi.db.BaoCunDao;
import cfcc.com.shouChi.db.QuanBie;
import cfcc.com.shouChi.db.QuanBieDao;
import cfcc.com.shouChi.utlis.DividerItemDecoration;
import cfcc.com.shouChi.utlis.MyToolbar;
import cfcc.com.shouChi.utlis.MyUtlis;

/**
 * Created by acer on 2018/1/12.
 * 我在这里加了一条注释
 */

public class BoxActivity extends MvpBaseActivity {
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.sp_quanbie)
    Spinner spQuanbie;
    @Bind(R.id.sp_bizhong)
    Spinner spBizhong;
    @Bind(R.id.et_zhouzhuanxiang)
    EditText etZhouzhuanxiang;
    @Bind(R.id.bt_add)
    Button btAdd;
    @Bind(R.id.rc_box)
    RecyclerView rcBox;
    private String[] city = {"未清分流通券", "未复点残损券"};
    private String scashclassname;
    private String sorderkind;
    private List<BaoCun> elist;
    private List<BaoCun> alist;
    private String scashclasscode;
    //币种属性
    private String bzsx;
    private String ip;
    private QuanBieDao quanBieDao;
    private BaoCunDao baoCunDao;
    private String sorderno;
    private String sbinlog;
    private String sbinlogicalid;
    private BoxRecyclerAdapter adapter;
    private List<BaoCun> mlist;
    private ArrayList<BaoCun> objects = new ArrayList<>();

    @Override
    public int getLayoutID() {

        return R.layout.box_activity;
    }

    @Override
    public Basepresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quanBieDao = Myappcation.getInstance().getSession().getQuanBieDao();
        baoCunDao = Myappcation.getInstance().getSession().getBaoCunDao();
        Intent intent = getIntent();
        sorderno = intent.getStringExtra("sorderno");
        /**
         * 设置Toobar
         */
        setToobar();
        /**
         * 查询数据库给spinner放值
         * 并且spinner适配
         */
        SpinnerAdapter();
        /**
         * 查询本地数据库,有值就展示
         */
        getDao();
        etZhouzhuanxiang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = String.valueOf(editable);
                if (s.length() > 19) {
                    String edittext = s.substring(0, 19);
                    etZhouzhuanxiang.setText(edittext);
                }
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim2 = etZhouzhuanxiang.getText().toString().trim();

                if (scashclassname.equals("- - 未选择 - -")) {
                    MyUtlis.showToast(BoxActivity.this, "请选择券别");
                    return;
                }

                if (trim2.equals("")) {
                    MyUtlis.showToast(BoxActivity.this, "周转箱编号不为空");
                    return;
                } else {
                    sbinlogicalid = trim2.substring(1);
                }
                if (sbinlogicalid.length() != 18) {
                    MyUtlis.showToast(BoxActivity.this, "周转箱编号必须为18位");
                    return;
                }
                /**
                 *查询本地库，判断是否已经有该周转箱号
                 */
//                isDbItemChongfu();
                /**
                 *判断同样的周转箱编号是否已经添加过了
                 */
                isItemChongfu();
                /**
                 *适配
                 */
                if (sbinlogicalid != ""){
                    if (mlist.size() != 0) {
                        BaoCun baoCun = new BaoCun(sbinlogicalid, scashclasscode, sorderkind, scashclassname, bzsx, sorderno);
                        mlist.add(baoCun);
                        final BoxRecyclerAdapter adapter = new BoxRecyclerAdapter(BoxActivity.this, mlist);
                        setEt();
                        rcBox.setAdapter(adapter);
                        rcBox.addItemDecoration(new DividerItemDecoration(BoxActivity.this, DividerItemDecoration.VERTICAL_LIST));
                        adapter.setOnLongClickListener(new RecyclerViewLongClick() {
                            @Override
                            public void ItemLongClick(View view, final int position) {
                                mlist.remove(position);
                                adapter.notifyDataSetChanged();
                                int i = position + 1;
                                MyUtlis.showToast(BoxActivity.this, "第" + i + "条删除成功");
                            }
                        });
                    } else {
                        initAdapter();
                    }
                }


            }


        });
    }

    private void getDao() {
        mlist = baoCunDao.queryBuilder().where(BaoCunDao.Properties.Sorderno.eq(sorderno)).list();
        if (mlist.size() != 0) {
            final BoxRecyclerAdapter adapter = new BoxRecyclerAdapter(BoxActivity.this, mlist);
            rcBox.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(BoxActivity.this, LinearLayoutManager.VERTICAL, false);
            rcBox.setLayoutManager(manager);
            rcBox.addItemDecoration(new DividerItemDecoration(BoxActivity.this, DividerItemDecoration.VERTICAL_LIST));
            adapter.setOnLongClickListener(new RecyclerViewLongClick() {
                @Override
                public void ItemLongClick(View view, final int position) {
                    mlist.remove(position);
                    adapter.notifyDataSetChanged();
                    int i = position + 1;
                    MyUtlis.showToast(BoxActivity.this, "第" + i + "条删除成功");
                }
            });
        }
    }
///**
// * 判断本地库中是否有相同的周转箱编号
// */
//    private void isDbItemChongfu() {
//        List<BaoCun> list = baoCunDao.queryBuilder().where(BaoCunDao.Properties.Sorderno.eq(sorderno)).list();
//        for (int i = 0; i < list.size(); i++) {
//            BaoCun bc = list.get(i);
//            sbinlog = bc.getSbinlogicalid();
//            if (sbinlogicalid.equals(sbinlog)) {
//                MyUtlis.showToast(BoxActivity.this, "本地库中相同的周转箱编号");
//                sbinlogicalid = "";
//            }
//        }
//    }

    /**
     * 判断是否扫了相同的周转箱编号
     */
    private void isItemChongfu() {
        if (mlist.size()!=0){
            for (BaoCun bc : mlist) {
                if (sbinlogicalid.equals(bc.getSbinlogicalid())) {
                    MyUtlis.showToast(BoxActivity.this, "列表中有相同周转箱编号");
                    sethandler();
                    sbinlogicalid="";
                }
            }
        }else{
            if (elist != null) {
                for (BaoCun bc : elist) {
                    if (sbinlogicalid.equals(bc.getSbinlogicalid())) {
                        MyUtlis.showToast(BoxActivity.this, "列表中有相同周转箱编号");
                        sethandler();
                        sbinlogicalid="";
                    }
                }
            }
            if (alist != null) {
                for (BaoCun bc : alist) {
                    if (sbinlogicalid.equals(bc.getSbinlogicalid())) {
                        MyUtlis.showToast(BoxActivity.this, "列表中有相同周转箱编号");
                        sethandler();
                        sbinlogicalid="";
                    }
                }
            }
        }
    }

    private void sethandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                etZhouzhuanxiang.setText("");
            }
        },2500);
    }

    private void initAdapter() {
        if (elist == null) {
            elist = new ArrayList<>();
            BaoCun baoCun = new BaoCun(sbinlogicalid, scashclasscode, sorderkind, scashclassname, bzsx, sorderno);
            elist.add(baoCun);
            adapter = new BoxRecyclerAdapter(BoxActivity.this, elist);
        } else {
            alist = new ArrayList<>();
            BaoCun baoCun = new BaoCun(sbinlogicalid, scashclasscode, sorderkind, scashclassname, bzsx, sorderno);
            elist.add(baoCun);
            alist.addAll(elist);
            adapter = new BoxRecyclerAdapter(BoxActivity.this, alist);
        }
        /**
         * 设置获取焦点
         */
        setEt();
        rcBox.setAdapter(adapter);
        rcBox.addItemDecoration(new DividerItemDecoration(BoxActivity.this, DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnLongClickListener(new RecyclerViewLongClick() {
            @Override
            public void ItemLongClick(View view, final int position) {
                elist.remove(position);
                if (alist != null) {
                    alist.remove(position);
                }
                adapter.notifyDataSetChanged();
                int i = position + 1;
                MyUtlis.showToast(BoxActivity.this, "第" + i + "条删除成功");
            }
        });
    }

    private void setEt() {
        etZhouzhuanxiang.setText("");
        etZhouzhuanxiang.setFocusable(true);
        etZhouzhuanxiang.setFocusableInTouchMode(true);
        LinearLayoutManager manager = new LinearLayoutManager(BoxActivity.this, LinearLayoutManager.VERTICAL, false);
        rcBox.setLayoutManager(manager);
    }

    /**
     * 给两个是spinner进行适配
     */

    private void SpinnerAdapter() {
        final List<String> ilist = new ArrayList<>();
        ilist.add("- - 未选择 - -");
        final List<QuanBie> DB_list = quanBieDao.queryBuilder().list();
        for (int i = 0; i < DB_list.size(); i++) {
            QuanBie bie = DB_list.get(i);
            String scashclassname = bie.getScashclassname();
            ilist.add(scashclassname);
        }
        final List<String> bzList = new ArrayList<>();
        for (int i = 0; i < city.length; i++) {
            String s = city[i];
            bzList.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_stytle, ilist);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_stytle, city);
        spQuanbie.setAdapter(adapter);
        spBizhong.setAdapter(adapter1);
        spQuanbie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                scashclassname = ilist.get(i);
                QuanBie spinner = DB_list.get(i);
                scashclasscode = spinner.getScashclasscode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spBizhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sorderkind = bzList.get(i);
                if (i == 0) {
                    bzsx = "1";
                } else if (i == 1) {
                    bzsx = "3";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 配置Toolbar
     */
    private void setToobar() {
        toolbar.setRightButtonText("上传本地");
        Drawable drawable = this.getResources().getDrawable(R.drawable.baocun);
        //非常重要，必须设置，否则图片不会显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        toolbar.setRightButtonIcon(drawable);
        toolbar.setLeftButtonIcon(R.drawable.back);
        toolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BoxActivity.this, ListActivity.class));
            }
        });
        /**
         *上传本地
         * 建表
         */
        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BoxActivity.this);
                builder.setTitle("提示信息")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("是否上传到本地数据库？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mlist.size()!=0){
                                    baoCunDao.queryBuilder().where(BaoCunDao.Properties.Sorderno.eq(sorderno)).buildDelete().executeDeleteWithoutDetachingEntities();
                                    baoCunDao.insertInTx(mlist);
                                    MyUtlis.showToast(BoxActivity.this, "成功上传" + mlist.size() + "箱");
                                    mlist.clear();
                                    adapter = new BoxRecyclerAdapter(BoxActivity.this, mlist);
                                    rcBox.setAdapter(adapter);
                                }else{
                                    if (elist == null) {
                                        MyUtlis.showToast(BoxActivity.this, "请先添加周转箱");
                                    } else if (elist.size() == 1) {
                                        baoCunDao.insertInTx(elist);
                                        MyUtlis.showToast(BoxActivity.this, "成功上传1箱");
                                        elist.clear();
                                        adapter = new BoxRecyclerAdapter(BoxActivity.this, elist);
                                        rcBox.setAdapter(adapter);
                                    } else {
                                        baoCunDao.insertInTx(alist);
                                        MyUtlis.showToast(BoxActivity.this, "成功上传" + alist.size() + "箱");
                                        alist.clear();
                                        adapter = new BoxRecyclerAdapter(BoxActivity.this, alist);
                                        rcBox.setAdapter(adapter);
                                    }
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
        });
    }
}
