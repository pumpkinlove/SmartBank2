package com.miaxis.smartbank.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.miaxis.smartbank.domain.event.NetStatusEvent;

import org.greenrobot.eventbus.EventBus;

public class NetworkReceiver extends BroadcastReceiver {
    public NetworkReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
            //signal strength changed
        }
        else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {//wifi连接上与否
//            EventBus.getDefault().post(new NetStatusEvent("网络状态改变"));
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                EventBus.getDefault().post(new NetStatusEvent("wifi网络连接断开", false));
            }
            else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {

                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                //获取当前wifi名称
                EventBus.getDefault().post(new NetStatusEvent("连接到网络 " + wifiInfo.getSSID(), true));
            }

        }
        else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {//wifi打开与否
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

            if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                EventBus.getDefault().post(new NetStatusEvent("系统关闭wifi", false));
            }
            else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                EventBus.getDefault().post(new NetStatusEvent("系统开启wifi", false));
            }
        }
    }
}
