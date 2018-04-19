package com.din.myhjc.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.din.myhjc.R;
import com.din.myhjc.activities.AddNoteActivity;
import com.din.myhjc.content.ListNote;
import com.din.myhjc.databases.DataNote;

import org.litepal.crud.DataSupport;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<ListNote> lists;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView title;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
        }
    }

    public NoteAdapter(Context context, List<ListNote> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.view.setOnClickListener(v -> {

            final int position = holder.getAdapterPosition();
            final ListNote listNote = lists.get(position);
            Intent intent = new Intent(context, AddNoteActivity.class);
            intent.putExtra("id", String.valueOf(listNote.getId()));
            intent.putExtra("TAG", "UPDATE");
            context.startActivity(intent);
        });

        holder.view.setOnLongClickListener(v -> {

            final int position = holder.getAdapterPosition();
            final ListNote listNote = lists.get(position);

            //-------- 长按出现提示Dialog菜单
            new android.app.AlertDialog.Builder(context).setTitle("提示").setMessage("是否删除").setCancelable(false).
                    setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //  删除本地数据库里的数据
                            DataSupport.deleteAll(DataNote.class, "id=?", String.valueOf(listNote.getId()));
                            //  remove该行在List里存放的数据，RecyclerView及时刷新把该Item删除，刷新Recycler的Item
                            lists.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(0, lists.size() - 1);
                        }
                    }).
                    setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
            return true;
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        ListNote listNote = lists.get(position);
        holder.title.setText(listNote.getTitle());
        holder.content.setText(listNote.getContent());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
