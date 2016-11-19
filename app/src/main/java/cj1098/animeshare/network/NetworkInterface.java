package cj1098.animeshare.network;


import android.support.annotation.NonNull;

/**
 * Created by chris on 11/18/16.
 */

public interface NetworkInterface {
    void makeRequest();
    NetworkInterface withNetworkCommand(@NonNull NetworkCommand networkCommand);
}
