package cfcc.com.shouChi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import cfcc.com.shouChi.Myappcation;
import cfcc.com.shouChi.R;
import cfcc.com.shouChi.adapter.BoxRecyclerAdapter;
import cfcc.com.shouChi.base.Basepresenter;
import cfcc.com.shouChi.base.MvpBaseActivity;
import cfcc.com.shouChi.db.BaoCun;
import cfcc.com.shouChi.db.BaoCunDao;
import cfcc.com.shouChi.url.Path;
import cfcc.com.shouChi.utlis.MyToolbar;
import cfcc.com.shouChi.utlis.MyUtlis;
import cfcc.com.shouChi.utlis.SpUtlis;

/**
 * 添加列表
 * Created by acer on 2018/1/10.
 */

public class ListActivity extends MvpBaseActivity {


    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.dd_bianhao)
    EditText ddBianhao;
    @Bind(R.id.jg_minghceng)
    EditText jgMinghceng;
    @Bind(R.id.sq_riqi)
    EditText sqRiqi;
    @Bind(R.id.push_dingdan)
    Button pushDingdan;
    @Bind(R.id.sum_xiang)
    EditText sumXiang;
    private String sorderno;
    private BaoCunDao cunDao;
    private JSONArray array;
    private String login_yydm;
    private String login_jghm;
    private String login_duankou;
    private String ip;
    private String dprocessdate;
    private String mingcheng;
    private String excutedate;
    private String sorderbank;
    private List<BaoCun> mList;


    @Override
    public int getLayoutID() {
        return R.layout.zhuang_xiang;
    }
    @Override
    public  Basepresenter getPresenter() {
        return null;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cunDao = Myappcation.getInstance().getSession().getBaoCunDao();
        setToobar();
        getIntents();
        setXiang();
        /**
         *上传订单
         */
        pushDingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("提示信息")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("是否上传到后台数据库？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
/**
 * 上传到网络数据库
 */
                                pushInternet();
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

    private void pushInternet() {
        if (mList.size() == 0) {
            MyUtlis.setToast(ListActivity.this, "本地库中没有箱子，请去添加！");
        } else {
            getJson(mList);
            new Thread() {
                @Override
                public void run() {
                    SharedPreferences sp1 = getSharedPreferences("a", Activity.MODE_PRIVATE);
                    Boolean isOk = sp1.getBoolean("isOk", true);
                    login_yydm = sp1.getString("daima", "");
                    login_jghm = sp1.getString("num", "");
                    login_duankou = sp1.getString("duankou", "");
                    ip = sp1.getString("ip", "");
                    SharedPreferences sp = getSharedPreferences("b", Activity.MODE_PRIVATE);
                    String jsessionid = sp.getString("jsessionid", "");
                    String soapAction = Path.TB_NAMESPACE + Path.UPDATEORDER;
                    SoapObject soapObject = MyUtlis.getSoapObject(Path.TB_NAMESPACE, Path.UPDATEORDER);
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("sorderno", sorderno);
                        jsonObject.put("dprocessdate", dprocessdate);
                        jsonObject.put("sorderbank", sorderbank);
                        jsonObject.put("orderBinList", array);
                        String s = jsonObject.toString();
                        soapObject.addProperty("str", s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.bodyOut = soapObject;
                    // 是否调用DotNet开发的
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(soapObject);
                    String path = "http://" + ip + ":" + login_duankou + "/" + login_yydm + "/xf/IRFWebService";
                    HttpTransportSE transport = new HttpTransportSE(path + ";jsessionid=" + jsessionid);
                    try {
                        transport.call(soapAction, envelope);
                        // 获取返回的数据s
                        Object response = (Object) envelope.getResponse();
                        boolean equals = response.equals(response);
                        if (equals == true) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyUtlis.setToast(ListActivity.this, "上传成功");
                                    cunDao.queryBuilder().where(BaoCunDao.Properties.Sorderno.eq(sorderno)).buildDelete().executeDeleteWithoutDetachingEntities();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private void getIntents() {
        Intent intent = getIntent();
        //订单编号
        sorderno = intent.getStringExtra("sorderno");
        //机构名称
        mingcheng = intent.getStringExtra("mingcheng");
        //申请日期
        dprocessdate = intent.getStringExtra("dprocessdate");
        //机构编码
        sorderbank = intent.getStringExtra("sorderbank");
        setText(sorderno, mingcheng, dprocessdate);
    }

    private void setXiang() {
        new Thread() {
            public void run() {
                mList = cunDao.queryBuilder().where(BaoCunDao.Properties.Sorderno.eq(sorderno)).list();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sumXiang.setText(mList.size()+"箱");
                    }
                });
            }
        }.start();
    }


    @Override
    protected void onStart() {
        super.onStart();
        setXiang();
    }

    private void getJson(List<BaoCun> list) {

        array = new JSONArray();
        for (BaoCun bc : list) {
            JSONObject json = new JSONObject();
            String sbinlogicalid = bc.getSbinlogicalid();
            String scashclasscode = bc.getScashclasscode();
            String bzsx = bc.getBizhongshuxing();
            try {
                json.put("sbinlogicalid", sbinlogicalid);
                json.put("scashclasscode", scashclasscode);
                json.put("sorderkind", bzsx);
                array.put(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setText(String sorderno, String mingcheng, String dprocessdate) {
        ddBianhao.setText(sorderno);
        jgMinghceng.setText(mingcheng);
        sqRiqi.setText(dprocessdate);
    }

    /**
     * 配置Toolbar
     */
    private void setToobar() {
        toolbar.setRightButtonText("扫描装箱");
        Drawable drawable = this.getResources().getDrawable(R.drawable.tianjia);
        //非常重要，必须设置，否则图片不会显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        toolbar.setRightButtonIcon(drawable);
        toolbar.setLeftButtonIcon(R.drawable.back);
        toolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtlis.startActivity(ListActivity.this, PackingActivity.class);
            }
        });
        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, BoxActivity.class);
                intent.putExtra("sorderno", sorderno);
                startActivity(intent);
            }
        });
    }
}
