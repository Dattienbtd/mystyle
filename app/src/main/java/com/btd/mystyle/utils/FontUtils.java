package com.btd.mystyle.utils;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.btd.mystyle.App;

import java.util.Hashtable;


/**
 * Created by FRAMGIA\bui.tien.dat on 08/02/2017.
 */

public class FontUtils {

    private static final String PATH_FONT = "fonts/";

    public enum FontStyle {
        DESIGN_ICON("Material-Design-Iconic-Font.ttf"),
        ROBOTO_BOLD("Roboto-Bold.ttf"),
        ROBOTO_ITALIC("Roboto-Italic.ttf"),
        ROBOTO_MEDIUM("Roboto-Medium.ttf"),
        ROBOTO_REGULAR("Roboto-Regular.ttf"),
        ROBOTO_LIGHT("Roboto-Light.ttf"),
        DALMAIS("Dalmais.otf"),
        STYLE_ITEM("Flaticon.ttf");

        private String name;

        FontStyle(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface getTypeface(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        Typeface typeface = fontCache.get(name);
        if (typeface == null) {
            try {
                typeface
                        = Typeface.createFromAsset(App.getInstance().getAssets(), PATH_FONT + name);
            } catch (NullPointerException e) {
                Log.e("FontUtil", "Font's not found " + name + "");
                return null;
            }
            fontCache.put(name, typeface);
        }
        return typeface;
    }

    @BindingAdapter("font")
    public static void setFont(View view, FontStyle fontStyle) {
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(FontUtils.getTypeface(fontStyle.toString()));
        } else if (view instanceof EditText) {
            ((EditText) view).setTypeface(FontUtils.getTypeface(fontStyle.toString()));
        } else if (view instanceof Button) {
            ((Button) view).setTypeface(FontUtils.getTypeface(fontStyle.toString()));
        }
    }

}

