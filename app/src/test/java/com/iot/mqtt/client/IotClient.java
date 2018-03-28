package com.iot.mqtt.client;

/**
 * Created by kulatkav on 3/28/2018.
 */

public interface IotClient {
    boolean connect();
    boolean disconnect();
    boolean connectionStatus();


}
