package cfcc.com.shouChi.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;


/**
 * Created by acer on 2017/11/20.
 */

public  abstract class MvpBaseActivity<V,T extends Basepresenter<V>> extends AppCompatActivity {

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getPresenter() !=null){
            presenter = getPresenter();
            presenter.attachView((V)this);
        }
        setContentView(getLayoutID());
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.detachView();
        }
    }

    /**
     * 获取到布局xml
     * @return
     */
    public  abstract  int getLayoutID();

    /**
     * 获取到Persenter
     * @return
     */
    public  abstract   T   getPresenter();
}
