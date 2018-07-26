package cfcc.com.shouChi.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cfcc.com.shouChi.R;
import cfcc.com.shouChi.activity.ListActivity;
import cfcc.com.shouChi.activity.LoginActivity;
import cfcc.com.shouChi.adapter.RecyclerAdapter;
import cfcc.com.shouChi.api.RecyclerViewClick;
import cfcc.com.shouChi.api.RecyclerViewLongClick;
import cfcc.com.shouChi.base.Basepresenter;
import cfcc.com.shouChi.bean.User;
import cfcc.com.shouChi.db.DaoSession;
import cfcc.com.shouChi.database.MySqLiteHelper;
import cfcc.com.shouChi.db.DingDan;
import cfcc.com.shouChi.db.DingDanDao;
import cfcc.com.shouChi.db.Info;
import cfcc.com.shouChi.db.InfoDao;
import cfcc.com.shouChi.db.QuanBie;
import cfcc.com.shouChi.db.QuanBieDao;
import cfcc.com.shouChi.url.Path;
import cfcc.com.shouChi.utlis.Constant;
import cfcc.com.shouChi.utlis.DBase;
import cfcc.com.shouChi.utlis.DividerItemDecoration;
import cfcc.com.shouChi.utlis.MyToolbar;
import cfcc.com.shouChi.utlis.MyUtlis;
import cfcc.com.shouChi.utlis.SpUtlis;
import cfcc.com.shouChi.view.PackView;

/**
 * Created by acer on 2017/12/5.
 */
public class PackPresenter extends Basepresenter<PackView> {
    private Context context;
    private ArrayList<Integer> list;
    private List<DingDan> mlist;
    private MySqLiteHelper helper;
    private Boolean isOk;
    private String login_yydm;
    private String login_jghm;
    private String login_duankou;
    private String ip;
    private MyToolbar toolbar;
    private String mJsessionid;
    private Button button_Select;
    private Button benDi;
    private EditText mText;
    private EditText mTime;
    private RecyclerView mRecyclerView;
    private Button mDownLoad;
    private PackView view;
    private SharedPreferences mSp;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int mPosition;
    private DaoSession mSession;
    private InfoDao mInfoDao;
    private String sorderno;
    private List cList;
    private RecyclerAdapter adapter;
    private QuanBieDao qbDao;
    private DingDanDao dingDanDao;
    private List<DingDan> dList;

    public PackPresenter(Context context) {
        this.context = context;
    }


    /**
     * 获取控件
     * 做初始化工作
     */
    public void getControl(String jsessionid, SharedPreferences sp1, DaoSession session) {
        this.mSp = sp1;
        this.mJsessionid = jsessionid;
        this.mSession = session;
        mInfoDao = mSession.getInfoDao();
        qbDao = mSession.getQuanBieDao();
        dingDanDao = mSession.getDingDanDao();
        view = getView();
        if (view != null) {
            list = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                list.add(i);
            }
            initView();
        }
        initSp();

        setToolbar();


    }

    /**
     * 获取sp本地
     */
    private void initSp() {
        isOk = mSp.getBoolean("isOk", true);
        login_yydm = mSp.getString("daima", "");
        login_jghm = mSp.getString("num", "");
        login_duankou = mSp.getString("duankou", "");
        ip = mSp.getString("ip", "");
    }

    /**
     * 获取到组件
     */
    private void initView() {
        toolbar = view.getToolbar();
        button_Select = view.getButtonSelect();
        benDi = view.getBenDi();
        mText = view.getEditText();
        mTime = view.getEditTime();
        mRecyclerView = view.getRecyclerView();
        mDownLoad = view.getDownLoadButton();
        helper = view.getHelper();
    }
    private void NextActivity(List list, int position) {
        DingDan o = (DingDan) list.get(position);
        Intent intent = new Intent(context, ListActivity.class);
        String dprocessdate = o.getDprocessdate();
        String excutedate = o.getExcutedate();
        String ftotalsum = o.getFtotalsum();
        String mingcheng = o.getMingcheng();
        String sorderno = o.getSorderno();
        String sorderbank = o.getSorderbank();
        intent.putExtra("dprocessdate",dprocessdate);
        intent.putExtra("excutedate",excutedate);
        intent.putExtra("ftotalsum",ftotalsum);
        intent.putExtra("mingcheng",mingcheng);
        intent.putExtra("sorderno",sorderno);
        intent.putExtra("sorderbank",sorderbank);
        context.startActivity(intent);
    }
    /**
     * 下载订单
     */
    public void DownloadDiDan() {
        mDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mlist==null){
                    MyUtlis.setToast(context, "请先查询!");
                }else{
                    List<DingDan> tList=new ArrayList<>();
                    DingDan idan = mlist.get(PackPresenter.this.mPosition);
                    String sorderno = idan.getSorderno();
                    List<DingDan> list = dingDanDao.queryBuilder().where(DingDanDao.Properties.Sorderno.eq(sorderno)).list();
                    if (list.size()!=0){
                        MyUtlis.setToast(context, "该条订单已经下载过了");
                    }else{
                        tList.add(idan);
                        dingDanDao.insertInTx(tList);
                        mlist.clear();
                        setAdapter(mlist);
                        MyUtlis.setToast(context, "下载成功");
                    }
                }
            }
        });
    }

    /**
     * 本地订单
     */
    public void BendiDingDan() {
        benDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dList = new ArrayList<>();
                 final List<DingDan> list = dingDanDao.queryBuilder().list();
                int size = list.size();
                dList.addAll(list);
                setAdapter(list);
                adapter.setItemRecyclerViewClick(new RecyclerViewClick() {
                    @Override
                    public void ItemClick(View view, int position) {
                        NextActivity(list, position);
                    }
                });
                adapter.setLongRecyclerViewClick(new RecyclerViewLongClick() {
                    @Override
                    public void ItemLongClick(View view, int position) {
                        list.remove(position);
                        /**
                         *数据库删除已经选中的当条
                         */
                        DingDan dingDan = dList.get(position);
                        dingDanDao.queryBuilder().where(DingDanDao.Properties.Sorderno.eq(dingDan.getSorderno())).buildDelete().executeDeleteWithoutDetachingEntities();
                        List<DingDan> list = dingDanDao.queryBuilder().list();
                        int size1 = list.size();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * 查询
     */
    public void Chaxun() {
        button_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        //获取到订单编号的值
                        String bianhao = mText.getText().toString().trim();
                        //获取时间节点
                        String time = mTime.getText().toString().trim();
//                        String time = "2017-09-07";
                        //获取到搜索的返回值
                        String soapAction = Path.TB_NAMESPACE + Path.SEARCHORDERHEADER;
                        // 指定WebService的命名空间和调用方法
                        SoapObject soapObject = new SoapObject(Path.TB_NAMESPACE, Path.SEARCHORDERHEADER);
                        if (time != null) {
                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("searchOrderNo", bianhao);
                                jsonObject.put("searchDate", time);
                                jsonObject.put("functype", 0);
                                String s = jsonObject.toString();
                                soapObject.addProperty("str", s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.bodyOut = soapObject;
                        // 是否调用DotNet开发的
                        envelope.dotNet = true;
                        envelope.setOutputSoapObject(soapObject);
                        String path = "http://" + ip + ":" + login_duankou + "/" + login_yydm + "/xf/IRFWebService";
                        HttpTransportSE transport = new HttpTransportSE(path + ";jsessionid=" + mJsessionid);
                        try {
                            transport.call(soapAction, envelope);
                            //获取到返回值
                            Object response = envelope.getResponse();
                            String toString = response.toString();
                            JSONArray array = new JSONArray(toString);
                            mlist = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String ftotalsum = object.getString("ftotalsum");
                                String excutedate = object.getString("excutedate");
                                String dprocessdate = object.getString("dprocessdate");
                                String sorderno = object.getString("sorderno");
                                //0001005770  根据编码 查询机构名称
                                String sbankcode = object.getString("sorderbank");
                                /**
                                 * 根据sbankcode查询机构名称
                                 * 根据 银行编号sbankcode 在 InstInfo 表中 查询  银行名称sbankname
                                 */
                                List<Info> list = mInfoDao.queryBuilder().where(InfoDao.Properties.Sbankcode.eq(sbankcode)).list();
                                String sbankname = list.get(0).getSbankname();
                                DingDan dan = new DingDan(sorderno, sbankname, dprocessdate, ftotalsum, excutedate,sbankcode);
                                mlist.add(dan);
                            }
                            /**
                             *在主线程中更新UI
                             */
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    setAdapter(mlist);
                                    adapter.setItemRecyclerViewClick(new RecyclerViewClick() {
                                        @Override
                                        public void ItemClick(View view, int position) {
                                            mPosition = position;
                                        }
                                    });
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    private void setAdapter(List<DingDan> mlist) {
        adapter = new RecyclerAdapter(context, mlist);
        //3.进行适配
        mRecyclerView.setAdapter(adapter);
        //设置RecyclerView的manager
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));

    }

    /**
     * 同步信息
     *
     * @param
     */
    public void Synchronization() {
        //同步的点击事件
        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater from = LayoutInflater.from(context);
                View view1 = from.inflate(R.layout.sz_dialog, null);
                builder.setView(view1);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                /**
                 * 这里需要请求数据 并且将数据存到数据库中
                 */
                builder.setPositiveButton("同步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /**
                         * Msmly这个方法没什么卵用
                         *  Msmly(checkBox1, checkBox2);
                         */
                        //券别信息
                        getCashClassList();
                        //机构信息
                        getGreenDaoInstList();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    //机构信息
    private void getGreenDaoInstList() {
        new Thread() {
            @Override
            public void run() {
                String soapAction = Path.TB_NAMESPACE + Path.TB_GETINSTLIST;
                // 指定WebService的命名空间和调用方法
                SoapObject soapObject = new SoapObject(Path.TB_NAMESPACE, Path.TB_GETINSTLIST);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.bodyOut = soapObject;
                // 是否调用DotNet开发的
                envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObject);
                String path = "http://" + ip + ":" + login_duankou + "/" + login_yydm + "/xf/IRFWebService";
                HttpTransportSE transport = new HttpTransportSE(path + ";jsessionid=" + mJsessionid);
                try {
                    transport.call(soapAction, envelope);
                    Object envelopeResponse = envelope.getResponse();
                    if (envelopeResponse != null) {
                        String s = envelopeResponse.toString();
                        JSONArray array = new JSONArray(s);
                        List<Info> mList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String sbankcode = object.getString("sbankcode");
                            String sbankname = object.getString("sbankname");
                            String ilevel = object.getString("ilevel");
                            String sparentbankcode = object.getString("sparentbankcode");
                            Info info = new Info(sbankcode, sbankname, ilevel, sparentbankcode);
                            mList.add(info);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    MyUtlis.setToast(context, "同步成功");
                                }
                            });
                        }
                        mInfoDao.insertInTx(mList);
                    } else {
                        MyUtlis.setToast(context, "同步失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }

    //券别信息
    private void getCashClassList() {
        new Thread() {
            @Override
            public void run() {
                String soapAction = Path.TB_NAMESPACE + Path.TB_GETCASHCLASSLIST;
                // 指定WebService的命名空间和调用方法
                SoapObject soapObject = new SoapObject(Path.TB_NAMESPACE, Path.TB_GETCASHCLASSLIST);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.bodyOut = soapObject;
                // 是否调用DotNet开发的
                envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObject);
                String path = "http://" + ip + ":" + login_duankou + "/" + login_yydm + "/xf/IRFWebService";
                HttpTransportSE transport = new HttpTransportSE(path + ";jsessionid=" + mJsessionid);
                try {
                    transport.call(soapAction, envelope);
                    Object envelopeResponse = envelope.getResponse();
                    if (envelopeResponse != null) {
                        String s = envelopeResponse.toString();
                        JSONArray array = new JSONArray(s);
                        List<QuanBie> sList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String scashclasscode = object.getString("scashclasscode");
                            String scashclassname = object.getString("scashclassname");
                            QuanBie bie = new QuanBie(scashclasscode, scashclassname);
                            sList.add(bie);
                        }
                        qbDao.insertInTx(sList);
                        Log.i("tag", s);
                    } else {
                        MyUtlis.setToast(context, "券别信息返回值为空");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //复核人信息
    private void getCheckPersonList() {
        new Thread() {
            @Override
            public void run() {
                String soapAction = Path.TB_NAMESPACE + Path.TB_GETCHECKPERSONLIST;
                // 指定WebService的命名空间和调用方法
                SoapObject soapObject = new SoapObject(Path.TB_NAMESPACE, Path.TB_GETCHECKPERSONLIST);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.bodyOut = soapObject;
                // 是否调用DotNet开发的
                envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObject);
                String path = "http://" + ip + ":" + login_duankou + "/" + login_yydm + "/xf/IRFWebService";
                HttpTransportSE transport = new HttpTransportSE(path + ";jsessionid=" + mJsessionid);
                try {
                    transport.call(soapAction, envelope);
                    Object envelopeResponse = envelope.getResponse();
                    if (envelopeResponse != null) {
                        String s = envelopeResponse.toString();
                        Log.i("tag", s);
                        SQLiteDatabase db = DBase.getInstance().openDatabase();
                        String sql = "insert into " + Constant.TABLE_UDLOCKBINPERSON + "(slockperid,istate,iuserid,sstate,sidentitycard,sdn,sperphone,spersonname,itype)  values (?,?,?,?,?,?,?,?,?)";
                        SQLiteStatement statement = db.compileStatement(sql);
                        db.beginTransaction();
                        JSONArray array = new JSONArray(s);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            statement.bindString(1, object.getString("slockperid"));
                            statement.bindString(2, object.getString("istate"));
                            statement.bindString(3, object.getString("iuserid"));
                            statement.bindString(4, object.getString("sstate"));
                            statement.bindString(5, object.getString("sidentitycard"));
                            statement.bindString(6, object.getString("sdn"));
                            statement.bindString(7, object.getString("sperphone"));
                            statement.bindString(8, object.getString("spersonname"));
                            statement.bindString(9, object.getString("itype"));
                            statement.executeInsert();
                        }
                        db.setTransactionSuccessful();
                        db.endTransaction();
                        //关闭替换为
                        DBase.getInstance().closeDatabase();


//                                        db.close();
                        Log.i("tag", s);
                    } else {
                        MyUtlis.setToast(context, "复核人信息返回值为空");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //获取周转箱注册信息集合
    private void getBinInfoList() {
        new Thread() {
            @Override
            public void run() {
                String soapAction = Path.TB_NAMESPACE + Path.TB_NAMESPACE;
                // 指定WebService的命名空间和调用方法
                SoapObject soapObject = new SoapObject(Path.TB_NAMESPACE, Path.TB_NAMESPACE);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.bodyOut = soapObject;
                // 是否调用DotNet开发的
                envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObject);
                String path = "http://" + ip + ":" + login_duankou + "/" + login_yydm + "/xf/IRFWebService";
                HttpTransportSE transport = new HttpTransportSE(path + ";jsessionid=" + mJsessionid);
                try {
                    transport.call(soapAction, envelope);
                    Object envelopeResponse = envelope.getResponse();
                    if (envelopeResponse != null) {
                        String s = envelopeResponse.toString();
                        Log.i("tag", s);
                    } else {
                        MyUtlis.setToast(context, "周转箱信息返回值为空");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 初始化Toolbar
     */
    private void setToolbar() {
        toolbar.setLeftButtonIcon(R.drawable.back);
        toolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtlis.startActivity(context, LoginActivity.class);
            }
        });
        toolbar.setRightButtonText("信息同步");
        Drawable drawable = context.getResources().getDrawable(R.drawable.tongbu);
        //非常重要，必须设置，否则图片不会显示
        drawable.setBounds(0, 0, 40, 40);
        toolbar.setRightButtonIcon(drawable);
    }

    private void Msmly(CheckBox checkBox1, CheckBox checkBox2) {
        if (checkBox1.isChecked()) {
            //周转箱
//                            getBinInfoList();
//                            //复核人
//                            getCheckPersonList();
        }
        if (checkBox2.isChecked()) {
            //券别信息
//                            getCashClassList();
        }
    }
}
