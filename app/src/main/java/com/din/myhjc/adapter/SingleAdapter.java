package com.din.myhjc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.din.myhjc.R;
import com.din.myhjc.content.Single;

import java.util.List;

/**
 * Created by dinzhenyan on 2018/4/17.
 */

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ViewHolder> {

    private List<Single> list;
    private OnItemClickListener onItemClickListener;

    @Override
    public SingleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(SingleAdapter.ViewHolder holder, int position) {
        Single single = list.get(position);
        holder.text.setText(single.getText());
        holder.imageView.setImageResource(single.getImageId());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView imageView;
        private View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            text = (TextView) view.findViewById(R.id.text);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }

    //  构造方法
    public SingleAdapter(List<Single> list) {
        this.list = list;
    }

    //  点击事件方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //  点击事件接口
    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }
}