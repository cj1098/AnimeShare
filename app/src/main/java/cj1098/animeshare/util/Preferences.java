package cj1098.animeshare.util;


import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Base64;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;


@Singleton
public class Preferences implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = Preferences.class.getSimpleName();

    // Database keys
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String USER_ID = "USER_ID";
    private static final String FIRST_TIME_LOGIN = "FIRST_TIME_LOGIN";
    private static final String ACCESS_TOKEN_EXPIRE_TIME = "ACCESS_TOKEN_EXPIRE_TIME";
    /* Database keys:
         * Used for tests: the array contents must match the public database key Strings
         */
    public static final String[] PREFERENCE_KEYS = {
            Preferences.ACCESS_TOKEN,
            Preferences.USER_ID,
            Preferences.FIRST_TIME_LOGIN,
            Preferences.ACCESS_TOKEN_EXPIRE_TIME
    };

    private SharedPreferences mSharedPreferences;

    // Local preference values
    private String mAccessToken;
    private String mUserId;
    private boolean mFirstTimeLogin;
    private long mAccessTokenExpireTime;


    private String mMobileCarrier;

    public Preferences(@NonNull SharedPreferences sharedPreferences) {

        mSharedPreferences = sharedPreferences;
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        initializePreferences();
    }

    private void initializePreferences() {
        mAccessToken = getSharedPreferences().getString(ACCESS_TOKEN, "");
        mUserId = getSharedPreferences().getString(USER_ID, "");
        mFirstTimeLogin = getSharedPreferences().getBoolean(FIRST_TIME_LOGIN, false);
        mAccessTokenExpireTime = getSharedPreferences().getLong(ACCESS_TOKEN_EXPIRE_TIME, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
        //mMobileCarrier = getSharedPreferences().getString(MOBILE_CARRIER, null);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case ACCESS_TOKEN:
                mAccessToken = getSharedPreferences().getString(ACCESS_TOKEN, "");
                break;
            case USER_ID:
                mUserId = getSharedPreferences().getString(USER_ID, "");
                break;
            case FIRST_TIME_LOGIN:
                mFirstTimeLogin = getSharedPreferences().getBoolean(FIRST_TIME_LOGIN, false);
                break;
            case ACCESS_TOKEN_EXPIRE_TIME:
                mAccessTokenExpireTime = getSharedPreferences().getLong(ACCESS_TOKEN_EXPIRE_TIME, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
                break;
            default:
                Log.e(TAG,  "There is no preference associated with the key: " + key);
                break;
        }

    }

    public void clearPreferences() {
        getSharedPreferences().edit().clear().apply();
    }

    public void removePreference(@NonNull String preference) {
        Editor editor = getSharedPreferences().edit();
        editor.remove(preference);
        editor.apply();
    }

    @Nullable
    public String getMobileCarrier() {
        return mMobileCarrier;
    }

    @NonNull
    public String getUserName() {
        String decryptedUsername = "";

        /*if (mEncryptedUsername != null) {
            try {
                LogUtil.d(TAG, withTags(UTL), "On Decryption, Encrypted Username: " + mEncryptedUsername);
                decryptedUsername = new String(CryptoUtil.decrypt(Base64.decode(mEncryptedUsername, Base64.DEFAULT),
                        CryptoUtil.createEncryptionKey()));
                LogUtil.d(TAG, withTags(UTL), "On Decryption, Decrypted Username: " + decryptedUsername);
            } catch (Exception e) {
                LogUtil.e(TAG, withTags(UTL), "Exception in generateUuid", e);
            }
        }*/

        return decryptedUsername;
    }


    public void setFirstTimeLogin(boolean firstTimeLogin) {
        Editor editor = getSharedPreferences().edit();
        editor.putBoolean(FIRST_TIME_LOGIN, firstTimeLogin);
        editor.apply();
    }

    public void setAccessToken(String accessToken) {
        Editor editor = getSharedPreferences().edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public void setmAccessTokenExpireTime(long accessTokenExpireTime) {
        Editor editor = getSharedPreferences().edit();
        editor.putLong(ACCESS_TOKEN_EXPIRE_TIME, accessTokenExpireTime);
        editor.apply();
    }

    public long getmAccessTokenExpireTime() {
        return mAccessTokenExpireTime;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setUsername(@NonNull String username) {
        /*try {
            Editor editor = getSharedPreferences().edit();
            LogUtil.d(TAG, withTags(UTL), "On Encryption, Username: " + username);

            username = Base64.encodeToString(CryptoUtil.encrypt(username.getBytes(), CryptoUtil.createEncryptionKey()), Base64.DEFAULT);

            LogUtil.d(TAG, withTags(UTL), "On Encryption, Encrypted Username: " + username);

            editor.putString(SXIR_USERNAME, username);

            editor.apply();
        } catch (Exception e) {
            LogUtil.e(TAG, withTags(UTL), "Exception in generateUuid", e);
        }*/
    }

    @NonNull
    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    // Generic get/set methods region: used for tests
    public String getString(String key) {
        return getSharedPreferences().getString(key, new JSONObject().toString());
    }

    public boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, getDefaultBooleanValue(key));
    }

    public long getLong(String key) {
        return getSharedPreferences().getLong(key, 0L);
    }

    public int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public void setString(String key, String value) {
        Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setBoolean(String key, boolean value) {
        Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setLong(String key, long value) {
        Editor editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void setInt(String key, int value) {
        Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean getDefaultBooleanValue(String key) {
        switch (key) {
            case FIRST_TIME_LOGIN:
                return true;
        }
        return false;
    }

    // End generic get/set methods region: used for tests
}