package com.din.myhjc.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cpf on 2018/1/16.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 吸附的itemView
     */
    private View mStickyItemView;

    /**
     * 吸附itemView 距离顶部
     */
    private int mStickyItemViewMarginTop;

    /**
     * 吸附itemView 高度
     */
    private int mStickyItemViewHeight;

    /**
     * 通过它获取到需要吸附view的相关信息
     */
    private StickyItemView mStickyView;

    /**
     * 滚动过程中当前的UI是否可以找到吸附的view
     */
    private boolean mCurrentUIFindStickView;

    /**
     * adapter
     */
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    /**
     * viewHolder
     */
    private RecyclerView.ViewHolder mViewHolder;

    /**
     * position list
     */
    private List<Integer> mStickyPositionList = new ArrayList<>();

    /**
     * layout manager
     */
    private LinearLayoutManager mLayoutManager;

    /**
     * 绑定数据的position
     */
    private int mBindDataPosition = -1;

    /**
     *
     */
    private List<View> mCurrentStickyItemCount = new ArrayList<>();

    /**
     * paint
     */
    private Paint mPaint;

    public StickyItemDecoration() {
        mStickyView = new StickyItemView();
        initPaint();
    }

    /**
     * init paint
     */
    private void initPaint() {
        mPaint = new Paint();
        // 设置抗锯齿
        mPaint.setAntiAlias(true);
    }


    /**
     * 大致思路就是：
     * 在列表滚动的时候会进入onDrawOver方法，然后循环当前列表的ItemView，如果遇到是吸附的Item View, 通过适配器再根据itemType来创建一个ViewHolder，并且得到这个ViewHolder的itemView；
     * 循环的时候需要不断去缓存吸附View所在RecyclerView中的下标位置position，根据View距离顶部的高度来得到当前吸附View的position；
     * 接下来通过adapter的onBindViewHolder来给ViewHolder的itemView绑定数据，然后计算itemView的宽高,这样吸附的View拿到了，数据也绑定好了；
     * 然后再计算距离顶部的高度，把itemView绘制到屏幕上即可。
     * 如果因为在当前列表中没有找到吸附的itemView（mCurrentUIFindStickView=false），就直接绘制上一个即可。
     *
     * @param canvas
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

        if (parent.getAdapter().getItemCount() <= 0) {
            return;
        }

        // 得到当前RecyclerView的布局管理器
        mLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
        mCurrentUIFindStickView = false;

        for (int m = 0, size = parent.getChildCount(); m < size; m++) {
            View view = parent.getChildAt(m);

            /**
             *  这里就用到了StickyItemView的isStickyView方法
             *  用来判断是否是需要吸附效果的View
             *  是的话才会进入到if逻辑当中
             */
            if (mStickyView.isStickyView(view)) {
                // 当前UI当中是否找到了需要吸附的View，此时设置为true
                mCurrentUIFindStickView = true;

                // 当前视图里吸附的View添加到List
                mCurrentStickyItemCount.add(view);

                // 这个方法是得到吸附View的viewHolder
                getStickyViewHolder(parent);

                // 缓存需要吸附的View在列表当中的下标position
                cacheStickyViewPosition(m);
                Log.d("TAG", "===============onDrawOver: " + m);

                // 如果当前吸附的view距离 顶部小于等于0，然后给吸附的View绑定数据，计算View的宽高
                if (view.getTop() <= 0) {
                    bindDataForStickyView(mLayoutManager.findFirstVisibleItemPosition(), parent.getMeasuredWidth());
                } else {
                    // 如果大于0，从position缓存中取得当前的position，然后绑定数据，计算View的宽高
                    if (mStickyPositionList.size() > 0) {
                        // 获取当前吸附的View的position
                        int currentPosition = getStickyViewPositionOfRecyclerView(m);
                        // 最后一个吸附的View的position
                        int indexOfCurrentPosition = mStickyPositionList.lastIndexOf(currentPosition);
                        // 绑定数据，计算View的宽高
                        bindDataForStickyView(mStickyPositionList.get(indexOfCurrentPosition), parent.getMeasuredWidth());
                        Log.d("TAG", "===============onDrawOver: ");
                    }
                }

                // 计算吸附的View距离顶部的高度
                if (view.getTop() > 0 && view.getTop() <= mStickyItemViewHeight) {
                    mStickyItemViewMarginTop = mStickyItemViewHeight - view.getTop();
                } else {
                    mStickyItemViewMarginTop = 0;
                    View nextStickyView = getNextStickyView(parent);
                    if (nextStickyView != null && nextStickyView.getTop() <= mStickyItemViewHeight) {
                        mStickyItemViewMarginTop = mStickyItemViewHeight - nextStickyView.getTop();
                    }
                }
                // 绘制吸附的View
                drawStickyItemView(canvas);
                break;
            }
        }

        // 如果在当前的列表视图中没有找到需要吸附的View
        if (!mCurrentUIFindStickView) {
            mStickyItemViewMarginTop = 0;
            // 如果已经滑动到底部了，就绑定最后一个缓存的position的View，这种情况一般出现在快速滑动列表的时候吸附View出现错乱，所以需要绑定一下
            if (mLayoutManager.findFirstVisibleItemPosition() + parent.getChildCount() == parent.getAdapter().getItemCount() && mStickyPositionList.size() > 0) {
                bindDataForStickyView(mStickyPositionList.get(mStickyPositionList.size() - 1), parent.getMeasuredWidth());
            }
            // 绘制View
            drawStickyItemView(canvas);
        }
    }

    /**
     * 得到下一个吸附View
     *
     * @param parent
     * @return
     */
    private View getNextStickyView(RecyclerView parent) {
        int num = 0;
        View nextStickyView = null;
        for (int m = 0, size = parent.getChildCount(); m < size; m++) {
            View view = parent.getChildAt(m);
            if (mStickyView.isStickyView(view)) {
                nextStickyView = view;
                num++;
            }
            if (num == 2) break;
        }
        return nextStickyView;
    }

    /**
     * 给StickyView绑定数据
     *
     * @param position
     */
    private void bindDataForStickyView(int position, int width) {
        if (mBindDataPosition == position || mViewHolder == null) {
            return;
        }
        mBindDataPosition = position;
        mAdapter.onBindViewHolder(mViewHolder, mBindDataPosition);
        measureLayoutStickyItemView(width);
        mStickyItemViewHeight = mViewHolder.itemView.getBottom() - mViewHolder.itemView.getTop();
    }

    /**
     * 缓存吸附的view position
     *
     * @param m
     */
    private void cacheStickyViewPosition(int m) {
        int position = getStickyViewPositionOfRecyclerView(m);
        if (!mStickyPositionList.contains(position)) {
            mStickyPositionList.add(position);
        }
    }

    /**
     * 得到吸附view在RecyclerView中 的position
     *
     * @param m
     * @return
     */
    private int getStickyViewPositionOfRecyclerView(int m) {
        return mLayoutManager.findFirstVisibleItemPosition() + m;
    }

    /**
     * 得到吸附viewHolder
     *
     * @param recyclerView
     */
    private void getStickyViewHolder(RecyclerView recyclerView) {
        if (mAdapter != null) {
            return;
        }
        mAdapter = recyclerView.getAdapter();
        mViewHolder = mAdapter.onCreateViewHolder(recyclerView, mStickyView.getStickViewType());
        mStickyItemView = mViewHolder.itemView;
    }

    /**
     * 计算布局吸附的itemView
     *
     * @param parentWidth
     */
    private void measureLayoutStickyItemView(int parentWidth) {
        if (mStickyItemView == null || !mStickyItemView.isLayoutRequested()) return;

        int widthSpec = View.MeasureSpec.makeMeasureSpec(parentWidth, View.MeasureSpec.EXACTLY);
        int heightSpec;

        ViewGroup.LayoutParams layoutParams = mStickyItemView.getLayoutParams();
        if (layoutParams != null && layoutParams.height > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }

        mStickyItemView.measure(widthSpec, heightSpec);
        mStickyItemView.layout(0, 0, mStickyItemView.getMeasuredWidth(), mStickyItemView.getMeasuredHeight());
    }

    /**
     * 绘制吸附的itemView
     *
     * @param canvas
     */
    private void drawStickyItemView(Canvas canvas) {
        if (mStickyItemView == null) return;

        int saveCount = canvas.save();
        canvas.translate(0, -mStickyItemViewMarginTop);
        mStickyItemView.draw(canvas);
        canvas.restoreToCount(saveCount);
    }


    // 吸附接口实现的方法回调
    public class StickyItemView implements StickyView {

        @Override
        public boolean isStickyView(View view) {
            return (Boolean) view.getTag();
        }

        @Override
        public int getStickViewType() {
            return 10;
        }
    }

    // 吸附使用的接口, 这两个方法会在ItemDecoration的绘制方onDrawOver法当中用到
    public interface StickyView {

        /**
         * 是否是吸附view
         *
         * @param view
         * @return
         */
        boolean isStickyView(View view);

        /**
         * 返回需要吸附View的itemType是多少。
         *
         * @return
         */
        int getStickViewType();
    }
}