package com.iot.connectme.connectme;

import android.content.Context;

/**
 * Created by kulatkav on 3/28/2018.
 */

public interface IotClient {
    boolean connect();
    boolean disconnect();
    boolean connectionStatus();

    void iotClient(Context context, String clientId,String url);
    void showMessage(String message);
    void sendMessage(String topic,String content,int qos);
    void getMessage();

}
