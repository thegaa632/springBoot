package com.boot.s1.repository;

import java.util.stream.IntStream;

import javax.swing.border.Border;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.boot.s1.domain.Board;

import lombok.Builder;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	public void insertTest() {
		IntStream.range(1,100).forEach(i -> {
			Board board = Board.builder()
					.title("title ..." + i)
					.content("content ... " + i)
					.writer("writer ... " + i)
					.build();
			
			Board result = boardRepository.save(board);
			log.info("bno: " + result.getBno());
		});
	}
}
