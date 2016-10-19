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
import com.miaxis.smartbank.emqtt.subscriber.MqttSubscriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
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
        super.onCreate();
        Toast.makeText(this,"onCreate",Toast.LENGTH_SHORT).show();
        config = ((MyApplication) getApplicationContext()).config;
        if (config == null) {
            Toast.makeText(this,"系统设置错误",Toast.LENGTH_SHORT).show();
            return;
        }
//        String serverURI = "tcp://" + config.getEmqttIp() + ":" + config.getEmqttPort();
//        subscriber = MqttSubscriber.getInstance(serverURI, config.getClientId(), handler);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"onStartCommand",Toast.LENGTH_SHORT).show();
        reConnect(null);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this,"onStart",Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Toast.makeText(this, "消息推送服务结束", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    private void reConnect(final MqttClient client) {
        Log.e("reConnect","reConnect---------");
        if (scheduler != null) {
            Log.e(scheduler.isShutdown()+"","isShutdown -------");
            Log.e(scheduler.isTerminated()+"","isTerminated -----------");
        }
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.e("scheduler run ","进入 run方法");
                if(client == null || !client.isConnected()) {
                    Log.e("reConnect ===== re ","重连 连接开始");
                    connect();
                }
                Log.e("connect","重连  连接 完成");
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    private void connect() {
        try {
            Log.e("connect","进入connect ------------");
            Log.e("clientid", config.getClientId());
            subscriber = MqttSubscriber.getInstance(
                    "tcp://" + config.getEmqttIp() + ":" + config.getEmqttPort(),
                    config.getClientId(),
                    new MqttCallbackHandler() {
                        @Override
                        public void connectionLost(Throwable cause) {
                            Log.e("lost","lost =============== l失去连接");
                            reConnect(client);
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            Log.e(topic, ""+message.toString());
                            try {
                                EventBus.getDefault().post(new MessageArrivedEvent(topic, message.toString()));
                            } catch (Exception e) {
                                Log.e("messageArrived", e.toString());
                            }
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    }
            );
            subscriber.connect();

            subscriber.subscribe("topic/0001",2);
            subscriber.subscribe("topic/1111",2);
            subscriber.subscribe("topic/1001",2);
            subscriber.subscribe("topic/000001",2);
            Log.e(" connect  connect"," 连接成功 -----");
            if(scheduler != null){
                Log.e(" scheduler  shutdown","shut down 定时任务");
                scheduler.shutdown();
            }
        } catch (Exception e) {
            Log.e("exception", "连接失败  异常"+e.getMessage());
        }
    }



}
