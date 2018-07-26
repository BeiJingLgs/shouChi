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
import cfcc.com.shouChi.db.BaoCun;

/**
 * Created by acer on 2018/1/12.
 */

public class BoxRecyclerAdapter   extends RecyclerView.Adapter<BoxRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<BaoCun>  mList;
    private RecyclerViewClick mListener;
    private RecyclerViewLongClick  mLongListener;
    public BoxRecyclerAdapter(Context  context, List<BaoCun> list) {
        this.mContext=context;
        this.mList=list;
    }

    @Override
    public BoxRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.box_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return  holder;
    }
    public  void  setOnClickListener(RecyclerViewClick  listener){
        this.mListener=listener;
    }
    public  void  setOnLongClickListener(RecyclerViewLongClick  listener){
        this.mLongListener=listener;
    }
    @Override
    public void onBindViewHolder(BoxRecyclerAdapter.MyViewHolder holder, int position) {
        if (holder!=null){
            BaoCun cun = mList.get(position);
            holder.qb.setText(cun.getScashclassname());
            holder.zzx.setText(cun.getSbinlogicalid());
            holder.bz.setText(cun.getSorderkind());
        }
    }

    @Override
    public int getItemCount() {
        return  mList !=null?mList.size():0;
    }
    class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView zzx,qb,bz;
        public MyViewHolder(View itemView) {
            super(itemView);
            zzx=itemView.findViewById(R.id.zhouzhuanxiang_bianhao);
            qb=itemView.findViewById(R.id.quan_bie);
            bz=itemView.findViewById(R.id.bi_zhong);
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
