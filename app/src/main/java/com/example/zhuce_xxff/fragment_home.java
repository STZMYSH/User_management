package com.example.zhuce_xxff;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import android.text.Layout;

public class fragment_home extends Fragment {
    View v;
    private MaterialToolbar mToolbar;
    private ViewPager viewPager;
    private AnimationPagerAdapter animationPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        // 获取MaterialToolbar对象
        mToolbar = v.findViewById(R.id.toolbar1);
        // 将Toolbar设置为Activity的ActionBar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        setHasOptionsMenu(true); // 告诉系统fragment有自己的菜单

        //轮播动画
        viewPager = v.findViewById(R.id.ViewPager);
        List<Integer> animations = new ArrayList<>();
        animations.add(R.layout.animation_one);
        animations.add(R.layout.animation_two);
        animations.add(R.layout.animation_three);
        animations.add(R.layout.animation_four);
        animations.add(R.layout.animation_five);
        animationPagerAdapter = new AnimationPagerAdapter(animations);
        viewPager.setAdapter(animationPagerAdapter);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_yiyan", Context.MODE_PRIVATE);

        String yiyan = sharedPreferences.getString("yiyan", "");
        String from = sharedPreferences.getString("from", "");
        String from_who = sharedPreferences.getString("from_who", "");
        String creator = sharedPreferences.getString("creator", "");

        TextView yyy = v.findViewById(R.id.yyy);
        yyy.setText(yiyan);
        TextView yyy2 = v.findViewById(R.id.yyy2);
        yyy2.setText("——" + from);
        TextView yyy3 = v.findViewById(R.id.yyy3);
        // 隐藏 TextView
        yyy3.setVisibility(View.GONE);
        if (from_who != "" || from_who.equals(from)) {
            yyy3.setText("——" + from_who);
            // 显示 TextView
            yyy3.setVisibility(View.VISIBLE);
        }

        TextView yyy4 = v.findViewById(R.id.yyy4);
        yyy4.setVisibility(View.GONE);
//        yyy4.setText("——"+creator);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // 响应用户点击菜单项的事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.my) {
            // 创建要跳转的Fragment
            Fragment targetFragment = new fragment_MY();

            // 使用FragmentManager将当前fragment替换为要跳转的Fragment
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, targetFragment);
            transaction.commit();

            // 显式设置底部导航栏选中项，以便正确显示选中状态
            BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomnav);
            bottomNav.setSelectedItemId(R.id.我的);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
