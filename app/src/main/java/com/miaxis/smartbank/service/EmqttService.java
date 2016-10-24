package com.miaxis.smartbank.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.Config;
import com.miaxis.smartbank.domain.event.MessageArrivedEvent;
import com.miaxis.smartbank.emqtt.callback.MqttCallbackHandler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.greenrobot.eventbus.EventBus;

public class EmqttService extends Service {

    private Config config;

//    private ScheduledExecutorService scheduler;

    private MqttClient client;

    public EmqttService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Toast.makeText(this,"onBind",Toast.LENGTH_SHORT).show();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"onCreate",Toast.LENGTH_SHORT).show();
        config = ((MyApplication) getApplicationContext()).config;
        if (config == null) {
            Toast.makeText(this,"系统设置错误",Toast.LENGTH_SHORT).show();
            return;
        }
        String serverURI = "tcp://" + config.getEmqttIp() + ":" + config.getEmqttPort();
//        subscriber = MqttSubscriber.getInstance(serverURI, config.getClientId(), handler);

        try {
            client = new MqttClient(serverURI, config.getClientId(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setKeepAliveInterval(60);
            options.setCleanSession(true);
            options.setUserName("admin");
            options.setPassword("Pumpkin13.".toCharArray());
            options.setConnectionTimeout(1000);
            client.setCallback(new MqttCallbackHandler() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.e("--------","connectionLost__" + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.e("-------", topic + message.toString());
                    EventBus.getDefault().postSticky(new MessageArrivedEvent(topic, message.toString()));
                    Log.e("-------", "-================");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
            client.connect(options);

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"onStartCommand",Toast.LENGTH_SHORT).show();
        try {
            client.subscribe("topic/0001", 2);
            Log.e("-----", "topic/0001");
        } catch (MqttException e) {
            Log.e("e", e.getMessage());
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this,"onStart",Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        try {
            client.disconnect();
            client.close();
            Toast.makeText(this, "消息推送服务结束", Toast.LENGTH_SHORT).show();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
