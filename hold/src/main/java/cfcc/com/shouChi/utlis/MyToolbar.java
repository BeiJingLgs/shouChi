package cfcc.com.shouChi.utlis;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cfcc.com.shouChi.R;

/**
 * Created by acer on 2017/11/23.
 */

public class MyToolbar extends Toolbar {
    Context context;
    @Bind(R.id.leftButton)
    ImageButton leftButton;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rightButton)
    Button rightButton;

    private View view;

    public MyToolbar(Context context) {
        super(context);
        this.context = context;
        initView();
        setContentInsetsRelative(10, 10);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        setContentInsetsRelative(10, 10);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        setContentInsetsRelative(10, 10);
    }

    private void initView() {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.mytoolbar, null);
            ButterKnife.bind(this, view);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(view, layoutParams);
        }
    }

    /**
     * 给左侧button设置图标
     *
     * @param icon
     */
    public void setLeftButtonIcon(int icon) {
        if (leftButton != null) {
            leftButton.setImageResource(icon);
            leftButton.setBackgroundColor(this.getResources().getColor(R.color.backgroundColor));
        }
    }

    /**
     * 给右侧设置字体 大小 颜色等等
     *
     * @param text
     */
    public void setRightButtonText(String text) {
        if (rightButton != null) {
            rightButton.setText(text);
            rightButton.setBackgroundResource(R.drawable.buttncolor);
            rightButton.setPadding(10,3,10,3);
        }
    }
    /**
     * 给右侧设置图标
     *
     */
    public void setRightButtonIcon(Drawable icon) {
        if (rightButton != null) {
            rightButton.setCompoundDrawables(null,icon,null,null);
        }
    }
    /**
     * 给title设置文本
     *
     * @param text
     */
    public void setTitleText(String text) {
        if (title != null) {
            title.setText(text);
        }
    }

    /**
     * 设置左边的点击事件
     *
     * @param lisenter
     */
    public void setLeftButtonOnClickListener(OnClickListener lisenter) {
        leftButton.setOnClickListener(lisenter);
    }

    /**
     * 设置右边的点击事件
     */
    public void setRightButtonOnClickListener(OnClickListener lisenter) {
        rightButton.setOnClickListener(lisenter);
    }

}
