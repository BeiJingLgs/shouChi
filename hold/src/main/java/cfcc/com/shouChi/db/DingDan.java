package cfcc.com.shouChi.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by acer on 2018/7/23.
 */
@Entity
public class DingDan {
    private String sorderno;
    private String mingcheng;
    private String dprocessdate;
    private String ftotalsum;
    private String excutedate;
    private String sorderbank;
    @Generated(hash = 280404019)
    public DingDan(String sorderno, String mingcheng, String dprocessdate,
            String ftotalsum, String excutedate, String sorderbank) {
        this.sorderno = sorderno;
        this.mingcheng = mingcheng;
        this.dprocessdate = dprocessdate;
        this.ftotalsum = ftotalsum;
        this.excutedate = excutedate;
        this.sorderbank = sorderbank;
    }
    @Generated(hash = 1551698128)
    public DingDan() {
    }
    public String getSorderno() {
        return this.sorderno;
    }
    public void setSorderno(String sorderno) {
        this.sorderno = sorderno;
    }
    public String getMingcheng() {
        return this.mingcheng;
    }
    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }
    public String getDprocessdate() {
        return this.dprocessdate;
    }
    public void setDprocessdate(String dprocessdate) {
        this.dprocessdate = dprocessdate;
    }
    public String getFtotalsum() {
        return this.ftotalsum;
    }
    public void setFtotalsum(String ftotalsum) {
        this.ftotalsum = ftotalsum;
    }
    public String getExcutedate() {
        return this.excutedate;
    }
    public void setExcutedate(String excutedate) {
        this.excutedate = excutedate;
    }
    public String getSorderbank() {
        return this.sorderbank;
    }
    public void setSorderbank(String sorderbank) {
        this.sorderbank = sorderbank;
    }


}
