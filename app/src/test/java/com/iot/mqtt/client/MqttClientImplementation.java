package com.iot.mqtt.client;

import android.content.Context;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.net.URL;

/**
 * Created by kulatkav on 3/28/2018.
 */



public abstract class MqttClientImplementation implements IotClient,CleintError {

    private static final int DURATION = 2000;
    String clientId;
    Context context;
    String url;


    @Override
    public void IotClient(Context context,String url) {
        this.context = context;
        this.url = url;
        clientId = MqttClient.generateClientId();
    }

    @Override
    public boolean connect() {
        try {
            MqttAndroidClient client = new MqttAndroidClient(this.context, this.url, clientId);
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    showMessage(MQTT_CONNECT_SUCCESS);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    showMessage(MQTT_CONNECT_ERROR);

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            showMessage(MQTT_CONNECT_ERROR);
        }
        return connectionStatus;
    }

    @Override
    public boolean disconnect() {
        return connectionStatus;
    }

    @Override
    public boolean connectionStatus() {
        return connectionStatus;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, DURATION).show();
    }
}
