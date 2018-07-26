package cfcc.com.shouChi.url;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfcc.com.shouChi.bean.Spinner;
import cfcc.com.shouChi.utlis.Constant;

/**
 * Created by acer on 2018/3/30.
 */

public class DbManager {
    public  static List<Spinner>  getListCursor(Cursor cursor){
        List<Spinner> list=new ArrayList<>();
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(Constant.SCASHCLASSNAME);
            String scashclassname = cursor.getString(index);
            int index1 = cursor.getColumnIndex(Constant.SCASHCLASSCODE);
            String scashclasscode = cursor.getString(index1);
            Spinner spinner = new Spinner( scashclasscode,scashclassname);
            list.add(spinner);
        }
        return list;
    }
}
