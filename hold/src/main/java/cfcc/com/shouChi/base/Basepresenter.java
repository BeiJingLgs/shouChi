package cfcc.com.shouChi.base;


import java.lang.ref.WeakReference;

/**
 * Created by acer on 2017/11/21.
 */

public class Basepresenter<V> {

    protected WeakReference<V> reference;

    /**
     * WeakReference 防止内存泄漏
     * @param view
     */
    public  void  attachView(V view){
        reference = new WeakReference<V>(view);
    }

    /**
     * 获取到view
     * @return
     */
    protected  V   getView(){
        return   reference.get();
    }
    public void detachView(){
        if(reference!=null){
            reference.clear();
            reference = null;
        }
    }
}
