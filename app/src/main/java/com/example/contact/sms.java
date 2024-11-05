package com.example.contact;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class sms extends AppCompatActivity {
    ListView lvSms;
    ArrayList<String> smsList;
    ArrayAdapter<String> adapterSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        addControls();
        showAllSms();
    }

    private void showAllSms() {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            smsList.clear();
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
                smsList.add("From: " + address + "\nMessage: " + body);
            }
            adapterSms.notifyDataSetChanged();
            cursor.close();
        } else {
            Toast.makeText(this, "Failed to retrieve SMS", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        lvSms = findViewById(R.id.lvSms);
        smsList = new ArrayList<>();
        adapterSms = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsList);
        lvSms.setAdapter(adapterSms);
    }
}