package tech.yaog.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.Arrays;

/**
 * Created by mutoukenji on 17-7-25.
 */

public class ImageEditText extends android.support.v7.widget.AppCompatEditText {
    protected int drawableWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    protected int drawableHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    public ImageEditText(Context context) {
        super(context);
        initAttrs(context, null, 0, 0);
    }

    public ImageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, 0, 0);
    }

    public ImageEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr, 0);
    }

    public void setDrawableWidth(int width) {
        drawableWidth = width;
    }

    public void setDrawableHeight(int height) {
        drawableHeight = height;
    }

    public int getDrawableWidth() {
        return drawableWidth;
    }

    public int getDrawableHeight() {
        return drawableHeight;
    }

    protected void initAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = context.obtainStyledAttributes(attrs, tech.yaog.widgets.imageedittext.R.styleable.ImageEditText, defStyleAttr, defStyleRes);
        try {
            drawableWidth = ta.getInteger(tech.yaog.widgets.imageedittext.R.styleable.ImageEditText_iee_drawableWidth, -3);
        }
        catch (Exception e){
            drawableWidth = -3;
        }
        if (drawableWidth == -3) {
            //noinspection ResourceType
            drawableWidth = ta.getDimensionPixelSize(tech.yaog.widgets.imageedittext.R.styleable.ImageEditText_iee_drawableWidth, -2);
        }
        try {
            drawableHeight = ta.getInteger(tech.yaog.widgets.imageedittext.R.styleable.ImageEditText_iee_drawableHeight, -3);
        }
        catch (Exception e){
            drawableHeight = -3;
        }
        if (drawableHeight == -3) {
            //noinspection ResourceType
            drawableHeight = ta.getDimensionPixelSize(tech.yaog.widgets.imageedittext.R.styleable.ImageEditText_iee_drawableHeight, -2);
        }
        
        ta.recycle();
    }

    private ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Drawable[] drawables = ImageEditText.this.getCompoundDrawables();
            for (int i = 0;i<drawables.length;i++) {
                Drawable drawable = drawables[i];
                if (drawable != null) {
                    int width = drawableWidth;
                    int height = drawableHeight;
                    if (width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                        width = drawable.getIntrinsicWidth();
                    }
                    else if (width == ViewGroup.LayoutParams.MATCH_PARENT) {
                        if (i % 2 == 0) {
                            width = drawable.getIntrinsicWidth() * getMeasuredHeight() / drawable.getIntrinsicHeight();
                        }
                        else {
                            width = getMeasuredWidth();
                        }
                    }
                    if (height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                        height = drawable.getIntrinsicHeight();
                    }
                    else if (height == ViewGroup.LayoutParams.MATCH_PARENT) {
                        if (i % 2 == 0) {
                            height = getMeasuredHeight();
                        }
                        else {
                            height = drawable.getIntrinsicHeight() * getMeasuredWidth() / drawable.getIntrinsicWidth();
                        }
                    }
                    drawable.setBounds(0,0,width,height);
                }
            }
            if (drawables.length < 4) {
                drawables = Arrays.copyOf(drawables, 4);
            }
            ImageEditText.super.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
    }

}
