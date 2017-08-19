package cj1098.animeshare.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class UiUtil {

    private UiUtil() {
        // private constructor for utility class per checkstyle
    }

    public static int getScreenHeight(@Nullable Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return 0;
        }
        Rect r = new Rect();
        View view = activity.getWindow().getDecorView();
        view.getWindowVisibleDisplayFrame(r);
        return view.getRootView().getHeight();
    }

    public static int getKeypadHeight(@Nullable Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return 0;
        }
        Rect r = new Rect();
        View view = activity.getWindow().getDecorView();
        view.getWindowVisibleDisplayFrame(r);
        int screenHeight = view.getRootView().getHeight();
        return screenHeight - r.bottom - r.top;
    }

    public static int getTitleBarHeight(@Nullable Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return 0;
        }
        Rect rectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentViewTop - statusBarHeight;
    }

    public static int getSoftButtonsBarHeight(@Nullable Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }


    public static void closeKeyboard(@Nullable Activity activity) {
        if (activity == null) {
            return;
        }

        closeKeyboard(activity, activity.getCurrentFocus());
    }

    public static void closeKeyboard(@Nullable Activity activity, @Nullable View view) {

        if (activity == null || activity.isFinishing()) {
            return;
        }

        // Check if no view has focus:
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openKeyboard(@Nullable Activity activity, @Nullable View view) {

        if (activity == null || activity.isFinishing()) {
            return;
        }

        // Check if no view has focus:
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }

    public static boolean isKeyboardOpen(@Nullable Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();

    }

    // TODO: this is useful for testing when you can't mock deviceUtil. Use this instead
    public static float dpFromPx(float px, @NonNull Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
