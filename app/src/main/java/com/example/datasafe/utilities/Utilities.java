package com.example.datasafe.utilities;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.example.datasafe.R;
import com.google.android.material.snackbar.Snackbar;

public class Utilities {
    public static void showSnackBar(View view, @StringRes int resId, @ColorInt int color) {
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).setTextColor(Color.WHITE).setBackgroundTint(color).show();
    }

    public static void showSnackBar(View view, String text, @ColorInt int color) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).setTextColor(Color.WHITE).setBackgroundTint(color).show();
    }

    public static void hideVirtualKeyBoard(Context context, View currentFocus) {
        // hide keyboard if open
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showCustomToast(Context context, @DrawableRes int drawableResId, @StringRes int textResId) {
        showCustomToast(context, drawableResId, context.getString(textResId));
    }

    public static void showCustomToast(Context context, @DrawableRes int drawableResId, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null, false);
        ImageView imageView = view.findViewById(R.id.imageView_toast);
        TextView textView = view.findViewById(R.id.textView_toast);
        imageView.setImageResource(drawableResId);
        textView.setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
