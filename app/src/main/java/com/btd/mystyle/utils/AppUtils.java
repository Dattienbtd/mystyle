package com.btd.mystyle.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by dattien on 4/19/17.
 */

public class AppUtils {
    // gettext in edittext
    public static TextWatcher getText(OnChangeText onChangeText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onChangeText.onChangeText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public interface OnChangeText {
        void onChangeText(CharSequence charSequence);
    }
}
