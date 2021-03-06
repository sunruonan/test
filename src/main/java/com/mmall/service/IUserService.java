package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService {
	ServerResponse<User> login(String username,String password);
	ServerResponse<String> register(User user);
	public ServerResponse<String> checkValid(String str,String type);
	ServerResponse selectQuestion(String username);
    ServerResponse<String> checkAnswer(String username,String question,String answer);
    ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken);
    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);
    ServerResponse<User> update_information(User user);
    ServerResponse<User> get_information(Integer userId);
    
    public ServerResponse checkAdminRole(User user);
}
