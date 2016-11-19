package cj1098.animeshare.network;

import android.support.annotation.NonNull;

/**
 * Created by chris on 11/18/16.
 */

public class NetworkClient implements NetworkInterface {

    NetworkCommand networkCommand;

    @Override
    public void makeRequest() {
        // post event to update ui
        networkCommand.callAnimeById(0);
    }

    @Override
    public NetworkClient withNetworkCommand(@NonNull NetworkCommand networkCommand) {
        this.networkCommand = networkCommand;
        return this;
    }
}
