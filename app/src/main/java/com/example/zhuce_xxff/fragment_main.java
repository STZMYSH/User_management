package com.example.zhuce_xxff;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fragment_main extends AppCompatActivity {
    ////    双击返回退出app
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(fragment_main.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                finishActivity(10);
//                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }/////


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar statusBar = new StatusBar(fragment_main.this);
        statusBar.setStatusBarColor(R.color.transparent);
        SharedPreferences prefs = getSharedPreferences("my_themes", MODE_PRIVATE);
        boolean ist = prefs.getBoolean("themes", true);

        statusBar.setTextColor(!ist);

//        setTheme(androidx.constraintlayout.widget.R.style.Base_Widget_AppCompat_SearchView_ActionBar);
        setContentView(R.layout.fragment_main);
//        getapi(View);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new fragment_home())
                    .commit();
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.myhome:
                        getapi();
                        fragment = new fragment_home();
                        break;
                    case R.id.photo:
                        fragment = new fragment_fanyi();
                        break;
                    case R.id.我的:
                        fragment = new fragment_MY();
                        break;
                    default:
                        return false;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
        });
    }

    public void btt(View view) {
        switch (view.getId()) {
            case R.id.yy:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 1000) {
                    getapi();
                    SharedPreferences sharedPreferences = getSharedPreferences("my_yiyan", Context.MODE_PRIVATE);
                    String yiyan = sharedPreferences.getString("yiyan", "");
                    String from = sharedPreferences.getString("from", "");
                    String from_who = sharedPreferences.getString("from_who", "");
                    String creator = sharedPreferences.getString("creator", "");

                    TextView yyy = findViewById(R.id.yyy);
                    yyy.setText(yiyan);
                    TextView yyy2 = findViewById(R.id.yyy2);
                    yyy2.setText("——" + from);
                    TextView yyy3 = findViewById(R.id.yyy3);
                    // 隐藏 TextView
                    yyy3.setVisibility(View.GONE);
                    if (from_who != "" || from_who.equals(from)) {
                        yyy3.setText("——" + from_who);
                        // 显示 TextView
                        yyy3.setVisibility(View.VISIBLE);
                    }

                    TextView yyy4 = findViewById(R.id.yyy4);
                    yyy4.setVisibility(View.GONE);
//        yyy4.setText("——"+creator);

                    firstTime = secondTime;
                } else {
                    Toast.makeText(this, "请勿频繁点击", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.yypsd:
                AlertDialog.Builder builder = new AlertDialog.Builder(fragment_main.this);
                builder.setTitle("修改密码");
                builder.setMessage("请输入新密码：");

// 添加一个输入框
                final EditText input = new EditText(fragment_main.this);
                builder.setView(input);

// 添加“确定”按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取用户输入的新密码
                        String newPassword = input.getText().toString();

// 更新数据库中的密码
                        DatabaseHelper dbHelper = new DatabaseHelper(getBaseContext());
                        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
                        String usen = prefs.getString("my_username", "");

                        dbHelper.updata(usen, newPassword);

                        SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                        editor.putString("my_password", newPassword);
                        editor.apply();

                        Toast.makeText(fragment_main.this, newPassword, Toast.LENGTH_SHORT).show();
                        // 在这里处理确定按钮的点击事件
                    }
                });

// 添加“取消”按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

// 显示对话框
                builder.show();

                break;
            case R.id.yyqqy:
                startActivity(new Intent(this, MainActivity.class));
                SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                editor.putBoolean("my_checkbox2", false);
                editor.apply();

                finish();
                break;
            default:
                break;
        }
    }

    public void getapi() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("https://v1.hitokoto.cn/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        InputStreamReader in = new InputStreamReader(conn.getInputStream());
                        BufferedReader br = new BufferedReader(in);

                        String line;
                        StringBuffer response = new StringBuffer();
                        while ((line = br.readLine()) != null) {
                            response.append(line);
                        }

                        JSONObject jsonObject = new JSONObject(response.toString());
                        String hito = jsonObject.getString("hitokoto");
                        String from = jsonObject.getString("from");
                        String from_who = jsonObject.getString("from_who");
                        String creator = jsonObject.getString("creator");

                        br.close();
                        in.close();
                        conn.disconnect();

                        SharedPreferences.Editor editor = getSharedPreferences("my_yiyan", MODE_PRIVATE).edit();

//                        editor.putString("yiyan", null);
//                        editor.putString("from", null);
//                        editor.putString("from_who", null);
//                        editor.putString("creator", null);
//                        editor.apply();


                        editor.putString("yiyan", hito);
                        if (from != "null") {
                            editor.putString("from", from);
                        } else {
                            editor.putString("from", "");
                        }
                        if (from_who != "null") {
                            editor.putString("from_who", from_who);
                        } else {
                            editor.putString("from_who", "");
                        }
                        if (creator != "null") {
                            editor.putString("creator", creator);
                        } else {
                            editor.putString("creator", "");
                        }
                        editor.apply();

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start(); // 启动子线程
//        thread.join(); // 等待子线程结束
    }

}
