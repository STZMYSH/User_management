package com.example.zhuce_xxff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updata_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_password);
        EditText username1 = findViewById(R.id.username1);
        Button bt_updata_password = findViewById(R.id.bt_updata_password);
        bt_updata_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values;
                values = new ContentValues();
                values.put("password",username1.getText().toString());
                DatabaseHelper dbb = new DatabaseHelper(updata_password.this);
                dbb.getReadableDatabase();
//                dbb.update("users.db", values, "en=?", new String[]{et_en.getText().toString()});
                dbb.close();

            }
        });
    }

}