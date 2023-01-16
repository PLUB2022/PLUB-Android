package com.plub.presentation.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.plub.presentation.R;
import com.plub.presentation.util.ExtensionsKt;


@SuppressLint("AppCompatCustomView")
public class RadiusImageView extends ImageView {
    private float radius = 0;
    private float borderWidth = 0;
    private int borderColor = 0;
    private Paint paint = new Paint();

    public RadiusImageView(Context context) {
        super(context);
    }

    public RadiusImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public RadiusImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RadiusImageView);

        if (typedArray != null) {
            for (int i = 0, j = typedArray.getIndexCount(); i < j; i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.RadiusImageView_radius) {
                    radius = typedArray.getDimension(attr, 0);
                } else if (attr == R.styleable.RadiusImageView_borderWidth) {
                    borderWidth = typedArray.getDimension(attr, 0);
                } else if (attr == R.styleable.RadiusImageView_borderColor) {
                    borderColor = typedArray.getColor(attr, 0);
                }
            }

            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);

        if (borderWidth > 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(ExtensionsKt.getPx(borderWidth));
            paint.setColor(borderColor);
            canvas.drawRoundRect(rect, radius, radius, paint);
        }
    }

    public void setRadius(float radius) {
        this.radius = ExtensionsKt.getPx(radius);
    }
}