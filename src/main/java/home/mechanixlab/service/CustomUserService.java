package home.mechanixlab.service;

import java.util.List;

import home.mechanixlab.model.UserModel;


public interface CustomUserService {

	public List<UserModel> findAll();
	
	public UserModel save(UserModel user);
	
	public UserModel findByUsernameAndPassword(UserModel user);
}
