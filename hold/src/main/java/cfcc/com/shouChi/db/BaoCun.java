package cfcc.com.shouChi.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by acer on 2018/3/26.
 */
@Entity
public class BaoCun {
    private String sbinlogicalid; //周转箱的编号
    private String scashclasscode;//券别代码
    private String sorderkind;//币种属性
    private String scashclassname;//券别名称
    private String bizhongshuxing;//币种属性代号
    private String sorderno; //订单编号
    @Generated(hash = 802946825)
    public BaoCun(String sbinlogicalid, String scashclasscode, String sorderkind,
            String scashclassname, String bizhongshuxing, String sorderno) {
        this.sbinlogicalid = sbinlogicalid;
        this.scashclasscode = scashclasscode;
        this.sorderkind = sorderkind;
        this.scashclassname = scashclassname;
        this.bizhongshuxing = bizhongshuxing;
        this.sorderno = sorderno;
    }
    @Generated(hash = 464400472)
    public BaoCun() {
    }
    public String getSbinlogicalid() {
        return this.sbinlogicalid;
    }
    public void setSbinlogicalid(String sbinlogicalid) {
        this.sbinlogicalid = sbinlogicalid;
    }
    public String getScashclasscode() {
        return this.scashclasscode;
    }
    public void setScashclasscode(String scashclasscode) {
        this.scashclasscode = scashclasscode;
    }
    public String getSorderkind() {
        return this.sorderkind;
    }
    public void setSorderkind(String sorderkind) {
        this.sorderkind = sorderkind;
    }
    public String getScashclassname() {
        return this.scashclassname;
    }
    public void setScashclassname(String scashclassname) {
        this.scashclassname = scashclassname;
    }
    public String getBizhongshuxing() {
        return this.bizhongshuxing;
    }
    public void setBizhongshuxing(String bizhongshuxing) {
        this.bizhongshuxing = bizhongshuxing;
    }
    public String getSorderno() {
        return this.sorderno;
    }
    public void setSorderno(String sorderno) {
        this.sorderno = sorderno;
    }
}
