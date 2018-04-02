package com.iot.connectme.connectme;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static android.content.ContentValues.TAG;

/**
 * Created by kulatkav on 3/28/2018.
 */



public class MqttClientImplementation extends MqttConnectMeClient {
    String url;
    String clientId;
    Context context;
    MqttClient mqttClient;

    MemoryPersistence persistence = new MemoryPersistence();

    @Override
    public void getMessage()  {

    }

    @Override
    public void sendMessage(String topic,String content,int qos) {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
            showMessage("Error sending message to MQTT broker.");
        }
    }

    @Override
    public void iotClient(Context context, String url,String clientId) {
        this.url = url;
        this.clientId = clientId;
        this.context = context;
    }

    @Override
    public boolean connect() {

        try {
            mqttClient = new MqttClient(url, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
        } catch (Exception e){
            showMessage("Error connectinv to MQTT broker.");

        }
        return super.connect();
    }

    @Override
    public boolean disconnect() {
        return super.disconnect();
    }

    @Override
    public boolean connectionStatus() {
        return false;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, MqttConnectMeClient.getDURATION()).show();
    }
}
