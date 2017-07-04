package tech.yaog.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import tech.yaog.widgets.numberickeypad.R;

/**
 * 3x4数字键盘
 *     可以自定义按钮文字、背景、字号字色、padding、margin
 * Created by ygl_h on 2017/7/3.
 */

public class NumericKeypad extends GridLayout implements View.OnClickListener {

    public interface OnItemClickListener {
        void onItemClick(int itemIndex, String itemText);
    }

    public static final int KEY_ONE = 0;
    public static final int KEY_TWO = 1;
    public static final int KEY_THREE = 2;
    public static final int KEY_FOUR = 3;
    public static final int KEY_FIVE = 4;
    public static final int KEY_SIX = 5;
    public static final int KEY_SEVEN = 6;
    public static final int KEY_EIGHT = 7;
    public static final int KEY_NINE = 8;
    public static final int KEY_STAR = 9;
    public static final int KEY_ZERO = 10;
    public static final int KEY_SHARP = 11;

    private static final int KEY_COUNT = 12;
    private static final int KEY_PER_LINE = 3;

    private static final int[] textColorAttrs = new int[] {
            R.styleable.NumericKeypad_textColorOne,
            R.styleable.NumericKeypad_textColorTwo,
            R.styleable.NumericKeypad_textColorThree,
            R.styleable.NumericKeypad_textColorFour,
            R.styleable.NumericKeypad_textColorFive,
            R.styleable.NumericKeypad_textColorSix,
            R.styleable.NumericKeypad_textColorSeven,
            R.styleable.NumericKeypad_textColorEight,
            R.styleable.NumericKeypad_textColorNine,
            R.styleable.NumericKeypad_textColorStar,
            R.styleable.NumericKeypad_textColorZero,
            R.styleable.NumericKeypad_textColorSharp
    };

    private static final int[] buttonBackgroundAttrs = new int[] {
            R.styleable.NumericKeypad_buttonBackgroundOne,
            R.styleable.NumericKeypad_buttonBackgroundTwo,
            R.styleable.NumericKeypad_buttonBackgroundThree,
            R.styleable.NumericKeypad_buttonBackgroundFour,
            R.styleable.NumericKeypad_buttonBackgroundFive,
            R.styleable.NumericKeypad_buttonBackgroundSix,
            R.styleable.NumericKeypad_buttonBackgroundSeven,
            R.styleable.NumericKeypad_buttonBackgroundEight,
            R.styleable.NumericKeypad_buttonBackgroundNine,
            R.styleable.NumericKeypad_buttonBackgroundStar,
            R.styleable.NumericKeypad_buttonBackgroundZero,
            R.styleable.NumericKeypad_buttonBackgroundSharp
    };

    private static final int[] textAttrs = new int[] {
            R.styleable.NumericKeypad_textOne,
            R.styleable.NumericKeypad_textTwo,
            R.styleable.NumericKeypad_textThree,
            R.styleable.NumericKeypad_textFour,
            R.styleable.NumericKeypad_textFive,
            R.styleable.NumericKeypad_textSix,
            R.styleable.NumericKeypad_textSeven,
            R.styleable.NumericKeypad_textEight,
            R.styleable.NumericKeypad_textNine,
            R.styleable.NumericKeypad_textStar,
            R.styleable.NumericKeypad_textZero,
            R.styleable.NumericKeypad_textSharp
    };

    private static final int[] textSizeAttrs = new int[] {
            R.styleable.NumericKeypad_textSizeOne,
            R.styleable.NumericKeypad_textSizeTwo,
            R.styleable.NumericKeypad_textSizeThree,
            R.styleable.NumericKeypad_textSizeFour,
            R.styleable.NumericKeypad_textSizeFive,
            R.styleable.NumericKeypad_textSizeSix,
            R.styleable.NumericKeypad_textSizeSeven,
            R.styleable.NumericKeypad_textSizeEight,
            R.styleable.NumericKeypad_textSizeNine,
            R.styleable.NumericKeypad_textSizeStar,
            R.styleable.NumericKeypad_textSizeZero,
            R.styleable.NumericKeypad_textSizeSharp
    };

    private String[] defaultButtonText = new String[]{
            "1","2","3","4","5","6","7","8","9","*","0","#"
    };
    private Button[] buttons = new Button[KEY_COUNT];
    private int buttonWidth;
    private int buttonHeight;
    private int buttonPaddingLeft;
    private int buttonPaddingRight;
    private int buttonPaddingTop;
    private int buttonPaddingBottom;
    private int buttonPaddingStart;
    private int buttonPaddingEnd;
    private int buttonMarginLeft;
    private int buttonMarginRight;
    private int buttonMarginTop;
    private int buttonMarginBottom;
    private int buttonMarginStart;
    private int buttonMarginEnd;

    private OnItemClickListener onItemClickListener;

    public NumericKeypad(Context context) {
        super(context);
        initButtons(context);
        initSelfInfo();
        initStyle(context, null, 0, 0);
    }

    public NumericKeypad(Context context, AttributeSet attrs) {
        super(context, attrs);
        initButtons(context);
        initSelfInfo();
        initStyle(context, attrs, 0, 0);
    }

    public NumericKeypad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initButtons(context);
        initSelfInfo();
        initStyle(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NumericKeypad(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initButtons(context);
        initSelfInfo();
        initStyle(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initButtons(Context context) {
        for (int i = 0 ; i< KEY_COUNT; i++) {
            buttons[i] = new Button(context);
            buttons[i].setText(defaultButtonText[i]);
            buttons[i].setGravity(Gravity.CENTER);
            this.addView(buttons[i]);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = LayoutParams.WRAP_CONTENT;
            params.height = LayoutParams.WRAP_CONTENT;

            buttons[i].setLayoutParams(params);
            buttons[i].setTag(i);
            buttons[i].setOnClickListener(this);
        }
    }

    private void initSelfInfo() {
        this.setColumnCount(KEY_PER_LINE);
    }

    private void initStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if(attrs == null) {
            return;
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumericKeypad, defStyleAttr, defStyleRes);

        try {
            buttonWidth = ta.getInt(R.styleable.NumericKeypad_buttonWidth, 0);
        }
        catch (Exception e) {
            buttonWidth = 0;
        }
        if(buttonWidth != ViewGroup.LayoutParams.MATCH_PARENT
                && buttonWidth != ViewGroup.LayoutParams.WRAP_CONTENT) {
            //noinspection ResourceType
            buttonWidth = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        try {
            buttonHeight = ta.getInt(R.styleable.NumericKeypad_buttonHeight, 0);
        }
        catch (Exception e) {
            buttonHeight = 0;
        }
        if(buttonHeight != ViewGroup.LayoutParams.MATCH_PARENT
                && buttonHeight != ViewGroup.LayoutParams.WRAP_CONTENT) {
            //noinspection ResourceType
            buttonHeight = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonHeight,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int buttonPadding = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonPadding, 0);
        buttonPaddingLeft = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonPaddingLeft, buttonPadding);
        buttonPaddingRight = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonPaddingRight, buttonPadding);
        buttonPaddingTop = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonPaddingTop, buttonPadding);
        buttonPaddingBottom = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonPaddingBottom, buttonPadding);
        buttonPaddingStart = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonPaddingStart, buttonPadding);
        buttonPaddingEnd = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonPaddingEnd, buttonPadding);
        int buttonMargin = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonMargin, 0);
        buttonMarginLeft = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonMarginLeft, buttonMargin);
        buttonMarginRight = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonMarginRight, buttonMargin);
        buttonMarginTop = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonMarginTop, buttonMargin);
        buttonMarginBottom = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonMarginBottom, buttonMargin);
        buttonMarginStart = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonMarginStart, buttonMargin);
        buttonMarginEnd = ta.getDimensionPixelSize(R.styleable.NumericKeypad_buttonMarginEnd, buttonMargin);

        updateButtonInfo();
        updateButtonPadding();

        ColorStateList allTextColor = ta.getColorStateList(R.styleable.NumericKeypad_android_textColor);
        int allBackground = ta.getResourceId(R.styleable.NumericKeypad_buttonBackground, 0);
        int allTextSize = ta.getDimensionPixelSize(R.styleable.NumericKeypad_android_textSize, 16);
        for (int i = 0;i<KEY_COUNT;i++) {
            // 获取字体颜色配置
            ColorStateList color = ta.getColorStateList(textColorAttrs[i]);
            if(color == null && allTextColor != null) {
                color = allTextColor;
            } else if (color == null) {
                color = ColorStateList.valueOf(context.getResources().getColor(android.R.color.primary_text_light));
            }
            buttons[i].setTextColor(color);
            // 按钮背景
            int background = ta.getResourceId(buttonBackgroundAttrs[i], allBackground);
            if(background == 0) {
                buttons[i].setBackground(null);
            }
            else {
                buttons[i].setBackgroundResource(background);
            }
            // 按钮文字
            String text = ta.getString(textAttrs[i]);
            if (text != null) {
                buttons[i].setText(text);
            }
            int textSize = ta.getDimensionPixelSize(textSizeAttrs[i], allTextSize);
            buttons[i].setTextSize(textSize);
        }

        ta.recycle();
    }

    private void updateButtonInfo() {

        for (int i = 0;i<KEY_COUNT;i++) {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = buttonWidth;
            params.height = buttonHeight;
            params.setMargins(buttonMarginLeft, buttonMarginTop, buttonMarginRight, buttonMarginBottom);
            params.setMarginStart(buttonMarginStart);
            params.setMarginEnd(buttonMarginEnd);
            buttons[i].setLayoutParams(params);
        }
    }

    private void updateButtonPadding() {
        for (int i = 0;i<KEY_COUNT;i++) {
            buttons[i].setPadding(buttonPaddingLeft, buttonPaddingTop, buttonPaddingRight, buttonPaddingBottom);
            buttons[i].setPaddingRelative(buttonPaddingStart, buttonPaddingTop, buttonPaddingEnd, buttonPaddingBottom);
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener != null) {
            int idx = (int) v.getTag();
            onItemClickListener.onItemClick(idx, buttons[idx].getText().toString());
        }
    }
}
