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
import com.din.myhjc.activities.AddActivity;
import com.din.myhjc.content.ListDiary;
import com.din.myhjc.databases.DataDiary;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListDiary> listDiary;
    private Context context;
    private List<Integer> list = new ArrayList<>();


    //--------  item类型
    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private static final int ITEM_TYPE_NAV = 2;

    //--------  头部、底部View个数
    private static int navCount = 1;
    private static int headerCount = 1;

    //-------- Adapter添加类型为DataBean的List数据 ---------------
    public DiaryAdapter(List<ListDiary> listDiary, Context context) {
        this.context = context;
        this.listDiary = listDiary;
    }

    //--------  内容长度
    public int getContentItemCount() {
        return listDiary.size();
    }

    //--------  判断当前item类型
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (headerCount != 0 && position < headerCount) {
            return ITEM_TYPE_HEADER;
        } else if (navCount != 0 && position >= (headerCount + dataItemCount)) {
            return ITEM_TYPE_NAV;
        } else {
            return ITEM_TYPE_CONTENT;
        }
    }

    //--------  头部ViewHolder
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        public HeadViewHolder(View view) {
            super(view);
        }
    }

    //--------  内容ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView month, day, week, weather, content;
        ;

        public ViewHolder(View view) {

            super(view);
            this.view = view;
            month = (TextView) view.findViewById(R.id.month);
            day = (TextView) view.findViewById(R.id.day);
            week = (TextView) view.findViewById(R.id.week);
            weather = (TextView) view.findViewById(R.id.weather);
            content = (TextView) view.findViewById(R.id.content);

        }
    }

    //--------  底部ViewHolder
    public class NavViewHolder extends RecyclerView.ViewHolder {
        public NavViewHolder(View view) {
            super(view);
        }
    }

    /**
     * 新建父级View视图(Layout)并设置点击事件
     *
     * @param parent   对应的父级Layout
     * @param viewType 对应的ViewItem个数
     * @return 对应的View
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_header, parent, false);
            DiaryAdapter.HeadViewHolder holder = new DiaryAdapter.HeadViewHolder(view);
            return holder;
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_diary, parent, false);
            final DiaryAdapter.ViewHolder holder = new DiaryAdapter.ViewHolder(view);

            //-------- 单击事件,获取点击的item的position,并将该item的ID传到修改页面,修改页面读取ID之后从litepal获取该行的数据
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = holder.getAdapterPosition();
                    ListDiary data = listDiary.get(position - 1);
                    Intent intent = new Intent(context, AddActivity.class);
                    intent.putExtra("id", String.valueOf(data.getId()));
                    intent.putExtra("TAG", "UPDATE");
                    context.startActivity(intent);
                }
            });

            //-------- 长按事件,删除该行
            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    //-------- 获取点击的Item的位置
                    final int position = holder.getAdapterPosition();
                    final ListDiary data = listDiary.get(position - 1);

                    //-------- 长按出现提示Dialog菜单
                    new android.app.AlertDialog.Builder(context).setTitle("提示").setMessage("是否删除").setCancelable(false).
                            setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //  删除本地数据库里的数据
                                    DataSupport.deleteAll(DataDiary.class, "id=?", String.valueOf(data.getId()));

                                    //  移除该行在List里存放的数据，RecyclerView及时刷新把该Item删除，刷新Recycler的Item
                                    listDiary.remove(position - 1);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(0, listDiary.size() - 1);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                    return true;
                }
            });
            return holder;
        } else if (viewType == ITEM_TYPE_NAV) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_nav, parent, false);
            DiaryAdapter.NavViewHolder holder = new DiaryAdapter.NavViewHolder(view);
            return holder;
        }
        return null;
    }

    private String temp = "0000";

    /**
     * 绑定数据
     *
     * @param holder   对应的父级View为子级View绑定数据
     * @param position 子级View的个数
     */
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DiaryAdapter.HeadViewHolder) {
        } else if (holder instanceof DiaryAdapter.ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            int currentPosition = position - headerCount;
            ListDiary list = listDiary.get(currentPosition);

            vh.month.setText(list.getMonth());
            vh.day.setText(list.getDay());
            vh.content.setText(list.getContents());
            vh.week.setText(list.getWeek());
            vh.weather.setText(list.getWeather());

            vh.month.setVisibility(View.VISIBLE);
            vh.day.setVisibility(View.VISIBLE);
            if (temp.equals(list.getDay())) {
                vh.month.setVisibility(View.GONE);
                vh.day.setVisibility(View.GONE);
            } else {
                temp = list.getDay();
            }
        } else if (holder instanceof DiaryAdapter.NavViewHolder) {
        }
    }

    //  获取Item总数
    public int getItemCount() {
        return headerCount + getContentItemCount() + navCount;
    }
}