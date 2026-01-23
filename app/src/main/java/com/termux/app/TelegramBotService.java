package com.termux.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.util.Properties;
import java.io.InputStream;

public class TelegramBotService extends Service {
    private String botToken;
    private static final String TAG = "TelegramBotService";

    @Override
    public void onCreate() {
        super.onCreate();
        loadToken();
        sendStatus("Online");
    }

    private void loadToken() {
        try {
            InputStream is = getAssets().open(".env");
            Properties prop = new Properties();
            prop.load(is);
            botToken = prop.getProperty("TELEGRAM_BOT_TOKEN");
            is.close();
        } catch (Exception e) {
            Log.e(TAG, "Error loading token", e);
        }
    }

    private void sendStatus(String status) {
        // Logika pengiriman status ke Telegram akan diimplementasikan di sini
        Log.d(TAG, "Device Status: " + status + " with token: " + (botToken != null ? "Loaded" : "Missing"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        sendStatus("Offline");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}