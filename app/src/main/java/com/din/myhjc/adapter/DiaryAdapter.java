package com.din.myhjc.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.din.myhjc.R;
import com.din.myhjc.activities.AddActivity;
import com.din.myhjc.content.ListDiary;
import com.din.myhjc.databases.DataDiary;
import com.din.myhjc.utils.DateUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListDiary> listDiary;
    private Context context;

    // item类型
    private static final int ITEM_TYPE_HEADER = 10;
    private static final int ITEM_TYPE_CONTENT = 11;
    private static final int ITEM_TYPE_NAV = 12;

    // Adapter添加类型为DataBean的List数据 ---------------
    public DiaryAdapter(List<ListDiary> listDiary, Context context) {
        this.context = context;
        this.listDiary = listDiary;
    }

    // 判断当前item类型
    public int getItemViewType(int position) {
        return listDiary.get(position).getItemType();
    }

    //--------  头部ViewHolder
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        private TextView date;

        public HeadViewHolder(View view) {
            super(view);
            date = (TextView) itemView.findViewById(R.id.date);
            // 通过接口实现判断View的Tag是否需要吸附
            view.setTag(true);
        }

        // 绑定头部数据
        public void bindHeadData(ListDiary listDiary) {
            date.setText(listDiary.getYear() + listDiary.getMonth() + listDiary.getMonth());
        }
    }

    // 内容ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView week, weather, content;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            week = (TextView) view.findViewById(R.id.week);
            weather = (TextView) view.findViewById(R.id.weather);
            content = (TextView) view.findViewById(R.id.content);
            // 通过接口实现判断View的Tag是否需要吸附
            view.setTag(false);
        }

        // 绑定中间数据
        public void bindContentData(ListDiary listDiary) {
            content.setText(listDiary.getContents());
            week.setText(listDiary.getWeek());
            weather.setText(listDiary.getWeather());
        }
    }

    // 底部ViewHolder
    public class NavViewHolder extends RecyclerView.ViewHolder {
        private TextView nav;

        public NavViewHolder(View view) {
            super(view);
            nav = (TextView) view.findViewById(R.id.nav);
            // 通过接口实现判断View的Tag是否需要吸附
            view.setTag(false);
        }

        // 绑定尾部数据
        public void bindNavData(ListDiary listDiary) {
            nav.setText(listDiary.getNav());
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_recycler_header, parent, false);
            DiaryAdapter.HeadViewHolder holder = new DiaryAdapter.HeadViewHolder(view);
            return holder;
        } else if (viewType == ITEM_TYPE_CONTENT) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_recycler, parent, false);
            final DiaryAdapter.ViewHolder holder = new DiaryAdapter.ViewHolder(view);

            // 单击事件,获取点击的item的position,并将该item的ID传到修改页面
            holder.view.setOnClickListener(v -> {
                final int position = holder.getAdapterPosition();
                ListDiary data = listDiary.get(position - 1);
                Intent intent = new Intent(context, AddActivity.class);
                intent.putExtra("id", String.valueOf(data.getId()));
                intent.putExtra("TAG", "UPDATE");
                context.startActivity(intent);
            });
            holder.view.setOnLongClickListener(v -> {
                final int position = holder.getAdapterPosition();                // 获取点击的Item的位置
                final ListDiary data = listDiary.get(position - 1);
                new android.app.AlertDialog.Builder(context).setTitle("提示").setMessage("是否删除").setCancelable(false).
                        setPositiveButton("确认", (DialogInterface dialogInterface, int i) -> {
                            DataSupport.deleteAll(DataDiary.class, "id=?", String.valueOf(data.getId()));   //  删除本地数据库里的数据
                            //  移除该行在List里存放的数据，RecyclerView及时刷新把该Item删除，刷新Recycler的Item
                            listDiary.remove(position - 1);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(0, listDiary.size() - 1);
                        }).setNegativeButton("取消", (DialogInterface dialogInterface, int i) -> {

                }).show();
                return true;
            });
            return holder;
        } else if (viewType == ITEM_TYPE_NAV) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_recycler_nav, parent, false);
            DiaryAdapter.NavViewHolder holder = new DiaryAdapter.NavViewHolder(view);
            return holder;
        }
        return null;
    }

    /**
     * 绑定数据
     *
     * @param holder   对应的父级View为子级View绑定数据
     * @param position 子级View的个数
     */
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListDiary list = listDiary.get(position);
        if (holder instanceof DiaryAdapter.HeadViewHolder) {
            HeadViewHolder vh = (HeadViewHolder) holder;
            vh.bindHeadData(list);
        } else if (holder instanceof DiaryAdapter.ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.bindContentData(list);
        } else if (holder instanceof DiaryAdapter.NavViewHolder) {
            NavViewHolder vh = (NavViewHolder) holder;
            vh.bindNavData(list);
        }
    }

    //  获取Item总数
    public int getItemCount() {
        return listDiary.size();
    }
}