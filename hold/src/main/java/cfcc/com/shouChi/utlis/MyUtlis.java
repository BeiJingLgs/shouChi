package cfcc.com.shouChi.utlis;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 *工具类
 * Created by acer on 2017/11/23.
 */
public class MyUtlis {
    /**
     * 跳转页面
     * @param context
     * @param activity
     */
    public  static  void    startActivity(Context context, Class<?>  activity){
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }
    /**
     *吐司
     * @param context
     * @param str
     */
    public static   void    setToast(Context context,String  str){
        Toast.makeText(context,str,Toast.LENGTH_LONG).show();
    }
    public  static SoapObject getSoapObject(String nameSpace, String method){
        // 设置连接参数
        SoapObject soapObject = new SoapObject(nameSpace,method);
        return  soapObject;
    }
    public   static HttpTransportSE getTransport(String url, SoapObject soapObject,SoapSerializationEnvelope  envelope){
        HttpTransportSE transport = new HttpTransportSE(url);
        transport.debug = true;// 是否是调试模式
        envelope.dotNet = false;
        envelope.bodyOut = transport;
        envelope.setOutputSoapObject(soapObject);// 设置请求参数
        return transport;
    }
}
