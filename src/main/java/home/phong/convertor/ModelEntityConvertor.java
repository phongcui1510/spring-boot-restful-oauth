package home.phong.convertor;

import java.util.ArrayList;
import java.util.List;

import home.phong.data.User;
import home.phong.model.UserModel;

public class ModelEntityConvertor {

	public static UserModel convertToEntity(User user){
		UserModel model = new UserModel();
		model.setUuid(user.getUuid());
		model.setName(user.getName());
		model.setPassword(user.getPassword());
		model.setAge(user.getAge());
		return model;
	}
	
	public static List<UserModel> convertToEntity(List<User> users){
		List<UserModel> returnLst = new ArrayList<UserModel>();
		for (User user : users) {
			returnLst.add(convertToEntity(user));
		}
		return returnLst;
	}
}
