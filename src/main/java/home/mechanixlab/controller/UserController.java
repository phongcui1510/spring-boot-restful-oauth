/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package home.mechanixlab.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import home.mechanixlab.model.UserModel;
import home.mechanixlab.service.CustomUserService;

	
@RestController
@RequestMapping("/users")
public class UserController {

	private CustomUserService customUserService ;

	@Autowired
    public UserController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

	@RequestMapping(value="/list", method= RequestMethod.GET)
	public ResponseEntity<List<UserModel>> getUsers() {
		return new ResponseEntity<List<UserModel>>(customUserService.findAll(), HttpStatus.OK);
	}

	@RequestMapping("/create")
	public UserModel createUser (UserModel user) {
		if (user != null) {
			if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getRole())) {
				user.setErrorMsg("User is missing require parameters");
				return user;
			} else {
				user.setPassword(new StandardPasswordEncoder().encode(user.getPassword()));
				return customUserService.save(user);
			}
		}
		UserModel returnUser = new UserModel();
		returnUser.setErrorMsg("User is null");
		return returnUser;
	}
}
