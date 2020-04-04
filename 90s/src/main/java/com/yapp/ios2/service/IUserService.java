package com.yapp.ios2.service;

public interface IMemberService{

    String login(String id, String password);

    String signup(String id, String password, String name, String phoneNum);

}
