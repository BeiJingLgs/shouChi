package cfcc.com.shouChi.utlis;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by acer on 2018/1/9.
 */

public class DBase {
    //解决多线程并发
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static DBase instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private DBase(){

    }

    /**
     * 初始化
     * @param helper
     */
    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DBase();
            mDatabaseHelper = helper;
        }
    }

    /**
     * 获得当前实例对象
     * @return
     */
    public static synchronized DBase getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                    DBase.class.getSimpleName()
                            + " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    /**
     * 打开数据库对象
     * @return
     */
    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }
    /**
     * 多线程下关闭
     */
    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();

        }
    }
}
