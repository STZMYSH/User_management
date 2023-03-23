package com.example.zhuce_xxff;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HYY {
    @GET("translation")
    Call<ResponseBody> getCall(@Query("from") String a, @Query("to") String b, @Query("apikey") String c ,@Query("src_text") String d);
    // 注解里传入 网络请求 的部分URL地址
    // ResponseBody 表示接收返回的明文字符串
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法
    // @Query会在url后面附加上 键-值 对，例如上式对应： root.php?a=?&b=?&c=?,
    //                                             ?号的值在MainActivity.java中的 Call<ResponseBody> call = request.getCall(a,b,c); 中依次指定

}
