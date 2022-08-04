package com.mi.services.service;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	List<UserDetails> userDetailList;

	public List<UserDetails> getUserDetails() throws Exception {

		if (userDetailList == null || userDetailList.isEmpty()) {

			userDetailList = new ArrayList<UserDetails>();

			URL userFileUrl = this.getClass().getClassLoader().getResource("users.csv");

			File userFile = new File(userFileUrl.toURI());

			List<String> lines = Files.readAllLines(userFile.toPath());
			for (String line : lines) {

				String[] lineParts = line.split(",");

				String user = lineParts[0];

				String pwd = lineParts[1];

				String[] roles = lineParts[2].split(":");

				UserDetails userDetails = User.withUsername(user).password(pwd).roles(roles).build();

				userDetailList.add(userDetails);

			}
		}

		return userDetailList;
	}

	public UserDetails getUserDetails(String userName, String pwd) {

		return userDetailList.stream().filter(ud -> ud.getUsername().equals(userName) && ud.getPassword().equals(pwd))
				.findFirst().orElseGet(null);
	}

	public Optional<UserDetails> findByUsername(String usernameFromToken) {

		return userDetailList.stream().filter(ud -> ud.getUsername().equals(usernameFromToken)).findFirst();
	}

}
