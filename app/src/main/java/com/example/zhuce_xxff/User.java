package com.example.zhuce_xxff;

public class User {
    private int id;
    private String username;
    private String password;
    private String aihao;

    public User() {
    }

    public User(String username, String password, String aihao) {
        this.username = username;
        this.password = password;
        this.aihao = aihao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getAihao() {
        return aihao;
    }
    public String setAihao() {
        return aihao;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}