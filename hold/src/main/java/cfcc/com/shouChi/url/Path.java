package cfcc.com.shouChi.url;

/**
 * Created by acer on 2017/11/22.
 */

public class Path {
    public static final String LW_LOGIN = "";
    /**
     * ConFigureActivity
     */
    //环境配置与登陆的nameSpace   命名空间
    public static final String LOGIN_NAMESPACE = "http://service.rf.subsystem.imspbc.cfcc.com/";
    //环境配置的methodName  方法名字
    public static final String CONFIGURE_METHODNAME = "testConnection";
    //保存订单  将订单上传到数据库
    public static final String UPDATEBININFO = "updateBinInfo";
    //保存订单  将订单上传到数据库
    public static final String UPDATEORDER = "updateOrder";
    //环境配置与登陆的Url  url
//    public static final String URL1 = "http://192.168.43.200:8080/imsclient/xf/IRFValidateService";
    public static final String URL1 = "http://192.168.43.200:8080/imsaib/xf/IRFValidateService";
    //登陆页面的方法名http://
    public static final String LOGIN_METHODNAME = "createRomoteUser";
    //登陆页面应用代码
//    public static final String LOGIN_YYDM = "imsclient";
    public static final String LOGIN_YYDM = "imsaib";
    //登陆页面的机构代码
//    public static final String LOGIN_JGDM = "0001005770";
//    public static final String LOGIN_JGDM = "42280120";
    public static final String LOGIN_JGDM = "42280101";
    //同步的url
//  public static final String TB_URL = "http://192.168.43.200:8080/imsclient/xf/IRFWebService";
    public static final String TB_URL = "http://192.168.43.200:8080/imsaib/xf/IRFWebService";
    //同步的命名空间
    public static final String TB_NAMESPACE = "http://service.rf.subsystem.imspbc.cfcc.com/";
    //获取周转箱注册信息集合
    public static final String TB_GETBININFOLIST = "getBinInfoList";
    //获取券别信息集合
    public static final String TB_GETCASHCLASSLIST = "getCashClassList";
    //获取复核人信息集合
    public static final String TB_GETCHECKPERSONLIST = "getCheckPersonList";
    // 获取机构信息集合
    public static final String TB_GETINSTLIST = "getInstList";
    //查询的方法
    public static final String SEARCHORDERHEADER = "searchOrderHeader";
}
