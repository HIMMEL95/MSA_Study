package com.uni.MSA.datasource;

import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.StringUtils;

public class AbstractDAO {
	private static final Logger log = LoggerFactory.getLogger(AbstractDAO.class);
	
	@Autowired
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	protected void printQueryId(String queryId) {
		if (log.isDebugEnabled()) {
			log.debug("\t QueryId \t:  " + queryId);
		}
	}
	
	public Object insert(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
	
	public Object update(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}
	
	public Object delete(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}
	
	public Object selectOne(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public Object selectList(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public Object selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}
	
	@SuppressWarnings("unchecked")
	public Object selectPagingList(String queryId, Object params){
	    printQueryId(queryId);
	    Map<String,Object> map = (Map<String,Object>)params;
	     
	    String strPageIndex = (String)map.get("PAGE_INDEX");
	    String strPageRow = (String)map.get("PAGE_ROW");
	   
	    int nPageIndex = 0;
	    int nPageRow = 5;
	     
	    if(StringUtils.isNullOrEmpty(strPageIndex) == false){
	        nPageIndex = Integer.parseInt(strPageIndex)-1;
	    }
	    if(StringUtils.isNullOrEmpty(strPageRow) == false){
	        nPageRow = Integer.parseInt(strPageRow);
	    }
	    map.put("START", (nPageIndex * nPageRow) + 1);
	    map.put("END", (nPageIndex * nPageRow) + nPageRow);
	     
	    return sqlSession.selectList(queryId, map);
	}
}
