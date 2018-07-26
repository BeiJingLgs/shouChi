package cfcc.com.shouChi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cfcc.com.shouChi.utlis.Constant;

/**
 * Created by acer on 2018/1/9.
 */

public class MySqLiteHelper extends SQLiteOpenHelper {
    public MySqLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MySqLiteHelper(Context context) {
        super(context,"ka.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 机构信息
         */
        db.execSQL(Constant.InstInfo);
        /**
         * 复核人信息
         */
        db.execSQL(Constant.UdLockbinPerson);
        /**
         * 券别信息
         */
        db.execSQL(Constant.Um8CashClassInfo);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
