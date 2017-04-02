package com.captcha.repository;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.captcha.model.User;

@Repository
public class UserRepository {    
	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String GET_USER_BY_USERNAME = "Select u.ID, u.USERNAME, u.PASSWORD, r.role from APP_USER u left join USER_ROLE r on (u.id = r.user_id) where u.username like(:username)";
	
    public User findByUsername(String username){
    	MapSqlParameterSource paramMap = new MapSqlParameterSource();
    	paramMap.addValue("username", username);
    	
		List<User> users= jdbcTemplate.query(GET_USER_BY_USERNAME, paramMap , new UserExtarctor(new UserMapper(), new RoleMapper()));
		
    	return CollectionUtils.isEmpty(users)? null: users.get(0);
    }
}
