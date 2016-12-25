package cj1098.animeshare.viewmodels;

/**
 * Created by chris on 11/27/16.
 */

public class UserViewModel {

    private String username;
    private String device_manufacturer;
    private String android_version;
    private String phone_model;
    private String locale;
    private String password;

    public UserViewModel(String username, String device_manufacturer, String android_version, String phone_model, String locale, String password) {
        this.username = username;
        this.device_manufacturer = device_manufacturer;
        this.android_version = android_version;
        this.phone_model = phone_model;
        this.locale = locale;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getDevice_manufacturer() {
        return device_manufacturer;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public String getPhone_model() {
        return phone_model;
    }

    public String getLocale() {
        return locale;
    }

    public String getPassword() {
        return password;
    }
}
