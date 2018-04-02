package com.iot.connectme.connectme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity" ;
    private TextView mTextMessage;
    private TextView mTextSndMessage,mTextRecMessage,mTextTopic,mTextQos,mTextMqttBroker,mTextClientId;
    private Button mButtonSendCommand;
    MqttClient mqttAppClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //variables to accesss the view items
        mTextMessage = (TextView) findViewById(R.id.message);
        mTextSndMessage = (TextView) findViewById(R.id.mTextSndMessage);
        mTextRecMessage = (TextView) findViewById(R.id.mTextRecMessage);
        mTextTopic      =  (TextView) findViewById(R.id.mTextTopic);
        mTextQos        = (TextView) findViewById(R.id.mTextQos);
        mTextMqttBroker = (TextView) findViewById(R.id.mTextMqttBroker);
        mTextClientId = (TextView) findViewById(R.id.mTextClientId);
        mButtonSendCommand = (Button) findViewById(R.id.mButtonSendCommand);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mButtonSendCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendMessage(mTextSndMessage.getText().toString(),Integer.parseInt(mTextQos.getText().toString()),mTextTopic.getText().toString());
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private boolean connectToBroker(String broker) {

        if(mqttAppClient ==null){

            MemoryPersistence persistence = new MemoryPersistence();

            try {
                mqttAppClient = new MqttClient(broker, mTextClientId.getText().toString(), persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                System.out.println("Connecting to broker: " + broker);
                mqttAppClient.connect(connOpts);
                Toast.makeText(getApplicationContext(), "Connected to broker.", Toast.LENGTH_LONG).show();
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return true;
        }



    }

    private void sendMessage(String content,int qos,String topic) throws MqttException {
        if(connectToBroker(mTextMqttBroker.getText().toString())) {
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            mqttAppClient.publish(topic, message);
            System.out.println("Message published");
            Toast.makeText(getApplicationContext(), "Message publisehd.",Toast.LENGTH_LONG ).show();
        }

    }

    private void receiveMessage(int qos,String topic) throws MqttException {
        if(connectToBroker(mTextMqttBroker.getText().toString())) {
            MqttMessage message = new MqttMessage();
            message.setQos(qos);
            mqttAppClient.subscribe(topic);
            Toast.makeText(getApplicationContext(), "Topic Subscribed.",Toast.LENGTH_LONG ).show();

        }

    }

    private void getMessage(){
        try {
            mqttAppClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }




}
