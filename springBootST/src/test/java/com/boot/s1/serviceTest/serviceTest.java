package com.boot.s1.serviceTest;

import com.boot.s1.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class serviceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void registerTest() {

        log.info("registerTest : " + boardService.getClass().getName());
    }
}
