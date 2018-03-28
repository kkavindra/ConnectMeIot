package com.iot.mqtt.client;

import android.content.Context;

import java.net.URL;

/**
 * Created by kulatkav on 3/28/2018.
 */

public interface IotClient {
    String url=null;
    boolean connectionStatus=false;
    boolean connect();
    boolean disconnect();
    boolean connectionStatus();

    void IotClient(Context context,String url);
    void showMessage(String message);

}
