package cfcc.com.shouChi.utlis;


/**
 * Created by acer on 2017/11/24.
 */

public class Constant {
    public static final String DATABASE_NAME = "sc.db";
    public static final int DATABASE_VESION = 2;
    public static final String ID = "_id";
      /**
     * 机构信息表sql
     */
    public static   final String InstInfo = "create table " + Constant.TABLE_INSTINFO + "(" + Constant.ID + " Integer primary key  autoincrement," + Constant.SBANKCODE + " varchar(10)," + Constant.SBANKNAME + " varchar(10)," + Constant.ILEVEL + " varchar(10)," + Constant.SPARENTBANKCODE + "  varchar(10))";
    /**
     * 周转箱注册sql
     */
    public static final String UdBinInfo = "create table " + Constant.TABLE_UDBININFO + "(" + Constant.ID + " Integer primary key  autoincrement  ," + Constant.SBINLOGICALID + " varchar(10)," + Constant.SCASHCLASSCODE + " varchar(10)," +
            "" + Constant.SORDERKIND + "  varchar(10) ," + Constant.IBUNDLENUM + " varchar(10)," + Constant.SMACHINEID + " varchar(10)," + Constant.SLOCKPERID + " varchar(10)," + Constant.FBUNDLESUM + " decimal)";

    /**
     * 复核人sql
     * slockperid,istate,iuserid,sstate,sidentitycard,sdn,sperphone,spersonname,itype
     */
    public static final String UdLockbinPerson="create table " + Constant.TABLE_UDLOCKBINPERSON +
            "(" + Constant.ID + " Integer primary key  autoincrement,"+Constant.SLOCKPERID+" varchar(10)," +
            ""+Constant.ISTATE+" varchar(10),"+Constant.IUSERID+" varchar(10),"+Constant.SSTATE+" varchar(10)," +
            ""+Constant.SIDENTITYCARD+" varchar(10),"+Constant.SDN+" varchar(10),"+Constant.SPERPHONE+"   varchar(10),"+Constant.SPERSONNAME+" varchar(10)," +
            ""+Constant.ITYPE+" varchar(10))";
    /**
     * 券别信息
     * fboxweight2,fresparvalue,iclass,soldcashclasscode,iboxbundle,
     * icashattri,scashclassname,swmscashclasscode,svelocity,scashclasscode,
     * iprimaryorassist,iholepaper,dutilchangedate,icurrboxbundle,itrueuse,scode,
     * fpriceofkiloruler,fparvalue,scashsize,irescurrency,ifalseuse,isalverbox,ibundlehole,
     * iset,iedition,istate,scheckweight,ichangenumber,dchangedate,fcurrbundleweight,fboxweight
     */
    public static  final String Um8CashClassInfo="create table "+Constant.TABLE_UM8CASHCLASSINFO+"("+Constant.ID+" Integer primary key  autoincrement, "+Constant.FBOXWEIGHT2+" varchar(10)" +
            ","+Constant.FRESPARVALUE+" varchar(10),"+Constant.ICLASS+" varchar(10),"+Constant.SOLDCASHCLASSCODE+" varchar(10),"+Constant.IBOXBUNDLE+" varchar(10)," +
            ""+Constant.ICASHATTRI+" varchar(10),"+Constant.SCASHCLASSNAME+" varchar(10),"+Constant.SWMSCASHCLASSCODE+" varchar(10),"+Constant.SVELOCITY+" decimal," +
            ""+Constant.SCASHCLASSCODE+" decimal,"+Constant.IPRIMARYORASSIST+" decimal,"+Constant.IHOLEPAPER+"  date,"+Constant.DUTILCHANGEDATE+" varchar(10)," +
            ""+Constant.ICURRBOXBUNDLE+" varchar(10),"+Constant.ITRUEUSE+" varchar(10),"+Constant.SCODE+" decimal,"+Constant.FPRICEOFKILORULER+"  varchar(10),"+Constant.FPARVALUE+" varchar(10)" +
            ","+Constant.SCASHSIZE+" varchar(10),"+Constant.IRESCURRENCY+" varchar(10),"+Constant.IFALSEUSE+" varchar(10),"+Constant.ISALVERBOX+" varchar(10)," +
            ""+Constant.IBUNDLEHOLE+" varchar(10),"+Constant.ISET+" date,"+Constant.IEDITION+" varchar(10)," +
            ""+Constant.ISTATE+" varchar(10),"+Constant.SCHECKWEIGHT+" varchar(10),"+Constant.ICHANGENUMBER+" decimal,"+Constant.DCHANGEDATE+" decimal,"+Constant.FCURRBUNDLEWEIGHT+" varchar(10),"+Constant.FBOXWEIGHT+" varchar(10))";




    /**
     * 机构信息表
     */
    //表名字
    public static final String TABLE_INSTINFO = "InstInfo";
    public static final String SBANKCODE = "sbankcode";// 机构代码 规则：8位 第一位级别 第二位本行还是全辖 第三/四位所属分行序号
    public static final String SBANKNAME = "sbankname";// 机构名称,录入
    public static final String ILEVEL = "ilevel";// 机构级别 1总行 3分行 4中心支行 5县行
    public static final String SPARENTBANKCODE = "sparentbankcode";// 直属机构代码,即登陆机构的本行代码
    /**
     * 周转箱注册表
     */
    //表名字
    public static final String TABLE_UDBININFO = "UdBinInfo";
    public static final String SBINLOGICALID = "sbinlogicalid";
    public static final String SORDERKIND = "sorderkind";
    public static final String IBUNDLENUM = "ibundlenum";
    public static final String SMACHINEID = "smachineid";
    //复核人与周转箱注册表公用同一个SLOCKPERID
    public static final String SLOCKPERID = "slockperid";
    public static final String FBUNDLESUM = "fbundlesum";
    /**
     * 复核人
     */
    //表名字
    public static final String TABLE_UDLOCKBINPERSON = "UdLockbinPerson";
    //这是干嘛的啊
    public static final String SIDENTITYCARD = "sidentitycard";
    public static final String SPERSONNAME = "spersonname";
    public static final String SPERPHONE = "sperphone";
    public static final String IUSERID = "iuserid";
    public static final String SDN = "sdn";
    public static final String SSTATE = "sstate";
    public static final String ITYPE = "itype";//人员类别
    /**
     * 券别信息
     */
    public static final String TABLE_UM8CASHCLASSINFO="Um8CashClassInfo";
    //与周转箱注册公用scashclasscode
    public static final String SCASHCLASSCODE = "scashclasscode";
    public static final String SWMSCASHCLASSCODE = "swmscashclasscode";
    public static final String SCASHCLASSNAME = "scashclassname";
    public static final String ICLASS = "iclass";
    public static final String ISET = "iset";
    public static final String IEDITION = "iedition";
    public static final String IPRIMARYORASSIST = "iprimaryorassist";
    public static final String SCASHSIZE = "scashsize";
    public static final String FPRICEOFKILORULER = "fpriceofkiloruler";
    public static final String FRESPARVALUE = "fresparvalue";
    public static final String FPARVALUE = "fparvalue";
    public static final String DCHANGEDATE = "dchangedate";
    public static final String IBOXBUNDLE = "iboxbundle";
    public static final String IBUNDLEHOLE = "ibundlehole";
    public static final String IHOLEPAPER = "iholepaper";
    public static final String FBOXWEIGHT = "fboxweight";
    public static final String IRESCURRENCY = "irescurrency";
    //与复核人公用istate
    public static final String ISTATE = "istate";
    public static final String ICHANGENUMBER = "ichangenumber";
    public static final String SCODE = "scode";
    public static final String SOLDCASHCLASSCODE = "soldcashclasscode";
    public static final String IFALSEUSE = "ifalseuse";
    public static final String ITRUEUSE = "itrueuse";
    public static final String DUTILCHANGEDATE = "dutilchangedate";
    public static final String ICASHATTRI = "icashattri";
    public static final String ICURRBOXBUNDLE = "icurrboxbundle";
    public static final String ISALVERBOX = "isalverbox";
    public static final String FCURRBUNDLEWEIGHT = "fcurrbundleweight";
    public static final String FBOXWEIGHT2 = "fboxweight2";
    public static final String SVELOCITY = "svelocity";
    public static final String SCHECKWEIGHT = "scheckweight";
}
