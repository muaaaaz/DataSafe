package com.example.datasafe.utilities;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorInt;
import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;

public class Utilities {
    public static void showSnackBar(View view, @StringRes int resId, @ColorInt int color) {
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).setBackgroundTint(color).show();
    }

    public static void showSnackBar(View view, String text, @ColorInt int color) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).setBackgroundTint(color).show();
    }

    public static void hideVirtualKeyBoard(Context context, View currentFocus) {
        // hide keyboard if open
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
