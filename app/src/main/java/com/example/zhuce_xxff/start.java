package com.example.zhuce_xxff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }


    public void btnlog(View view) {
        EditText usn = findViewById(R.id.usesname);
        EditText pwd = findViewById(R.id.password);
        EditText pwd2 = findViewById(R.id.password2);
        EditText aihao = findViewById(R.id.aihao);
        String username = usn.getText().toString().trim();
        String password = pwd.getText().toString().trim();
        String password2 = pwd2.getText().toString().trim();
        String  aiha= aihao.getText().toString().trim();

        RadioGroup radioGroup = findViewById(R.id.nn);

// 在需要获取用户选择的 RadioButton 的地方调用 getCheckedRadioButtonId() 方法
        int checkedId = radioGroup.getCheckedRadioButtonId();
// 根据选中的 RadioButton 的 id 获取 RadioButton 对象
        RadioButton radioButton = findViewById(checkedId);
// 获取用户选择了哪个 RadioButton
        String choice = "";
        if (radioButton != null) {
            choice = radioButton.getText().toString();
        }

        switch (view.getId()) {
            case R.id.login:
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(this, "用户名或密码不能为空!", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(password2)) {
                        if (username.length() < 4 || password.length() < 4) {
                            Toast.makeText(this, "用户名或密码长度不够!", Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseHelper db = new DatabaseHelper(this);
                            User user = new User(username, password,aiha);
                            long id = db.addUser(user);
                            if (id > 0) {
                                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.gologin:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}