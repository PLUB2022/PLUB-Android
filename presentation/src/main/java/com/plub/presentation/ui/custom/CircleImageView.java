package com.plub.presentation.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;

import com.plub.presentation.R;

public class CircleImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_CIRCLE_BACKGROUND_COLOR = Color.TRANSPARENT;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;

    private final RectF _drawableRect = new RectF();
    private final RectF _borderRect = new RectF();

    private final Matrix _shaderMatrix = new Matrix();
    private final Paint _bitmapPaint = new Paint();
    private final Paint _borderPaint = new Paint();
    private final Paint _circleBackgroundPaint = new Paint();

    private int _borderColor = DEFAULT_BORDER_COLOR;
    private int _borderWidth = DEFAULT_BORDER_WIDTH;
    private int _circleBackgroundColor = DEFAULT_CIRCLE_BACKGROUND_COLOR;

    private Bitmap _bitmap;
    private BitmapShader _bitmapShader;
    private int _bitmapWidth;
    private int _bitmapHeight;

    private float _drawableRadius;
    private float _borderRadius;

    private ColorFilter _colorFilter;

    private boolean _ready;
    private boolean _setupPending;
    private boolean _borderOverlay;
    private boolean _disableCircularTransformation;

    public CircleImageView(Context context) {
        super(context);

        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);

        _borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_circle_border_width, DEFAULT_BORDER_WIDTH);
        _borderColor = a.getColor(R.styleable.CircleImageView_circle_border_color, DEFAULT_BORDER_COLOR);
        _borderOverlay = a.getBoolean(R.styleable.CircleImageView_circle_border_overlay, DEFAULT_BORDER_OVERLAY);

        if (a.hasValue(R.styleable.CircleImageView_circle_background_color)) {
            _circleBackgroundColor = a.getColor(R.styleable.CircleImageView_circle_background_color,
                    DEFAULT_CIRCLE_BACKGROUND_COLOR);
        } else if (a.hasValue(R.styleable.CircleImageView_circle_fill_color)) {
            _circleBackgroundColor = a.getColor(R.styleable.CircleImageView_circle_fill_color,
                    DEFAULT_CIRCLE_BACKGROUND_COLOR);
        }

        a.recycle();

        init();
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        _ready = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new OutlineProvider());
        }

        if (_setupPending) {
            setup();
            _setupPending = false;
        }
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (_disableCircularTransformation) {
            super.onDraw(canvas);
            return;
        }

        if (_bitmap == null) {
            return;
        }

        if (_circleBackgroundColor != Color.TRANSPARENT) {
            canvas.drawCircle(_drawableRect.centerX(), _drawableRect.centerY(), _drawableRadius, _circleBackgroundPaint);
        }
        canvas.drawCircle(_drawableRect.centerX(), _drawableRect.centerY(), _drawableRadius, _bitmapPaint);
        if (_borderWidth > 0) {
            canvas.drawCircle(_borderRect.centerX(), _borderRect.centerY(), _borderRadius, _borderPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        setup();
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        setup();
    }

    public int getBorderColor() {
        return _borderColor;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        if (borderColor == _borderColor) {
            return;
        }

        _borderColor = borderColor;
        _borderPaint.setColor(_borderColor);
        invalidate();
    }

    public int getCircleBackgroundColor() {
        return _circleBackgroundColor;
    }

    public void setCircleBackgroundColor(@ColorInt int circleBackgroundColor) {
        if (circleBackgroundColor == _circleBackgroundColor) {
            return;
        }

        _circleBackgroundColor = circleBackgroundColor;
        _circleBackgroundPaint.setColor(circleBackgroundColor);
        invalidate();
    }

    public void setCircleBackgroundColorResource(@ColorRes int circleBackgroundRes) {
        setCircleBackgroundColor(ResourcesCompat.getColor(getResources(), circleBackgroundRes, null));
    }


    public void setBorderWidth(int borderWidth) {
        if (borderWidth == _borderWidth) {
            return;
        }

        _borderWidth = borderWidth;
        setup();
    }

    public boolean isBorderOverlay() {
        return _borderOverlay;
    }

    public void setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == _borderOverlay) {
            return;
        }

        _borderOverlay = borderOverlay;
        setup();
    }

    public boolean isDisableCircularTransformation() {
        return _disableCircularTransformation;
    }

    public void setDisableCircularTransformation(boolean disableCircularTransformation) {
        if (_disableCircularTransformation == disableCircularTransformation) {
            return;
        }

        _disableCircularTransformation = disableCircularTransformation;
        initializeBitmap();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initializeBitmap();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == _colorFilter) {
            return;
        }

        _colorFilter = cf;
        applyColorFilter();
        invalidate();
    }

    @Override
    public ColorFilter getColorFilter() {
        return _colorFilter;
    }

    private void applyColorFilter() {
        if (_bitmapPaint != null) {
            _bitmapPaint.setColorFilter(_colorFilter);
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initializeBitmap() {
        if (_disableCircularTransformation) {
            _bitmap = null;
        } else {
            _bitmap = getBitmapFromDrawable(getDrawable());
        }
        setup();
    }

    private void setup() {
        if (!_ready) {
            _setupPending = true;
            return;
        }

        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }

        if (_bitmap == null) {
            invalidate();
            return;
        }

        _bitmapShader = new BitmapShader(_bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        _bitmapPaint.setAntiAlias(true);
        _bitmapPaint.setShader(_bitmapShader);

        _borderPaint.setStyle(Paint.Style.STROKE);
        _borderPaint.setAntiAlias(true);
        _borderPaint.setColor(_borderColor);
        _borderPaint.setStrokeWidth(_borderWidth);

        _circleBackgroundPaint.setStyle(Paint.Style.FILL);
        _circleBackgroundPaint.setAntiAlias(true);
        _circleBackgroundPaint.setColor(_circleBackgroundColor);

        _bitmapHeight = _bitmap.getHeight();
        _bitmapWidth = _bitmap.getWidth();

        _borderRect.set(calculateBounds());
        _borderRadius = Math.min((_borderRect.height() - _borderWidth) / 2.0f, (_borderRect.width() - _borderWidth) / 2.0f);

        _drawableRect.set(_borderRect);
        if (!_borderOverlay && _borderWidth > 0) {
            _drawableRect.inset(_borderWidth - 1.0f, _borderWidth - 1.0f);
        }
        _drawableRadius = Math.min(_drawableRect.height() / 2.0f, _drawableRect.width() / 2.0f);

        applyColorFilter();
        updateShaderMatrix();
        invalidate();
    }

    private RectF calculateBounds() {
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        _shaderMatrix.set(null);

        if (_bitmapWidth * _drawableRect.height() > _drawableRect.width() * _bitmapHeight) {
            scale = _drawableRect.height() / (float) _bitmapHeight;
            dx = (_drawableRect.width() - _bitmapWidth * scale) * 0.5f;
        } else {
            scale = _drawableRect.width() / (float) _bitmapWidth;
            dy = (_drawableRect.height() - _bitmapHeight * scale) * 0.5f;
        }

        _shaderMatrix.setScale(scale, scale);
        _shaderMatrix.postTranslate((int) (dx + 0.5f) + _drawableRect.left, (int) (dy + 0.5f) + _drawableRect.top);

        _bitmapShader.setLocalMatrix(_shaderMatrix);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class OutlineProvider extends ViewOutlineProvider {

        @Override
        public void getOutline(View view, Outline outline) {
            Rect bounds = new Rect();
            _borderRect.roundOut(bounds);
            outline.setRoundRect(bounds, bounds.width() / 2.0f);
        }

    }
}
