package tech.yaog.widgets;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * 字体缓存管理.
 * 为了对应 Google 无法释放字体内存的 bug ，无奈之下使用全局缓存处理
 * Created by mutoukenji on 17-9-13.
 */
public class FontManager {
    private static final Map<String, Typeface> fonts = new HashMap<>();

    /**
     * 取得字体.
     * 如果有缓存则从缓存读取，否则从资源文件读取并写入缓存
     * @param context Context
     * @param font 字体文件路径（对于assets文件夹的相对路径）
     * @return 字体
     */
    public static Typeface getFont(Context context, String font) {
        if (fonts.containsKey(font)) {
            return fonts.get(font);
        }
        else {
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(), font);
            fonts.put(font, typeFace);
            return typeFace;
        }
    }
}
