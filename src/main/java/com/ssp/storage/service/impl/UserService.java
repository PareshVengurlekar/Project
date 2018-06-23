package com.ssp.storage.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssp.storage.constant.ErrorCode;
import com.ssp.storage.domain.Folder;
import com.ssp.storage.domain.User;
import com.ssp.storage.exception.UserException;
import com.ssp.storage.repository.FolderRepository;
import com.ssp.storage.repository.UserRepository;
import com.ssp.storage.service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FolderRepository folderRepository;

	@Value("${server.port}")
	private int port;

	@Override
	public boolean authenticate(String username, String password) throws UserException {
		return userRepository.existsByUsernameAndPassword(username, password);
	}

	@Override
	public User addUser(User user) throws UserException {
		if (userRepository.existsByEmail(user.getEmail()))
			throw new UserException(port + ErrorCode.EMAIL_DUPLICATE.getKey(), ErrorCode.EMAIL_DUPLICATE.getValue());
		if (userRepository.existsByUsername(user.getUsername()))
			throw new UserException(port + ErrorCode.USERNAME_DUPLICATE.getKey(),
					ErrorCode.USERNAME_DUPLICATE.getValue());
		try {
			Folder folder = new Folder();
			folder.setUserId(user);
			folder.setRoot(true);
			folder.setFolderName("root");
			user.setFolders(Arrays.asList(folder));
			User userResponse = userRepository.save(user);
			folder.setUserId(userResponse);
			folderRepository.save(folder);
			return userResponse;
		} catch (DataAccessException e) {
			throw new UserException(port, e.getMessage());
		}
	}

}