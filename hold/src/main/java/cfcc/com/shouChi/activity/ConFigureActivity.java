package cfcc.com.shouChi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import butterknife.Bind;
import cfcc.com.shouChi.R;
import cfcc.com.shouChi.base.Basepresenter;
import cfcc.com.shouChi.base.MvpBaseActivity;
import cfcc.com.shouChi.url.Path;
import cfcc.com.shouChi.utlis.MyToolbar;
import cfcc.com.shouChi.utlis.MyUtlis;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by acer on 2017/11/21.
 */

public class ConFigureActivity extends MvpBaseActivity {
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.ip)
    EditText ip;
    @Bind(R.id.dma)
    EditText dma;
    @Bind(R.id.num)
    EditText num;
    @Bind(R.id.duanko)
    EditText duanko;
    @Bind(R.id.ok)
    Button ok;
    private String num1;
    private String ip1;
    private String dk;
    private String dma1;
    private String TAG = "tag";

    @Override
    public int getLayoutID() {
        return R.layout.configure;
    }

    @Override
    public Basepresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCanChe();
        toolbar.setLeftButtonIcon(R.drawable.back);
        toolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtlis.startActivity(ConFigureActivity.this, LoginActivity.class);
            }
        });

        /**
         * 点击确认 缓存数据  验证
         */
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initText();
                /**
                 * 不能为空
                 */
                if ("".equals(ip1) && "".equals(num1) && "".equals(dk) && "".equals(dma1)) {
                    Toast.makeText(ConFigureActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            // SOAP Action
                            String soapAction = Path.LOGIN_NAMESPACE + Path.CONFIGURE_METHODNAME;
                            // 指定WebService的命名空间和调用方法
                            SoapObject soapObject = new SoapObject(Path.LOGIN_NAMESPACE, Path.CONFIGURE_METHODNAME);
                            // 设置需要调用WebService接口的两个参数mobileCode UserId
                            // soapObject.addProperty("mobileCode", phone);
//                             生成调用WebService方法调用的soap信息，并且指定Soap版本
                            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                            envelope.bodyOut = soapObject;
                            // 是否调用DotNet开发的
                            envelope.dotNet = true;
                            envelope.setOutputSoapObject(soapObject);
                            String path = "http://" + ip1 + ":" + dk + "/" + dma1 + "/xf/IRFValidateService";
                            HttpTransportSE transport = new HttpTransportSE(path);
                            try {
                                // soap 协议发送
                                transport.call(soapAction, envelope);
                                // 获取返回的数据s
                                Object response = (Object) envelope.getResponse();
                                boolean equals = response.equals(response);
                                if (equals == true) {
                                    LocalCache();
                                    /**
                                     *要显示在页面的东西必须是在主线程
                                     */
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ConFigureActivity.this);
                                            builder.setTitle("提示信息")
                                                    .setIcon(R.mipmap.ic_launcher)
                                                    .setMessage("环境配置成功")
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            MyUtlis.startActivity(ConFigureActivity.this, LoginActivity.class);
                                                        }
                                                    });
                                            builder.create().show();
                                        }
                                    });

                                } else {
                                    //要显示在页面的东西必须是在主线程
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ConFigureActivity.this);
                                            builder.setTitle("提示信息")
                                                    .setIcon(R.mipmap.ic_launcher)
                                                    .setMessage("环境配置失败，请重新配置")
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    });
                                            builder.create().show();
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


        });
    }

    /**
     * 拿到本地缓存
     */
    private void getCanChe() {
        SharedPreferences sp = getSharedPreferences("a", Activity.MODE_PRIVATE);
        ip.setText(sp.getString("ip", ""));
        num.setText(sp.getString("num", ""));
        duanko.setText(sp.getString("duankou", ""));
        dma.setText(sp.getString("daima", ""));
    }

    /**
     * 缓存到本地
     */
    private void LocalCache() {
        SharedPreferences sp = getSharedPreferences("a", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("ip", ip1);
        edit.putString("num", num1);
        edit.putString("duankou", dk);
        edit.putString("daima", dma1);
        edit.putBoolean("isOk", true);
        edit.commit();
    }

    /**
     * 获取到EditText的值
     */
    private void initText() {
        ip1 = ip.getText().toString().trim();
        num1 = num.getText().toString().trim();
        dk = duanko.getText().toString().trim();
        dma1 = dma.getText().toString().trim();

    }
}
