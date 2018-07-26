package cfcc.com.shouChi.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by acer on 2018/7/18.
 */
@Entity
public class Info {
    private String sbankcode;
    private String sbankname;
    private String ilevel;
    private String sparentbankcode;
    @Generated(hash = 2091921595)
    public Info(String sbankcode, String sbankname, String ilevel,
            String sparentbankcode) {
        this.sbankcode = sbankcode;
        this.sbankname = sbankname;
        this.ilevel = ilevel;
        this.sparentbankcode = sparentbankcode;
    }
    @Generated(hash = 614508582)
    public Info() {
    }
    public String getSbankcode() {
        return this.sbankcode;
    }
    public void setSbankcode(String sbankcode) {
        this.sbankcode = sbankcode;
    }
    public String getSbankname() {
        return this.sbankname;
    }
    public void setSbankname(String sbankname) {
        this.sbankname = sbankname;
    }
    public String getIlevel() {
        return this.ilevel;
    }
    public void setIlevel(String ilevel) {
        this.ilevel = ilevel;
    }
    public String getSparentbankcode() {
        return this.sparentbankcode;
    }
    public void setSparentbankcode(String sparentbankcode) {
        this.sparentbankcode = sparentbankcode;
    }
   

}
