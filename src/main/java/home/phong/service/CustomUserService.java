package home.phong.service;

import java.util.List;

import home.phong.model.UserModel;

public interface CustomUserService {

	public List<UserModel> findAll();
	
	public UserModel save(UserModel user);
}
