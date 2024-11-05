package com.example.contact;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contact.model.Contact;

import java.util.ArrayList;

public class DanhBa extends AppCompatActivity {

    ListView lvDanhBa;
    ArrayList<Contact> dsDanhBa;
    ArrayAdapter<Contact> adapterDanhBa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_ba);
        addControls();
        showAllContact();
    }

    private void showAllContact() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            dsDanhBa.clear();
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                Contact contact = new Contact(name, phone);
                dsDanhBa.add(contact);
            }
            adapterDanhBa.notifyDataSetChanged();
            cursor.close();
        } else {
            Toast.makeText(this, "Failed to retrieve contacts", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        lvDanhBa = findViewById(R.id.lvDanhBa);
        dsDanhBa = new ArrayList<>();
        adapterDanhBa = new ArrayAdapter<>(DanhBa.this, android.R.layout.simple_list_item_1, dsDanhBa);
        lvDanhBa.setAdapter(adapterDanhBa);
    }
}
