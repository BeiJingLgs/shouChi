package cfcc.com.shouChi.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.Bind;
import cfcc.com.shouChi.Myappcation;
import cfcc.com.shouChi.R;
import cfcc.com.shouChi.base.MvpBaseActivity;
import cfcc.com.shouChi.database.MySqLiteHelper;
import cfcc.com.shouChi.db.DaoSession;
import cfcc.com.shouChi.db.InfoDao;
import cfcc.com.shouChi.presenter.PackPresenter;
import cfcc.com.shouChi.utlis.DbManager;
import cfcc.com.shouChi.utlis.MyToolbar;
import cfcc.com.shouChi.view.PackView;
import static cfcc.com.shouChi.utlis.DBase.initializeInstance;

/**
 * 装箱
 * Created by acer on 2017/11/23.
 */

public class PackingActivity extends MvpBaseActivity<PackView, PackPresenter> implements PackView {


    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.bianhao)
    EditText bianhao;
    @Bind(R.id.button_select)
    Button buttonSelect;
    @Bind(R.id.xiazai)
    Button xiazai;
    @Bind(R.id.time)
    EditText time;
    @Bind(R.id.bendi)
    Button bendi;
    @Bind(R.id.rv)
    RecyclerView rv;
    private MySqLiteHelper helper;
    private String jsessionid;
    private InfoDao dao;

    @Override
    public int getLayoutID() {
        return R.layout.packing_activity;
    }

    @Override
    public PackPresenter getPresenter() {
        return new PackPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = DbManager.getInstant(this);
        DaoSession session = Myappcation.getInstance().getSession();
        initializeInstance(helper);
        /**
         *生成日期
         */
        setdata();

        /**
         * 将jsessionid存储到SharedPreferences中
         */
        setSpJsessionid();
        SharedPreferences sp1= getSharedPreferences("a", Activity.MODE_PRIVATE);
        presenter.getControl(jsessionid,sp1,session);
        /**
         *信息同步
         */
        presenter.Synchronization();
        /**
         *查询
         */
        presenter.Chaxun();
        /**
         *下载订单
         */
        presenter.DownloadDiDan();
        /**
         *本地订单
         */
        presenter.BendiDingDan();
    }
    private void setSpJsessionid() {
        Intent intent = getIntent();
        jsessionid = intent.getStringExtra("jsessionid");
        SharedPreferences sp = getSharedPreferences("b", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("jsessionid", jsessionid);
        edit.commit();
    }

    private void setdata() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        time.setText(formatter.format(date));
    }

    @Override
    public MyToolbar getToolbar() {
        return toolbar;
    }

    @Override
    public Button getButtonSelect() {
        return buttonSelect;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return rv;
    }

    @Override
    public Button getDownLoadButton() {
        return xiazai;
    }

    public Button getBenDi() {
        return bendi;
    }

    @Override
    public EditText getEditText() {
        return bianhao;
    }

    @Override
    public EditText getEditTime() {
        return time;
    }

    @Override
    public MySqLiteHelper getHelper() {
        return helper;
    }

}
