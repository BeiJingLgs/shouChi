package cfcc.com.shouChi.utlis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Text;

import cfcc.com.shouChi.R;

/**
 * 工具类
 * Created by acer on 2017/11/23.
 */
public class MyUtlis {
    public static final int LENGTH_SHORT = 0;
    @SuppressLint("NewApi")
    public static void showToast(Context conext, String str) {
        Toast mToast = Toast.makeText(conext, null,LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 400);
        LinearLayout toastView = (LinearLayout) mToast.getView();
        WindowManager wm = (WindowManager) conext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        TextView tv = new TextView(conext);
        /**
         *  如果Toast显示不正常，请把这句注释掉
         *   toastView.setBackground(conext.getDrawable(R.drawable.bg_toast));
         */
        toastView.setBackground(conext.getDrawable(R.drawable.bg_toast));
        tv.setTextSize(24);
        toastView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 40);
        tv.setLayoutParams(params);
        mToast.setView(toastView);
        toastView.addView(tv);
        tv.setText(str);
        tv.setTextColor(conext.getResources().getColor(R.color.colorWhite));
        mToast.show();
    }


    /**
     * 跳转页面
     *
     * @param context
     * @param activity
     */
    public static void startActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    /**
     * 吐司
     *
     * @param context
     * @param str
     */
    public static void setToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public static SoapObject getSoapObject(String nameSpace, String method) {
        // 设置连接参数
        SoapObject soapObject = new SoapObject(nameSpace, method);
        return soapObject;
    }

    public static HttpTransportSE getTransport(String url, SoapObject soapObject, SoapSerializationEnvelope envelope) {
        HttpTransportSE transport = new HttpTransportSE(url);
        transport.debug = true;// 是否是调试模式
        envelope.dotNet = false;
        envelope.bodyOut = transport;
        envelope.setOutputSoapObject(soapObject);// 设置请求参数
        return transport;
    }
}
