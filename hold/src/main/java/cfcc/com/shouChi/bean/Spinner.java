package cfcc.com.shouChi.bean;

/**
 * Created by acer on 2018/3/30.
 */

public class Spinner {
   private String scashclasscode;
   private String scashclassname;

    public Spinner(String scashclasscode, String scashclassname) {
        this.scashclasscode = scashclasscode;
        this.scashclassname = scashclassname;
    }

    public String getScashclasscode() {
        return scashclasscode;
    }

    public void setScashclasscode(String scashclasscode) {
        this.scashclasscode = scashclasscode;
    }

    public String getScashclassname() {
        return scashclassname;
    }

    public void setScashclassname(String scashclassname) {
        this.scashclassname = scashclassname;
    }

    @Override
    public String toString() {
        return "Spinner{" +
                "scashclasscode='" + scashclasscode + '\'' +
                ", scashclassname='" + scashclassname + '\'' +
                '}';
    }
}
