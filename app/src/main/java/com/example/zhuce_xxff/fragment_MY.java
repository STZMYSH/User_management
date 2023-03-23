package com.example.zhuce_xxff;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.zhuce_xxff.databinding.DialogAboutBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import androidx.databinding.DataBindingUtil;

public class fragment_MY extends Fragment {
    private SharedPreferences preferences;

    //    private SharedPreferences.Editor editor;
    private int caidan = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        username = preferences.getString("username", ""); // 替换成当前用户的用户名
        imageView = v.findViewById(R.id.tx);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个旋转动画对象，并设置相关属性
                RotateAnimation animation = new RotateAnimation(0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(1000); // 设置动画时长，单位毫秒
                animation.setInterpolator(new LinearInterpolator()); // 设置动画插值器，这里使用线性插值器

                // 设置动画监听器，在动画结束时恢复原来的状态
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        imageView.setRotation(0);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

                // 启动动画
                imageView.startAnimation(animation);
                caidan += 1;
                if (caidan == 7) {
//                    // 获取屏幕宽度和高度
//                    DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
//                    int screenWidth = metrics.widthPixels;
//                    int screenHeight = metrics.heightPixels;
//
//// 创建一个 ImageView 控件，并设置其宽度和高度
//                    ImageView heartView = new ImageView(getContext());
//                    heartView.setImageResource(R.drawable.ic_heart);
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
//                    params.leftMargin = (int) (Math.random() * screenWidth);
//                    params.topMargin = screenHeight - 100;
//                    heartView.setLayoutParams(params);
//
//// 将 ImageView 添加到 Activity 的布局中，并设置其动画效果
//                    ((RelativeLayout) v.findViewById(android.R.id.content)).addView(heartView);
//                    Animation heartAnim = AnimationUtils.loadAnimation(getContext(), R.anim.heart_anim);
//                    heartView.startAnimation(heartAnim);

                    showEasterEgg(); // 显示小彩蛋动画


                    int currentRandom = generateRandom();
                    switch (currentRandom) {
                        case 0:
                            playEasterEggSound(MediaPlayer.create(getActivity(), R.raw.xxb)); // 播放小彩蛋音效
                            break;
                        case 1:
                            playEasterEggSound(MediaPlayer.create(getActivity(), R.raw.ddl)); // 播放小彩蛋音效
                            break;
                        case 2:
                            playEasterEggSound(MediaPlayer.create(getActivity(), R.raw.mla)); // 播放小彩蛋音效
                            break;
                        case 3:
                            playEasterEggSound(MediaPlayer.create(getActivity(), R.raw.pkq)); // 播放小彩蛋音效
                            break;
                        case 4:
                            playEasterEggSound(MediaPlayer.create(getActivity(), R.raw.wo)); // 播放小彩蛋音效
                            break;
                    }
                    Toast.makeText(getActivity(), "❤🧡💛💚💙💜🤎🖤🤍💖💘", Toast.LENGTH_SHORT).show();
                    caidan = 0;
                }
            }
        });

        loadAvatar(username);
        MaterialCardView yyptx = v.findViewById(R.id.yyptx);
        yyptx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });

        //////////////////设置标题栏点击事件DataBinding///未成功
        MaterialToolbar toolbar = v.findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAboutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                        R.layout.dialog_about, null, false);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(binding.getRoot());
                builder.setTitle("关于我们");
                builder.setPositiveButton("确定", null);

                binding.versionName.setText(BuildConfig.VERSION_NAME);
                binding.versionCode.setText(String.valueOf(BuildConfig.VERSION_CODE));
//                binding.url.setOnClickListener(v -> {
////             打开某个网址
//                });

                AlertDialog dialog = builder.create();
                dialog.show();
                Toast.makeText(getActivity(), "正常", Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////
        toolbar.setTitle("你好\t" + preferences.getString("username", "") + "!");
        return v;


    }

    private static int lastRandom = -1;

    public static int generateRandom() {
        Random random = new Random();
        int currentRandom = random.nextInt(5); // 生成当前随机数
        while (currentRandom == lastRandom) { // 如果与上一个随机数相同，则重新生成
            currentRandom = random.nextInt(5);
        }
        lastRandom = currentRandom; // 更新上一个随机数为当前随机数
        return currentRandom;
    }

    private void showEasterEgg() {
        // 获取LottieAnimationView控件
// 获取屏幕高度

//        // 如果之前显示过动画，先清除动画并从布局中移除
//        if (animationView != null) {
//            animationView.clearAnimation();
//            ViewGroup rootView = getActivity().findViewById(android.R.id.content);
//            rootView.removeView(animationView);
//        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        LottieAnimationView animationView = new LottieAnimationView(getActivity());
        animationView.setAnimation("dog.json");
        animationView.setRepeatCount(LottieDrawable.INFINITE);
        animationView.setRepeatMode(LottieDrawable.REVERSE);
        animationView.playAnimation();

// 设置控件布局参数
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // 居中

// 计算控件顶部和底部边距
        int topMargin = screenHeight / 2; // 使动画位于屏幕底部正中间
        int bottomMargin = 100; // 底部边距
        layoutParams.setMargins(0, topMargin, 0, bottomMargin); // 设置边距

// 将控件添加到布局中
        ViewGroup rootView = getActivity().findViewById(android.R.id.content);
        rootView.removeView(animationView); // 从视图中移除控件

        rootView.addView(animationView, layoutParams);

// 在 mHandler 中延迟 5 秒后移除动画
        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                rootView.removeView(animationView); // 从视图中移除控件
            }
        };
        mHandler.postDelayed(mRunnable, 4000);
    }

    private void playEasterEggSound(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    LottieAnimationView animationView;
//    private Handler mHandler = new Handler();
//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            // 在此处移除动画
//            ViewGroup rootView = getActivity().findViewById(android.R.id.content);
//            rootView.removeView(animationView);
//        }
//    };


    //头像
    private static final int REQUEST_CODE_PERMISSION = 1001;
    private static final int REQUEST_CODE_SELECT_IMAGE = 1002;
    private static final int REQUEST_CODE_CROP_IMAGE = 1003;

    private String username;
    private ImageView imageView;
    private String imagePath;

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    private void handleImage(Uri imageUri) {
        // 显示图片
        imageView.setImageURI(imageUri);

        // 裁剪图片
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 245);
        intent.putExtra("outputY", 245);
        intent.putExtra("circleCrop", "true");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    private void saveImage(Bitmap bitmap) {
        File file = new File(getActivity().getExternalFilesDir(null), username + "avatar.png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            imagePath = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSelectedAvatar(String username, String imagePath) {
        SharedPreferences sp = getActivity().getSharedPreferences("avatar", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(username, imagePath);
        editor.apply();
    }

    private String getSelectedAvatar(String username) {
        SharedPreferences sp = getActivity().getSharedPreferences("avatar", getActivity().MODE_PRIVATE);
        return sp.getString(username, null);
    }

    private void loadAvatar(String username) {
        String imagePath = getSelectedAvatar(username);
        if (imagePath != null) {
            File file = new File(imagePath);
            if (file.exists()) {
                imageView.setImageURI(Uri.fromFile(file));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            handleImage(imageUri);
        } else if (requestCode == REQUEST_CODE_CROP_IMAGE && resultCode == getActivity().RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap bitmap = bundle.getParcelable("data");
                saveImage(bitmap);
                saveSelectedAvatar(username, imagePath);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户同意授权，可以打开相册
                openAlbum();
            } else {
                // 用户拒绝授权，可以提示用户手动授权
                Toast.makeText(getActivity(), "请手动授权相册读取权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
