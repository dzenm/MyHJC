package com.din.myhjc.utils;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.din.myhjc.R;

/**
 * 作者：漆可 on 2016/8/31 13:53
 */
public class TransitionView extends RelativeLayout {

    private View spread; // 播放扩散动画的view
    private View line;
    private TextView showText;
    private TextView success;
    private OnAnimationEndListener mOnAnimationEndListener;

    public TransitionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransitionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //允许绘制背景，及执行onDraw()方法
//        setWillNotDraw(false);

        init();
    }

    private void init() {
        View rootView = inflate(getContext(), R.layout.transtion_view, this);

        spread = rootView.findViewById(R.id.spread);
        line = rootView.findViewById(R.id.line);
        showText = (TextView) rootView.findViewById(R.id.showText);
        success = (TextView) rootView.findViewById(R.id.success);
    }

    /**
     * 开始播放动画
     */
    public void startAnimation(String text) {
        this.setVisibility(View.VISIBLE);
        showText.setText(text);
        showText.setTranslationX(0);
        showText.setVisibility(View.INVISIBLE);
        success.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);

        //缩放动画
        AnimationHelper.spreadAni(spread, getScale(), new AnimationHelper.SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //结束后播放sign up 文字显示入场动画
                startSignUpInAni();
            }
        });
    }

    private void startSignUpInAni() {
        AnimationHelper.signUpTextInAni(showText, new AnimationHelper.SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //开启线条播放动画
                startLineAni();
            }
        });
    }

    private void startLineAni() {
        AnimationHelper.lineExpendAni(line, new AnimationHelper.SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //开启success文字入场动画
                startSuccessAni();
            }
        });
    }

    private void startSuccessAni() {
        AnimationHelper.successInAni(success, showText, new AnimationHelper.SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //回调
                if (mOnAnimationEndListener != null) {
                    mOnAnimationEndListener.onEnd();
                }
            }
        });
    }

    //计算扩散动画最终放大比例
    private float getScale() {
        //原始扩散圆的直径
        int orgWidth = spread.getMeasuredWidth();

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //扩散圆最终扩散的圆的半径
        float finalDiameter = (int) (Math.sqrt(width * width + height * height));

        //因为圆未居中，所以加1
        return finalDiameter / orgWidth + 1;
    }

    public void setOnAnimationEndListener(OnAnimationEndListener onAnimationEndListener) {
        this.mOnAnimationEndListener = onAnimationEndListener;
    }

    /**
     * 动画结束监听
     */
    public interface OnAnimationEndListener {
        void onEnd();
    }
}