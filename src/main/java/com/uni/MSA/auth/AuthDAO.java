package com.uni.MSA.auth;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uni.MSA.datasource.AbstractDAO;

@Repository("authDAO")
public class AuthDAO extends AbstractDAO{
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectUserInfo(String memberIdNum) throws Exception {
		return (Map<String, Object>) selectOne("auth.selectUserInfo", memberIdNum);
	}
	
	public int updatePassword(Map<String, Object> map) throws Exception{
		return (int)update("auth.updatePassword", map);
	}
	
	//로그인 히스토리
	public void insertLoginHistory(Map<String, Object> map){
		insert("auth.insertLoginHistory", map);
	}
}
