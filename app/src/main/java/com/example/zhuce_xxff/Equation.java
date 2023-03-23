package com.example.zhuce_xxff;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)  // 忽略json字符串中多出的字段，只映射CiBaTranslation中给出的字符
public class Equation {
    @JsonProperty("tgt_text")  // 网络返回json字符串中的键
    private String a;   // 包装类中对应的字段

    @JsonProperty("to")
    private String b;

    @JsonProperty("from")
    private String c;

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

}