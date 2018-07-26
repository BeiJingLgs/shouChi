package cfcc.com.shouChi.view;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import cfcc.com.shouChi.database.MySqLiteHelper;
import cfcc.com.shouChi.utlis.MyToolbar;

/**
 * Created by acer on 2017/12/5.
 */

public interface PackView {
    MyToolbar  getToolbar();
    /**
     * 查询
     * @return
     */
    Button     getButtonSelect();
    RecyclerView  getRecyclerView();
    Button     getDownLoadButton();
    Button     getBenDi();
    EditText   getEditText();
    EditText   getEditTime();
    MySqLiteHelper getHelper();
}
