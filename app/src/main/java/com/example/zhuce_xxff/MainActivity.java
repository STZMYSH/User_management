package com.example.zhuce_xxff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * _oo0oo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * 0\  =  /0
 * ___/`---'\___
 * .' \\|     |// '.
 * / \\|||  :  |||// \
 * / _||||| -:- |||||- \
 * |   | \\\  - /// |   |
 * | \_|  ''\---/''  |_/ |
 * \  .-\__  '-'  ___/-. /
 * ___'. .'  /--.--\  `. .'___
 * ."" '<  `.___\_<|>_/___.' >' "".
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * \  \ `_.   \_ __\ /__ _/   .-` /  /
 * =====`-.____`.___ \_____/___.-`___.-'=====
 * `=---='
 * <p>
 * <p>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * 佛祖保佑     永不宕机     永无BUG
 * <p>
 * 佛曰:
 * 写字楼里写字间，写字间里程序员；
 * 程序人员写程序，又拿程序换酒钱。
 * 酒醒只在网上坐，酒醉还来网下眠；
 * 酒醉酒醒日复日，网上网下年复年。
 * 但愿老死电脑间，不愿鞠躬老板前；
 * 奔驰宝马贵者趣，公交自行程序员。
 * 别人笑我忒疯癫，我笑自己命太贱；
 * 不见满街漂亮妹，哪个归得程序员？
 */

public class MainActivity extends AppCompatActivity {
    ////    双击返回退出app
    private long firstTime = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        boolean isChecked2 = prefs.getBoolean("my_checkbox2", false);
        if (isChecked2) {
            startActivity(new Intent(MainActivity.this, fragment_main.class));
            MainActivity.this.finish();
        }
        setContentView(R.layout.activity_password_zh);
//密码找回
        LinearLayout bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        findViewById(R.id.zhps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
        EditText usr = findViewById(R.id.username4);
        EditText ai = findViewById(R.id.aihao2);
        DatabaseHelper db = new DatabaseHelper(this);
        findViewById(R.id.zhaohui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrr = usr.getText().toString();
                String aii = ai.getText().toString();
                if (usrr.equals("") || aii.equals("")) {
                    Toast.makeText(MainActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    User us = db.getUserzh(usrr);

                    if (ai.getText().toString().equals(us.getAihao())) {
                        EditText usr = findViewById(R.id.username1);
                        EditText ai = findViewById(R.id.password1);
                        usr.setText(us.getUsername());
                        ai.setText(us.getPassword());
                        Toast.makeText(MainActivity.this, "找回成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "找回失败,账号或安全问题错误!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//
        CheckBox myCheckbox2 = findViewById(R.id.checkBox2);
        CheckBox myCheckbox = findViewById(R.id.checkBox);


        boolean isChecked = prefs.getBoolean("my_checkbox", false);
        String passwd = prefs.getString("my_password", "");
        String usen = prefs.getString("my_username", "");
        myCheckbox.setChecked(isChecked);
        myCheckbox2.setChecked(isChecked2);
        if (isChecked) {
            EditText usn = findViewById(R.id.username1);
            EditText pwd = findViewById(R.id.password1);
            usn.setText(usen);
            pwd.setText(passwd);
        }
        SharedPreferences.Editor editore = getSharedPreferences("my_themes", MODE_PRIVATE).edit();
        SharedPreferences prefsd = getSharedPreferences("my_themes", MODE_PRIVATE);


        boolean ist = prefsd.getBoolean("themes", true);
        ImageButton btn = findViewById(R.id.anhei);
        ToggleButton username111 = findViewById(R.id.username111);
        ToggleButton tb_show_password = findViewById(R.id.tb_show_password);
        if (!ist) {
//            MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            username111.setBackgroundDrawable(getResources().getDrawable(R.drawable.baseline_account_circle_hei24));
            tb_show_password.setBackgroundDrawable(getResources().getDrawable(R.drawable.password_show_hei));
            btn.setImageResource(R.drawable.image_btn_press);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ist) {
                    editore.putBoolean("themes", false);
                    editore.apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                } else {
                    editore.putBoolean("themes", true);
                    editore.apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }
            }
        });
        EditText etPassword = findViewById(R.id.password1);
        ToggleButton tbShowPassword = findViewById(R.id.tb_show_password);

        tbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                etPassword.setSelection(etPassword.getText().length()); // 将光标移动到文本末尾
            }
        });

        myCheckbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 如果复选框已选中，则执行某些操作
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                    editor.putBoolean("my_checkbox2", isChecked);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                    editor.putBoolean("my_checkbox2", isChecked);
                    editor.apply();
                }
            }
        });
        myCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 如果复选框已选中，则执行某些操作
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                    editor.putBoolean("my_checkbox", isChecked);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                    editor.putBoolean("my_checkbox", isChecked);
                    editor.putString("my_username", "");
                    editor.putString("my_password", "");
                    editor.apply();
                }
            }
        });

    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        SharedPreferences.Editor editore = getSharedPreferences("my_themes", MODE_PRIVATE).edit();
//        editore.putBoolean("themes", false);
//        editore.apply();
//
//        // 这里是需要在Activity销毁之前执行的代码
//    }

    private final int VIBRATE_TIME = 10;

    public void btn(View view) {
        EditText usn = findViewById(R.id.username1);
        EditText pwd = findViewById(R.id.password1);


        switch (view.getId()) {
            case R.id.golog:
                VibrateHelp.vSimple(view.getContext(), VIBRATE_TIME);
                startActivity(new Intent(this, start.class));
                break;
            case R.id.Log_in:
                VibrateHelp.vSimple(view.getContext(), VIBRATE_TIME);
                DatabaseHelper db = new DatabaseHelper(this);
                String username = usn.getText().toString();
                String password = pwd.getText().toString();
                preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                editor = preferences.edit();

                CheckBox myCheckbox = findViewById(R.id.checkBox);

                boolean isChecked = myCheckbox.isChecked();
//                如果勾选保存账号和密码到SharedPreferences
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                    editor.putString("my_username", username);
                    editor.putString("my_password", password);
                    editor.apply();
                }
                if (db.checkUser(username.trim(), password.trim())) {
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(this, fragment_main.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}