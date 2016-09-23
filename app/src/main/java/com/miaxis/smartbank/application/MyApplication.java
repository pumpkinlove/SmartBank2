package com.miaxis.smartbank.application;

import android.app.Application;
import android.content.Intent;

import com.miaxis.smartbank.domain.Config;
import com.miaxis.smartbank.domain.event.CallServiceEvent;
import com.miaxis.smartbank.service.EmqttService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Date;

/**
 * Created by xu.nan on 2016/9/22.
 */
public class MyApplication extends Application {

    public DbManager.DaoConfig daoConfig;

    public Config config;

    @Override
    public void onCreate() {
        x.Ext.init(this);
        super.onCreate();

        initDB();
        initService();
        initConfig();

    }

    private void initDB() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName("smart_bank2.db")//创建数据库的名称
                .setDbVersion(1)//数据库版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });//数据库更新操作
    }

    private void initService() {
        Intent i = new Intent(this, EmqttService.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(i);
    }

    public void initConfig() {
        DbManager dbManager = x.getDb(daoConfig);
        try {
            config = dbManager.findFirst(Config.class);
            if (config == null) {
                config = new Config();
                config.setId(1);
                config.setIp("120.26.51.167");
                config.setPort("80");
                config.setEmqttIp("120.26.51.167");
                config.setEmqttPort("1883");
                config.setClientId("default" + new Date().getTime());
                dbManager.save(config);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
