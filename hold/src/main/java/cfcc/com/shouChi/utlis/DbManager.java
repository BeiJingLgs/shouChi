package cfcc.com.shouChi.utlis;

import android.content.Context;

import cfcc.com.shouChi.database.MySqLiteHelper;

/**
 * Created by acer on 2017/12/6.
 */

public class DbManager {
    private static MySqLiteHelper helper;

    public static MySqLiteHelper getInstant(Context context) {
        if (helper == null) {
            helper = new MySqLiteHelper(context);
        }
        return helper;
    }

    public static class DbManagerHolder {
        static final DbManager manager = new DbManager();
    }

    public static DbManager getInstant() {
        return DbManagerHolder.manager;
    }
}
