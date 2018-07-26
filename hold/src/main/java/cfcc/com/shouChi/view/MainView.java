package cfcc.com.shouChi.view;

import android.widget.Button;

/**
 * Created by acer on 2017/11/21.
 */

public interface MainView {
    /**
     * 获取到各个按钮
     * @return
     */
    Button  getLogin();
    Button  conFigure();
    Button  synchronization();
    Button  Packing();
    Button  SignOut();
}
