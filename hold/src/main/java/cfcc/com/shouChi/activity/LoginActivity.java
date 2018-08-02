package cfcc.com.shouChi.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import cfcc.com.shouChi.R;
import cfcc.com.shouChi.base.Basepresenter;
import cfcc.com.shouChi.base.MvpBaseActivity;
import cfcc.com.shouChi.url.Path;
import cfcc.com.shouChi.utlis.MyToolbar;
import cfcc.com.shouChi.utlis.MyUtlis;

/**
 * Created by acer on 2017/11/21.
 */

public class LoginActivity extends MvpBaseActivity {

    @Bind(R.id.user_one)
    EditText user_one;
    @Bind(R.id.pass1)
    EditText pass1;
    @Bind(R.id.user2)
    EditText user2;
    @Bind(R.id.pass2)
    EditText pass2;
    @Bind(R.id.date)
    EditText date;
    @Bind(R.id.DW_login)
    Button DWLogin;
    @Bind(R.id.LW_Login)
    Button LWLogin;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.logout)
    Button logout;
    private String user_1;
    private String user_2;
    private String pass_1;
    private String pass_2;
    private String time;
    private Date curDate;
    /**
     * ip:是当前用户ip地址
     */
    private String ip;
    /**
     * 端口号
     */
    private String login_duankou;
    /**
     * 应用代码
     */
    private String login_jghm;
    /**
     * 机构代码
     */
    private String login_yydm;
    private String format;
    private String[] split;

    @Override
    public int getLayoutID() {
        return R.layout.login_two;
    }

    @Override
    public Basepresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setRightButtonText("环境配置");
//        Intent intent=getIntent();
//        String ip=  intent.getStringExtra("ip");
//        String duankou=  intent.getStringExtra("duankou");
//        String daima=  intent.getStringExtra("daima");
//        String haoma= intent.getStringExtra("haoma");
        Drawable drawable = this.getResources().getDrawable(R.drawable.wangluo);
        //非常重要，必须设置，否则图片不会显示
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        drawable.setBounds(0, 0,40,40);
        toolbar.setRightButtonIcon(drawable);
        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtlis.startActivity(LoginActivity.this, ConFigureActivity.class);
            }
        });
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        curDate = new Date(System.currentTimeMillis());
        format = formatter.format(curDate);
        date.setText(formatter.format(curDate));
        setEditText();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        /**
         * 联网登陆代码
         */
        LWLogin.setOnClickListener(new View.OnClickListener() {
            private boolean isOk;

            @Override
            public void onClick(View view) {
                initView();
                getSp();
                if (isOk == true) {
                    if ("".equals(user_1) && "".equals(user_2) && "".equals(pass_1) && "".equals(pass_2) && "".equals(time)) {
                        MyUtlis.showToast(LoginActivity.this, "账号密码不能为空");
                    } else {
                        new Thread() {
                            @Override
                            public void run() {
                                String soapAction = Path.LOGIN_NAMESPACE + Path.LOGIN_METHODNAME;
                                SoapObject soapObject = MyUtlis.getSoapObject(Path.LOGIN_NAMESPACE, Path.LOGIN_METHODNAME);

                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("instcode",login_jghm);
                                    jsonObject.put("userlockpercode", user_1);
                                    jsonObject.put("usermachinecode", user_2);
                                    jsonObject.put("lockperpassword", pass_1);
                                    jsonObject.put("machinepassword", pass_2);
                                    jsonObject.put("loginDate", format);
                                    jsonObject.put("remoteAddr", ip);
                                    jsonObject.put("contextpath", login_yydm);
                                    String s = jsonObject.toString();
                                    soapObject.addProperty("str", s);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                                String path="http://"+ip+":"+login_duankou+"/"+login_yydm+"/xf/IRFValidateService";
                                HttpTransportSE transport = MyUtlis.getTransport(path, soapObject, envelope);
                                try {
                                    transport.call(soapAction, envelope);
                                    Object obj = envelope.getResponse();
                                    String s = obj.toString();
                                    if (s != null && !s.equals("")) {
                                        String substring = s.substring(8, s.length() - 2);
                                        split = substring.split("; ");
                                        String jsessionid = LocalCache();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                MyUtlis.showToast(LoginActivity.this, "登录成功");
                                            }
                                        });
                                        Intent intent = new Intent(LoginActivity.this, PackingActivity.class);
                                        intent.putExtra("jsessionid", jsessionid);
                                        startActivity(intent);
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                MyUtlis.showToast(LoginActivity.this, "登录失败");
                                            }
                                        });
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    final String message = e.getMessage();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MyUtlis.showToast(LoginActivity.this, message);
                                        }
                                    });
                                } catch (XmlPullParserException e) {
                                    e.printStackTrace();
                                }
                            }

                            private void setValue(SoapObject soapObject) {
                            }
                        }.start();
                    }
                } else {
                    MyUtlis.showToast(LoginActivity.this, "请先进行环境配置");
                }
            }

            /**
             * 获 取环境配置的参数
             */

            private void getSp() {
                SharedPreferences sp1 = getSharedPreferences("a", Activity.MODE_PRIVATE);
                isOk = sp1.getBoolean("isOk", true);
                login_yydm = sp1.getString("daima", "");
                login_jghm = sp1.getString("num", "");
                login_duankou = sp1.getString("duankou", "");
                ip = sp1.getString("ip", "");
            }
        });
        /**
         * 断网登陆
         */
        DWLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initView();
                SharedPreferences sp = getSharedPreferences("test", Activity.MODE_PRIVATE);
                String username1 = sp.getString("username1", "");
                String password1 = sp.getString("password1", "");
                String username2 = sp.getString("username2", "");
                String password2 = sp.getString("password2", "");
                if ("".equals(user_1) && "".equals(user_2) && "".equals(pass_1) && "".equals(pass_2) && "".equals(time)) {
                    MyUtlis.showToast(LoginActivity.this, "账号密码不能为空!");
                } else if ("".equals(user_1) || "".equals(user_2) || "".equals(pass_1) || "".equals(pass_2) || "".equals(time)) {
                    MyUtlis.showToast(LoginActivity.this, "请输入登陆必填项！");
                } else if (user_1.equals(username1) && user_2.equals(username2) && pass_1.equals(password1) && pass_2.equals(password2)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyUtlis.showToast(LoginActivity.this, "登录成功");
                        }
                    });
                    MyUtlis.startActivity(LoginActivity.this, PackingActivity.class);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyUtlis.showToast(LoginActivity.this, "登录失败");
                        }
                    });
                }
            }
        });
    }

    /**
     * 获取本地缓存 set到EditText中
     */

    private void setEditText() {
        SharedPreferences sp = getSharedPreferences("test", Activity.MODE_PRIVATE);
        String username1 = sp.getString("username1", "");
        String password1 = sp.getString("password1", "");
        String username2 = sp.getString("username2", "");
        String password2 = sp.getString("password2", "");
        user_one.setText(username1);
        user2.setText(username2);
        pass1.setText(password1);
        pass2.setText(password2);
    }

    /**
     * 获取到EditText中的字符串
     */
    private void initView() {
        user_1 = user_one.getText().toString().trim();
        user_2 = user2.getText().toString().trim();
        pass_1 = pass1.getText().toString().trim();
        pass_2 = pass2.getText().toString().trim();
        time = date.getText().toString().trim();
    }

    /**
     * 创建SharedPreferences的实例
     * 请求成功后，返回数据，
     * 登陆成功缓存数据
     */
    private String LocalCache() {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < split.length; i++) {
            String s1 = split[i];
            String[] strings = s1.split("=");
            map.put(strings[0], strings[1]);
        }
        String userlockpercode = map.get("userlockpercode");
        String usermachinecode = map.get("usermachinecode");
        String lockperpassword = map.get("lockperpassword");
        String machinepassword = map.get("machinepassword");
        String jsessionid = map.get("jsessionid");
        SharedPreferences sp = getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("username1", userlockpercode);
        edit.putString("password1", lockperpassword);
        edit.putString("username2", usermachinecode);
        edit.putString("password2", machinepassword);
        edit.commit();
        return jsessionid;
    }
}
