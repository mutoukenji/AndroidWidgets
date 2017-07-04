package tech.yaog.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tech.yaog.widgets.imagetextbutton.R;

/**
 * 图像文字混合按钮
 * Created by ygl_h on 2017/7/2.
 */

public class ImageTextButton extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    private int drawablePadding;
    private int drawableWidth;
    private int drawableHeight;
    private int drawablePosition;

    public static final int POSITION_TOP = Gravity.TOP;
    public static final int POSITION_BOTTOM = Gravity.BOTTOM;
    public static final int POSITION_LEFT = Gravity.LEFT;
    public static final int POSITION_RIGHT = Gravity.RIGHT;
    public static final int POSITION_START = Gravity.START;
    public static final int POSITION_END = Gravity.END;

    public ImageTextButton(Context context) {
        super(context);
        createWidgets(context);
        init(context,null,0,0);
    }

    public ImageTextButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createWidgets(context);
        init(context,attrs,0,0);
    }

    public ImageTextButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createWidgets(context);
        init(context,attrs,defStyleAttr,0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageTextButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        createWidgets(context);
        init(context,attrs,defStyleAttr,defStyleRes);
    }

    private void createWidgets(Context context) {
        imageView = new ImageView(context);
        textView = new TextView(context);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.addView(imageView);
        this.addView(textView);
        this.setGravity(Gravity.CENTER);
        if(attrs == null) {
            return;
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageTextButton, defStyleAttr, defStyleRes);
        String text = ta.getString(R.styleable.ImageTextButton_android_text);
        textView.setText(text);
        float textSize = ta.getDimension(R.styleable.ImageTextButton_android_textSize, 0);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        int color = ta.getColor(R.styleable.ImageTextButton_android_color, 0);
        textView.setTextColor(color);
        int imageResourceId = ta.getResourceId(R.styleable.ImageTextButton_android_drawable, 0);
        imageView.setImageResource(imageResourceId);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);

        //noinspection ResourceType
        drawableWidth = ta.getDimensionPixelSize(R.styleable.ImageTextButton_drawableWidth, -2);
        //noinspection ResourceType
        drawableHeight = ta.getDimensionPixelSize(R.styleable.ImageTextButton_drawableHeight, -2);
        if(drawableWidth == -2) {
            drawableWidth = LayoutParams.WRAP_CONTENT;
        }
        else if(drawableWidth == -1) {
            drawableWidth = LayoutParams.MATCH_PARENT;
        }
        if(drawableHeight == -2) {
            drawableHeight = LayoutParams.WRAP_CONTENT;
        }
        else if(drawableHeight == -1) {
            drawableHeight = LayoutParams.MATCH_PARENT;
        }
        drawablePadding = ta.getDimensionPixelSize(R.styleable.ImageTextButton_drawablePadding, 0);
        drawablePosition = ta.getInt(R.styleable.ImageTextButton_drawablePosition, POSITION_LEFT);
        shiftPosition();
        setDrawableParams();
        ta.recycle();
    }

    private void setDrawableParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(drawableWidth, drawableHeight);
        switch (drawablePosition) {
            case POSITION_TOP:
            case POSITION_BOTTOM:
                params.gravity = Gravity.CENTER_HORIZONTAL;
                break;
            default:
                params.gravity = Gravity.CENTER_VERTICAL;
        }
        params.gravity |= drawablePosition;
        float l = 0,r = 0,t = 0,b = 0;
        if(drawablePosition == POSITION_TOP) {
            b = drawablePadding;
        }
        else if(drawablePosition == POSITION_BOTTOM) {
            t = drawablePadding;
        }
        else if(drawablePosition == POSITION_LEFT) {
            r = drawablePadding;
        }
        else if(drawablePosition == POSITION_RIGHT) {
            l = drawablePadding;
        }
        params.setMargins((int)l,(int)r,(int)t,(int)b);
        if(drawablePosition == POSITION_START) {
            params.setMarginEnd(drawablePadding);
        }
        else if(drawablePosition == POSITION_END) {
            params.setMarginStart(drawablePadding);
        }
        imageView.setLayoutParams(params);
    }

    private void shiftPosition() {
        switch (drawablePosition) {
            case POSITION_TOP:
            case POSITION_BOTTOM:
                this.setOrientation(VERTICAL);
                break;
            default:
                this.setOrientation(HORIZONTAL);
                break;
        }
    }
}
