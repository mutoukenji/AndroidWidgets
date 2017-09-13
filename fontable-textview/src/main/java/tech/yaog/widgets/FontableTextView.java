package tech.yaog.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import tech.yaog.widgets.fontabletextview.R;

/**
 * 可定义字体的TextView
 * Created by mutoukenji on 17-8-28.
 */

public class FontableTextView extends android.support.v7.widget.AppCompatTextView {

    private String font;

    public FontableTextView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public FontableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public FontableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontableTextView, defStyleAttr, defStyleRes);

        String fontPath = typedArray.getString(R.styleable.FontableTextView_custom_font);
        if (fontPath != null) {
            setFont(fontPath);
        }

        typedArray.recycle();
    }

    public void setFont(String customFont) {
        if (customFont != null) {
            if (font == null || !font.equals(customFont)) {
                try {
                    Typeface typeFace = FontManager.getFont(getContext(), customFont);
                    setTypeface(typeFace);
                    font = customFont;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
