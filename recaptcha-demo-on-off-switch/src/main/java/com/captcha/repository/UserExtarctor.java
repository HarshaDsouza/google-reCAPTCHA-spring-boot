package com.captcha.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.data.jdbc.core.OneToManyResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.captcha.model.User;

public class UserExtarctor extends OneToManyResultSetExtractor<User, String, Long>{

	public UserExtarctor(RowMapper<User> rootMapper, RowMapper<String> childMapper) {
		super(rootMapper, childMapper);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Long mapPrimaryKey(ResultSet rs) throws SQLException {
		
		return rs.getLong("id");
	}

	@Override
	protected Long mapForeignKey(ResultSet rs) throws SQLException {
		
		return rs.getLong("id");
	}

	@Override
	protected void addChild(User root, String child) {
				
		root.getRoles().add(child);
		
	}

}
