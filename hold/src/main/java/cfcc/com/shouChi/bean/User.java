package cfcc.com.shouChi.bean;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Date;
import java.util.Hashtable;

/**
 * Created by acer on 2017/12/7.
 */

public class User implements KvmSerializable {
    /**
     * 机构代码
     */
    private String instcode;
    /**
     * 封箱人代码
     */
    private String userlockpercode;

    /**
     * 复核人代码
     */
    private String usermachinecode;
    /**
     * 封箱人密码
     */
    private String lockperpassword;
    /**
     * 复核人密码
     */
    private String machinepassword;
    /**
     * 登录日期
     */
    private Date loginDate;
    /**
     * IP地址
     */
    private String remoteAddr;
    /**
     * 应用代码
     */
    private String contextpath;

    public User() {

    }

    public User(String instcode, String userlockpercode, String usermachinecode, String lockperpassword, String machinepassword, Date loginDate, String remoteAddr, String contextpath) {
        this.instcode = instcode;
        this.userlockpercode = userlockpercode;
        this.usermachinecode = usermachinecode;
        this.lockperpassword = lockperpassword;
        this.machinepassword = machinepassword;
        this.loginDate = loginDate;
        this.remoteAddr = remoteAddr;
        this.contextpath = contextpath;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
    }

    public String getUserlockpercode() {
        return userlockpercode;
    }

    public void setUserlockpercode(String userlockpercode) {
        this.userlockpercode = userlockpercode;
    }

    public String getUsermachinecode() {
        return usermachinecode;
    }

    public void setUsermachinecode(String usermachinecode) {
        this.usermachinecode = usermachinecode;
    }

    public String getLockperpassword() {
        return lockperpassword;
    }

    public void setLockperpassword(String lockperpassword) {
        this.lockperpassword = lockperpassword;
    }

    public String getMachinepassword() {
        return machinepassword;
    }

    public void setMachinepassword(String machinepassword) {
        this.machinepassword = machinepassword;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getContextpath() {
        return contextpath;
    }

    public void setContextpath(String contextpath) {
        this.contextpath = contextpath;
    }

    @Override
    public Object getProperty(int arg0) {
        switch (arg0) {
            case 0:
                return instcode;
            case 1:
                return userlockpercode;
            case 2:
                return usermachinecode;
            case 3:
                return lockperpassword;
            case 4:
                return machinepassword;
            case 5:
                return loginDate;
            case 6:
                return remoteAddr;
            case 7:
                return contextpath;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 8;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                instcode = o.toString();
                break;
            case 1:
                userlockpercode = o.toString();
                break;
            case 2:
                usermachinecode = o.toString();
                break;
            case 3:
                lockperpassword = o.toString();
                break;
            case 4:
                machinepassword = o.toString();
                break;
            case 5:
                loginDate = (Date)o;
                break;
            case 6:
                remoteAddr = o.toString();
            case 7:
                contextpath = o.toString();
                break;
            default:
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "instcode";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "userlockpercode";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "usermachinecode";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "lockperpassword";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "machinepassword";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.OBJECT_TYPE;
                propertyInfo.name = "loginDate";
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "remoteAddr";
            case 7:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "contextpath";
                break;
            default:
                break;
        }
    }
}
