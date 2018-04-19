package com.din.myhjc.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class StationeryEditText extends android.support.v7.widget.AppCompatEditText {

    private Paint mPaint;

    public StationeryEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.GRAY);

        int leftPad = this.getPaddingLeft();
        int rightPad = this.getPaddingRight();
        int topPad = this.getPaddingTop();


        float size = this.getTextSize();
        float baseTop = topPad + size / 8;

        int lineCount = getLineCount();
        int height = getHeight();
        int lineHeight = getLineHeight();
        int count = height / lineHeight + 1;
        if (lineCount < count) {
            lineCount = count;
        }
        for (int i = 1; i <= lineCount; i++) {
            canvas.drawLine(leftPad                         // startX
                    , baseTop + lineHeight * i        // startY
                    , getWidth() - rightPad           // endX
                    , baseTop + lineHeight * i        // endY
                    , mPaint);
            canvas.save();
        }
    }
}
