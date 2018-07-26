package cfcc.com.shouChi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cfcc.com.shouChi.R;
import cfcc.com.shouChi.api.RecyclerViewClick;
import cfcc.com.shouChi.api.RecyclerViewLongClick;
import cfcc.com.shouChi.db.DingDan;

/**
 * Created by acer on 2018/1/3.
 */

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.MyViewHodler> {
    private  Context  mContext;
    private  List<DingDan> mList;
    private  RecyclerViewClick  mListener;
    private  RecyclerViewLongClick  mLongListener;
    public RecyclerAdapter(Context context, List<DingDan> list) {
        this.mContext=context;
        this.mList= list;
    }
    public  void   setItemRecyclerViewClick(RecyclerViewClick   listener){
        this.mListener=listener;
    }
    public   void  setLongRecyclerViewClick(RecyclerViewLongClick LongListener){
        this.mLongListener=LongListener;
    }
    @Override
    public RecyclerAdapter.MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent, false);
        MyViewHodler hodler = new MyViewHodler(view);
        return  hodler;
    }
    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHodler holder, int position) {
       if (holder !=null){
           DingDan dan = mList.get(position);
           holder.ddbh.setText(dan.getSorderno());
           holder.jgmc.setText(dan.getMingcheng());
           holder.scrq.setText(dan.getDprocessdate());
           holder.sqrq.setText(dan.getExcutedate());
       }
    }
    @Override
    public int getItemCount() {
        return mList !=null?mList.size():0;
    }
    class   MyViewHodler  extends RecyclerView.ViewHolder{
        TextView  ddbh,jgmc,scrq,jehj,sqrq;
        public MyViewHodler(View itemView) {
            super(itemView);
            ddbh=itemView.findViewById(R.id.dingdan_bianhao);
            jgmc=itemView.findViewById(R.id.jigou_mingcheng);
            scrq=itemView.findViewById(R.id.shengcheng_riqi);
            sqrq=itemView.findViewById(R.id.shenqing_riqi);
            /**
             * 点击当前行的时候
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.ItemClick(view,getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                     mLongListener.ItemLongClick(view,getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
