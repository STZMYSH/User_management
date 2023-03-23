package com.example.zhuce_xxff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class fragment_fanyi extends Fragment {
    private MaterialToolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fanyi, container, false);

        mToolbar = v.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);

        final EditText edt_a = (EditText) v.findViewById(R.id.edt_a);
        Button btn_ZYY = (Button) v.findViewById(R.id.btn_a);
        btn_ZYY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView txt_feedback = (TextView) getActivity().findViewById(R.id.edt_b);
                txt_feedback.setText("");
                String a = edt_a.getText().toString();
                ZYY(a);
            }
        });
        Button btn_YYZ = (Button) v.findViewById(R.id.btn_b);
        btn_YYZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView txt_feedback = (TextView) getActivity().findViewById(R.id.edt_b);
                txt_feedback.setText("");
                String a = edt_a.getText().toString();
                YYZ(a);
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // 响应标题栏菜单的点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.my) {
            Fragment targetFragment = new fragment_MY();

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, targetFragment);
            transaction.commit();

            BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomnav);
            bottomNav.setSelectedItemId(R.id.我的);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void ZYY(String a) {
        final String d = "zh";
        final String b = "en";
        final String c = "5846d37d438c513a6c1392dcf61fc5d1";
        final TextView txt_feedback = (TextView) getActivity().findViewById(R.id.edt_b);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.niutrans.com/NiuTransServer/")
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        HYY request = retrofit.create(HYY.class);
        Call<ResponseBody> call = request.getCall(d, b, c, a);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //获取返回的json字符串
                String jstr = null;
                try {
                    jstr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //把json字符串转化为Java对象
                Equation equation = null;
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                try {
                    equation = mapper.readValue(jstr, Equation.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String str = equation.getA();
                txt_feedback.setText(str);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                txt_feedback.setText("请求失败:" + t.getMessage());
            }
        });
    }

    public void YYZ(String a) {
        final String d = "en";
        final String b = "zh";
        final String c = "5846d37d438c513a6c1392dcf61fc5d1";
        final TextView txt_feedback = (TextView) getActivity().findViewById(R.id.edt_b);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.niutrans.com/NiuTransServer/") // 设置 网络请求, 以 / 结尾
                .callbackExecutor(Executors.newSingleThreadExecutor())  // 以单独的线程处理,以应对文件下载等情景
                .build();
        HYY request = retrofit.create(HYY.class);
        Call<ResponseBody> call = request.getCall(d, b, c, a);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // 步骤7：获取返回的json字符串
                String jstr = null;
                try {
                    jstr = response.body().string();   // 注意：这里需要使用string(), 而不能使用toString()，使用toString()返回的是类名
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 步骤8: 把json字符串转化为Java对象
                Equation equation = null;
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);  // 允许使用非双引号属性名字
                try {
                    equation = mapper.readValue(jstr, Equation.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String str = equation.getA();
                txt_feedback.setText(str);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                txt_feedback.setText("请求失败:" + t.getMessage());
            }
        });
    }
}
