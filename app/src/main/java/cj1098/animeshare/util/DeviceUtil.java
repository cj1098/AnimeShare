package cj1098.animeshare.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.preference.Preference;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.util.Locale;
import java.util.UUID;

import cj1098.animeshare.R;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chris on 11/18/16.
 */

public class DeviceUtil {
    public enum CurrentLayout {
        MOBILE,
        TABLET_PORTRAIT,
        TABLET_LANDSCAPE,
    }

    private static final String TAG = DeviceUtil.class.getSimpleName();

    @VisibleForTesting
    static final long DOWNLOAD_MEMORY_THRESHOLD = 200000;

    public static final Locale LOCALE_SPANISH = new Locale("es", "ES");
    public static final Locale LOCALE_SPANISH_US = new Locale("es", "US");

    private Context mContext;
    private Preference mPreferences;
    private String mUuid;

    public DeviceUtil(Context context) {
        mContext = context;
    }

    public String getCarrier() {

        try {
            TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            return manager.getNetworkOperatorName();
        } catch (Exception e) {
            // ignored
        }

        return "";
    }

    public boolean isTablet() {
        return mContext.getResources().getBoolean(R.bool.is_a_tablet);
    }

    public boolean isLandscape() {
        return mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public boolean isPortrait() {
        return mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public boolean isTabletLandscape() {
        return isTablet() && isLandscape();
    }

    public CurrentLayout currentDeviceLayout() {
        if (isTablet()) {
            // TABLET
            if (isPortrait()) {
                return CurrentLayout.TABLET_PORTRAIT;
            }
            return CurrentLayout.TABLET_LANDSCAPE;
        }

        // MOBILE
        return CurrentLayout.MOBILE;
    }

    //Check to see if a new configuration is in our supported locale list.
    public boolean isInSupportedLocale(Configuration config) {
        return (isEnglishLocale(config.locale) || isFrenchLocale(config.locale) || isSpanishLocale(config.locale));
    }

    public Locale getLocale() {
        return mContext.getResources().getConfiguration().locale;
    }

    public String getDeviceUuid() {
        return mUuid;
    }

    public Point getDeviceDimens() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        return size;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int pxFromDp(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        // TODO Seth: We should probably use densityDpi instead of xdpi
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public float convertPixelsToDp(float px) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @NonNull
    public Scheduler getMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    public Scheduler getIOScheduler() {
        return Schedulers.io();
    }

    public int getBuildVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Usage example:
     *
     * {@code if (isBuildVersionGreaterThanOrEqualTo23)} is equivalent to {@code if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)}
     *
     * @param comp The build version to compare against. Use an int, rather than one of the constants provided by {@code Build.VERSION_CODES}
     * @return true if the SDK version on the device is greater than or equal to the version being compared against.
     */
    public boolean isBuildVersionGreaterThanOrEqualTo(int comp) {
        return Build.VERSION.SDK_INT >= comp;
    }

    @NonNull
    public String getAppVersion() {
        String version = "";
        try {
            version = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName + "."
                    + mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception in getAppVersion", e);
        }
        return version;
    }

    public String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public String getModel() {
        return Build.MODEL;
    }

    public String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    private String getAndroidId() {
        String androidDeviceId = null;

        final String androidId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (androidId != null && !"9774d56d682e549c".equals(androidId)) {
            androidDeviceId = androidId;
        }

        return androidDeviceId;
    }

    private String getWifiMacAddress() {
        String macAddress = null;

        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null) {
                macAddress = info.getMacAddress();
            }
        }

        return macAddress;
    }
    // endregion

    /**
     * @param allocationSize the amount of memory we want to test against
     * @return true if we have enough memory for the specified amount. False otherwise
     */
    public boolean hasEnoughMemory(long... allocationSize) {
        // The number 1048576L is 1024*1024 and is used to convert bytes to MB.
        // The reason I break this down to MB, KB, and bytes is so we can have those measurements if need be in the future.
        long availableMemInBytes = getAvailableMemory();

        //if the amount of memory we have is less than what we want to allocate, then display an error.
        if (allocationSize != null && allocationSize.length >= 1) {
            return availableMemInBytes >= allocationSize[0];
        } else {
            //TODO: return a specified amount of memory available
            return availableMemInBytes >= DOWNLOAD_MEMORY_THRESHOLD;
        }
    }

    @VisibleForTesting
    long getDeviceElapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    @VisibleForTesting
    long getAvailableMemory() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        return mi.availMem;
    }

    @VisibleForTesting
    public boolean isTesting() {
        return false;
    }

    // region Static helper methods -- shouldn't be mocked
    public static boolean isFrenchLocale(Locale locale) {
        return locale.equals(Locale.CANADA_FRENCH) || locale.equals(Locale.FRENCH) || locale.equals(Locale.FRANCE);
    }

    public static boolean isSpanishLocale(Locale locale) {
        return locale.equals(LOCALE_SPANISH) || locale.equals(LOCALE_SPANISH_US);
    }

    public static boolean isEnglishLocale(Locale locale) {
        return locale.equals(Locale.CANADA) || locale.equals(Locale.ENGLISH) || locale.equals(Locale.US) || locale.equals(Locale.UK);
    }
    // endregion



}
