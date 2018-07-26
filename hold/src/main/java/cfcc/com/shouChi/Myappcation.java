package cfcc.com.shouChi;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.bulong.rudeness.RudenessScreenHelper;

import cfcc.com.shouChi.db.DaoMaster;
import cfcc.com.shouChi.db.DaoSession;


/**
 * Created by acer on 2018/3/15.
 */

public class Myappcation extends Application {
    //静态单例
    public static Myappcation instance;
    private SQLiteDatabase db;
    private DaoSession session;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        new RudenessScreenHelper(this, 1000).activate();
        setDatabase();
    }

    public static Myappcation getInstance() {
        return instance;
    }

    /**
     * 设置数据库
     */
    public void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "info.db", null);
        db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        session = master.newSession();
    }
    public DaoSession getSession(){
        return session;
    }
    public  SQLiteDatabase getDb(){
        return db;
    }
}
