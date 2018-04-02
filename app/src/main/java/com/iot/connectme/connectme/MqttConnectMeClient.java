package com.iot.connectme.connectme;

import android.content.Context;


/**
 * Created by kulatkav on 3/28/2018.
 */



public abstract class MqttConnectMeClient implements IotClient,ExceptionHanlder {

    private static int DURATION = 2000;
    private String clientId;
    Context context;
    private String url;



    @Override
    public abstract void iotClient(Context context,String clientId,String url);

    @Override
    public boolean connect(){
        return false;
    }


    @Override
    public boolean disconnect() {
        return false;
    }

    @Override
    public boolean connectionStatus() {
        return false;
    }

    @Override
    public abstract void showMessage(String message);

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static int getDURATION() {
        return DURATION;
    }

    public static void setDURATION(int DURATION) {
        MqttConnectMeClient.DURATION = DURATION;
    }

}
