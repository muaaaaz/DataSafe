package com.example.datasafe.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datasafe.R;

public class LoginSignupActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final String TAG = "LoginSignupActivity";
    GestureDetector detector;
    float x1;
    float y1;
    float x2;
    float y2;
    final float threshold = 100f;
    Button signupBtn;
    boolean isLoginSignupActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        detector = new GestureDetector(LoginSignupActivity.this, this);
        signupBtn = findViewById(R.id.btn_create_account);
        signupBtn.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_view_login_signup, new SignupFragment())
                    .commitNow();
            isLoginSignupActive = true;
            showHideViews();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isLoginSignupActive", isLoginSignupActive);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isLoginSignupActive = savedInstanceState.getBoolean("isLoginSignupActive");
        showHideViews();
    }

    void showHideViews() {
        if (isLoginSignupActive) {
            findViewById(R.id.box_info_login_signup).setVisibility(View.GONE);
            signupBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float xDiff = x2 - x1, yDiff = y2 - y1;
                if (Math.abs(xDiff) > threshold) {
                    if (xDiff > 0) {
                        Log.i(TAG, "onTouchEvent: RIGHT");
                    } else {
                        Log.i(TAG, "onTouchEvent: LEFT");
                    }
                } else if (Math.abs(yDiff) > threshold) {
                    if (yDiff > 0) {
                        Log.i(TAG, "onTouchEvent: DOWN");
                    } else {
                        Log.i(TAG, "onTouchEvent: UP");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_view_login_signup, new LoginFragment())
                                .commitNow();
                        isLoginSignupActive = true;
                        showHideViews();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}