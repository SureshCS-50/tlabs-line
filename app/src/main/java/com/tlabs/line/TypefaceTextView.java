package com.tlabs.line;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Sureshkumar on 16/12/16.
 */

public class TypefaceTextView extends TextView {

    public final static int ROBOTO_REGULAR = 0;
    public final static int ROBOTO_CONDENSED_BOLD = 1;
    public final static int ROBOTO_LIGHT = 2;
    public final static int ROBOTO_MEDIUM = 3;
    private final static LruCache<Integer, Typeface> sTypefaceCache = new LruCache<Integer, Typeface>(7);

    public TypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        overrideFonts(context, attrs);
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideFonts(context, attrs);
    }

    private void overrideFonts(Context context, AttributeSet attrs) {
        TypedArray values = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TypefaceTextView, 0, 0);
        try {
            //The value 0 is a default, but shouldn't ever be used since the attr is an enum
            int typefaceIndex = values.getInt(R.styleable.TypefaceTextView_typeface, 0);
            Typeface typeface = null;
            switch (typefaceIndex) {
                case ROBOTO_REGULAR:
                default:
                    typeface = sTypefaceCache.get(ROBOTO_REGULAR);
                    if (typeface == null) {
                        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular.ttf");
                        sTypefaceCache.put(typefaceIndex, typeface);
                    }
                    break;
                case ROBOTO_CONDENSED_BOLD:
                    typeface = sTypefaceCache.get(ROBOTO_CONDENSED_BOLD);
                    if (typeface == null) {
                        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed_bold.ttf");
                        sTypefaceCache.put(typefaceIndex, typeface);
                    }
                    break;
                case ROBOTO_LIGHT:
                    typeface = sTypefaceCache.get(ROBOTO_LIGHT);
                    if (typeface == null) {
                        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
                        sTypefaceCache.put(typefaceIndex, typeface);
                    }
                    break;
                case ROBOTO_MEDIUM:
                    typeface = sTypefaceCache.get(ROBOTO_MEDIUM);
                    if (typeface == null) {
                        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_medium.ttf");
                        sTypefaceCache.put(typefaceIndex, typeface);
                    }
                    break;
            }
            setTypeface(typeface);
            // Note: This flag is required for proper typeface rendering
            setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        } finally {
            values.recycle();
        }
    }
}