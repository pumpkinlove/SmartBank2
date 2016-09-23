package com.miaxis.smartbank.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.miaxis.smartbank.application.MyApplication;
import com.miaxis.smartbank.domain.Config;
import com.miaxis.smartbank.domain.event.CallServiceEvent;
import com.miaxis.smartbank.domain.event.MessageArrivedEvent;
import com.miaxis.smartbank.emqtt.callback.MqttCallbackHandler;
import com.miaxis.smartbank.emqtt.subscriber.MqttSubscriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmqttService extends Service {

    private Config config;

    private ScheduledExecutorService scheduler;
    private MqttSubscriber subscriber;

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
        Toast.makeText(this,"onCreate",Toast.LENGTH_SHORT).show();
        config = ((MyApplication) getApplicationContext()).config;
        if (config == null) {
            Toast.makeText(this,"系统设置错误",Toast.LENGTH_SHORT).show();
            return;
        }
        String serverURI = "tcp://" + config.getEmqttIp() + ":" + config.getEmqttPort();
        subscriber = MqttSubscriber.getInstance(serverURI, config.getClientId(), handler);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"onStartCommand",Toast.LENGTH_SHORT).show();
//        reConnect();
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this,"onStart",Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    private void reConnect() {
        Log.e("reConnect","reConnect---------");
        if (scheduler != null && subscriber.isConnect()) {
            Log.e(scheduler.isShutdown()+"","isShutdown -------");
            Log.e(scheduler.isTerminated()+"","isTerminated -----------");
            scheduler.shutdown();
        }
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.e("scheduler run ","进入 run方法");
                if(!subscriber.isConnect()) {
                    try {
                        Log.e("reConnect ===== re ","重连 连接开始");
                        subscriber.connect();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e("shutdown ===== re ","shutdown");
                    scheduler.shutdown();
                }
                Log.e("connect","重连  连接 完成");
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    private MqttCallbackHandler handler = new MqttCallbackHandler() {
        @Override
        public void connectionLost(Throwable cause) {
            Log.e("-----","connectionLost");
            reConnect();
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Log.e("------", topic + "==========" + message.toString());
            EventBus.getDefault().post(new MessageArrivedEvent(topic, message.toString()));
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    };
}
