package com.boot.s1;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@Log4j2
public class DataSourceTests {
	
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void testConnection() throws Exception {
		
		@Cleanup
		Connection con = dataSource.getConnection();
		
		log.info(con);
//		성공하지 않는다면 테스트는 실패처리됨
		Assertions.assertNotNull(con);
	}
}
