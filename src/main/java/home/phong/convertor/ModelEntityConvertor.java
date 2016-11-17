package home.phong.convertor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import home.phong.data.Role;
import home.phong.data.User;
import home.phong.model.UserModel;

public class ModelEntityConvertor {

	public static UserModel convertToUserModel(User user){
		UserModel model = new UserModel();
		model.setUuid(user.getUuid());
		model.setName(user.getName());
		model.setRole(user.getRole().name());
		model.setPassword(user.getPassword());
		model.setAge(user.getAge());
		model.setCity(user.getCity());
		model.setDistrict(user.getDistrict());
		model.setEmail(user.getEmail());
		model.setMobileno(user.getMobileNo());
		return model;
	}
	
	public static List<UserModel> convertToUserModel(List<User> users){
		List<UserModel> returnLst = new ArrayList<UserModel>();
		for (User user : users) {
			returnLst.add(convertToUserModel(user));
		}
		return returnLst;
	}
	
	public static User convertToEntity(UserModel userModel) {
		User user = new User();
		user.setName(userModel.getName());
		user.setPassword(userModel.getPassword());
		user.setRole(Role.valueOf(userModel.getRole()));
		user.setAge(userModel.getAge());
		user.setCity(userModel.getCity());
		user.setDistrict(userModel.getDistrict());
		user.setMobileNo(userModel.getMobileno());
		user.setStatus(userModel.getStatus());
		user.setRegistrationdatetime(new Date());
		return user;
	}
}
