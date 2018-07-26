package cfcc.com.shouChi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cfcc.com.shouChi.R;
import cfcc.com.shouChi.api.RecyclerViewClick;
import cfcc.com.shouChi.api.RecyclerViewLongClick;

/**
 * Created by acer on 2017/12/19.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHodler> {
    private Context context;
    private ArrayList<Integer> mlist;
    private RecyclerViewClick mListener;
    private RecyclerViewLongClick mLongListener;

    public MyRecyclerAdapter(Context context, ArrayList<Integer> list) {
        this.context = context;
        this.mlist = list;
    }

    public void setItemRecyclerViewClick(RecyclerViewClick listener) {
        this.mListener = listener;
    }

    public void setLongRecyclerViewClick(RecyclerViewLongClick listener) {
        this.mLongListener = listener;
    }

    @Override
    public MyRecyclerAdapter.MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        MyViewHodler hodler = new MyViewHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHodler holder, int position) {
        if (holder != null) {
            Integer integer = mlist.get(position);
            holder.tv.setText(integer.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mlist != null ? mlist.size() : 0;
    }

    class MyViewHodler extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHodler(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.text_num);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.ItemClick(view, getAdapterPosition());
                }
            });
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mLongListener.ItemLongClick(view,getAdapterPosition());
                    return true;
                }
            });
        }
    }


}