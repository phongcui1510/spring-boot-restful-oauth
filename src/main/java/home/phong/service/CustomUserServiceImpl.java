package home.phong.service;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.phong.convertor.ModelEntityConvertor;
import home.phong.data.User;
import home.phong.model.UserModel;
import home.phong.repository.UserRepository;

@Service
public class CustomUserServiceImpl implements CustomUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UserModel> findAll() {
		// TODO Auto-generated method stub
		Iterable<User> users = userRepository.findAll();
		List<User> myList = IterableUtils.toList(users); 
		return ModelEntityConvertor.convertToUserModel(myList);
	}

	@Override
	public UserModel save(UserModel userModel) {
		User user = ModelEntityConvertor.convertToEntity(userModel);
		User savedUser = userRepository.save(user);
		
		return ModelEntityConvertor.convertToUserModel(savedUser);
	}

}
