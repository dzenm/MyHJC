package com.din.myhjc.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.din.myhjc.R;
import com.din.myhjc.databinding.ActivityGuideBinding;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ImageView guideFirst, guideSecond, guideThird;
    private float mDistance;
    private List<View> list = new ArrayList<View>();
    private ActivityGuideBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_guide);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    private void init() {

        //  引导页三个页面
        LayoutInflater inflater = getLayoutInflater().from(GuideActivity.this);
        View guideFirst = inflater.inflate(R.layout.guide_first, null);
        ImageView imageOne = (ImageView) guideFirst.findViewById(R.id.guideFirst);
        View guideSecond = inflater.inflate(R.layout.guide_second, null);
        ImageView imageTwo = (ImageView) guideSecond.findViewById(R.id.guideSecond);
        View guideThird = inflater.inflate(R.layout.guide_third, null);
        ImageView imageThree = (ImageView) guideThird.findViewById(R.id.guideThird);

        //  获取默认Display管理
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        //  Bitmap让图片适应屏幕大小
        Bitmap bitmapOne = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.guide_first), point.x, point.y, true);
        imageOne.setImageBitmap(bitmapOne);
        Bitmap bitmapTwo = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.guide_second), point.x, point.y, true);
        imageTwo.setImageBitmap(bitmapTwo);
        Bitmap bitmapThree = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.guide_third), point.x, point.y, true);
        imageThree.setImageBitmap(bitmapThree);

        //  将三个页面添加到List
        list.add(guideFirst);
        list.add(guideSecond);
        list.add(guideThird);

        //  ViewPager适配器
        bind.guideViewPager.setAdapter(new ViewPagerAdapter(list));
        bind.startBtn.setOnClickListener(view -> {
                    startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                    finish();
                }
        );
        addPoint();
    }

    //-------- 为ViewPager设置Adapter
    public class ViewPagerAdapter extends PagerAdapter {
        private List<View> viewList;

        public ViewPagerAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }

    private void addPoint() {

        //  设置小圆点之间的宽度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 25, 0);

        //  添加小圆点资源
        guideFirst = new ImageView(this);
        guideFirst.setImageResource(R.drawable.guide_selected);
        bind.guideAll.addView(guideFirst, layoutParams);

        guideSecond = new ImageView(this);
        guideSecond.setImageResource(R.drawable.guide_selected);
        bind.guideAll.addView(guideSecond, layoutParams);

        guideThird = new ImageView(this);
        guideThird.setImageResource(R.drawable.guide_selected);
        bind.guideAll.addView(guideThird, layoutParams);

        //--------设置打开的是第一个页面
        bind.guideViewPager.setCurrentItem(0);
        bind.guidePoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = bind.guideAll.getChildAt(1).getLeft() - bind.guideAll.getChildAt(0).getLeft();
                bind.guidePoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        //-------- 小圆点随着页面动态跳转
        bind.guideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //  小白点移动的距离,并通过setLayoutParams(params)不断更新其位置
                float leftMargin = (mDistance * (position + positionOffset));
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bind.guidePoint.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                bind.guidePoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                //  页面跳转时,设置小圆点的margin
                float leftMargin = mDistance * position;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bind.guidePoint.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                bind.guidePoint.setLayoutParams(params);
                //  当页面跳转到最后一个页面时,隐藏小圆点,显示进入按钮
                if (position == 2) {
                    bind.startBtn.setVisibility(View.VISIBLE);
                    bind.guideAll.setVisibility(View.GONE);
                    bind.guidePoint.setVisibility(View.GONE);
                } else {
                    bind.startBtn.setVisibility(View.GONE);
                    bind.guideAll.setVisibility(View.VISIBLE);
                    bind.guidePoint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //  设置页面跳转效果
//        bind.guideViewPager.setPageTransformer(true, new DepthPageTransformer());
    }
    //-------- 页面跳转动画
//    public class DepthPageTransformer implements ViewPager.PageTransformer {
//        private static final float MIN_SCALE = 0.75f;
//        @Override
//        public void transformPage(View page, float position) {
//            int pageWidth = page.getWidth();
//            if (position < -1) {
//                // [-Infinity,-1)
//                // 页面远离左侧页面
//                page.setAlpha(0);
//            } else if (position <= 0) {
//                // [-1,0]
//                // 页面在由中间页滑动到左侧页面 或者 由左侧页面滑动到中间页
//                page.setAlpha(1);
//                page.setTranslationX(0);
//                page.setScaleX(1);
//                page.setScaleY(1);
//            } else if (position <= 1) {
//                // (0,1]
//                //页面在由中间页滑动到右侧页面 或者 由右侧页面滑动到中间页
//                // 淡入淡出效果
//                page.setAlpha(1 - position);
//                // 反方向移动
//                page.setTranslationX(pageWidth * -position);
//                // 0.75-1比例之间缩放
//                float scaleFactor = MIN_SCALE
//                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
//                page.setScaleX(scaleFactor);
//                page.setScaleY(scaleFactor);
//            } else { // (1,+Infinity]
//                // 页面远离右侧页面
//                page.setAlpha(0);
//            }
//        }
//    }
}