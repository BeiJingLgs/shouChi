package cfcc.com.shouChi.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by acer on 2018/7/20.
 */
@Entity
public class QuanBie {
    private String scashclasscode;//券别代码
    private String scashclassname;//券别名称
    @Generated(hash = 872914693)
    public QuanBie(String scashclasscode, String scashclassname) {
        this.scashclasscode = scashclasscode;
        this.scashclassname = scashclassname;
    }
    @Generated(hash = 1573397225)
    public QuanBie() {
    }
    public String getScashclasscode() {
        return this.scashclasscode;
    }
    public void setScashclasscode(String scashclasscode) {
        this.scashclasscode = scashclasscode;
    }
    public String getScashclassname() {
        return this.scashclassname;
    }
    public void setScashclassname(String scashclassname) {
        this.scashclassname = scashclassname;
    }
}
