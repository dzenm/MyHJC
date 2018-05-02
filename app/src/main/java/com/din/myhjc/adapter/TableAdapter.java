package com.din.myhjc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.din.myhjc.R;
import com.din.myhjc.content.ListTable;

import java.util.List;
import java.util.Random;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private List<ListTable> tables;
    private int rgb;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        //-------- 声明RecyclerView的Item里的View --------
        View view;

        TextView title, content;
        LinearLayout backgrounds;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            //-------- 随机更换每一个item的背景颜色
            Random random = new Random();
            rgb = Color.rgb(150 + random.nextInt(105), 150 + random.nextInt(105), 150 + random.nextInt(105));

            backgrounds = (LinearLayout) view.findViewById(R.id.backgrounds);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
        }
    }

    public TableAdapter(Context context, List<ListTable> tables) {
        this.context = context;
        this.tables = tables;
    }
    public TableAdapter(){

    }

    //-------- RecyclerView点击事件
    public TableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_recycler, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-------- 获取点击的Item的位置
                final int position = holder.getAdapterPosition();
                final ListTable table = tables.get(position);
                //-------- 长按出现提示Dialog菜单
                View dialog = View.inflate(context, R.layout.table_dialog, null);
                TextView title = (TextView) dialog.findViewById(R.id.title);
                TextView content = (TextView) dialog.findViewById(R.id.content);
                title.setText(table.getTitle().toString());
                content.setText(table.getContent().toString());
                content.setMovementMethod(new ScrollingMovementMethod());
                new android.app.AlertDialog.Builder(context).
                        setView(dialog).
                        setCancelable(true).show();
            }
        });
        return holder;
    }

    //-------- 获取本地数据库里的数据，为RecyclerView里的Item添加数据
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.backgrounds.setBackgroundColor(rgb);
        ListTable table = tables.get(position);
        holder.title.setText(table.getTitle());
        holder.content.setText(table.getContent());
    }

    //-------- RecyclerView添加的数据条数
    public int getItemCount() {
        return tables.size();
    }
}