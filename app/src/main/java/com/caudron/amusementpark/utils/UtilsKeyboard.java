package com.caudron.amusementpark.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class UtilsKeyboard {
    public static <T extends Activity> void hideVirtualKeyboard(T activity) {
        InputMethodManager kb = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null){
            kb.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
