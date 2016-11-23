package home.mechanixlab.service;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.mechanixlab.convertor.ModelEntityConvertor;
import home.mechanixlab.data.User;
import home.mechanixlab.model.UserModel;
import home.mechanixlab.repository.UserRepository;

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
