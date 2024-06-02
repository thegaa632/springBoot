package com.boot.s1.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import com.boot.s1.dto.BoardDTO;
import com.boot.s1.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

import com.boot.s1.domain.Board;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

	@Test
	public void insertTest() {
//		1 ~ 100 까지 각각 title, content, writer 등록, 번호는 자동으로 증가
		IntStream.range(1,101).forEach(i -> {
			Board board = Board.builder()
					.title("title ..." + i)
					.content("content ... " + i)
					.writer("writer ... " + i)
					.build();
//			해당 결과를 저장
			Board result = boardRepository.save(board);
//			로그로 출력
			log.info("bno: " + result.getBno());
		});
	}

	@Test
	public void testRegister() {
		log.info(boardService.getClass().getName());

		BoardDTO boardDTO = BoardDTO.builder()
				.title("sample Title...")
				.content("sample content")
				.writer("sample writer")
				.build();

		Long bno = boardService.register(boardDTO);
		log.info("bno: " + bno);
	}

//	@Test
//	public void selectTest() {
////		검색할 번호
//		Long bno = 100L;
////		해당 번호로 게시판의 엔터티 id 찾기
//		Optional<Board> result = boardRepository.findById(bno);
////		예외처리 후 board에 저장
//		Board board = result.orElseThrow();
////		결과를 표시
//		log.info(board);
//	}
	
//	@Test
//	public void updateTest() {
////		업데이트할 게시판의 번호
//		Long bno = 100L;
////		해당 번호로 게시판의 엔터티의 id 찾기
//		Optional<Board> result = boardRepository.findById(bno);
////		예외처리 후 board에 저장
//		Board board = result.orElseThrow();
////		title과 content 변경
//		board.change("update..title 100", "update..content 100");
////		해당 결과를 저장
//		boardRepository.save(board);
//	}
	
//	@Test
//	public void deleteTest() {
////		삭제할 게시판의 번호
//		Long bno = 100L;
////		해당 번호 데이터 삭제
//		boardRepository.deleteById(bno);
//	}
	
//	수정이나 삭제시에 select문을 실행하여 기존에 존재하는 엔터티 객체를 영속 컨텍스트에 추가하여 데이터베이스와 동기화 하기위한 준비를 해야함(update와 delete)
	
	@Test
	public void pagingTest() {
		//1 page order by desc
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		
		Page<Board> result = boardRepository.findAll(pageable);

		//페이징 확인
		log.info("total count : " + result.getTotalElements());
		log.info("total pages : " + result.getTotalPages());
		log.info("total number : " + result.getNumber());
		log.info("total size : " + result.getSize());
	}
	
	@Test
	public void search1Test() {
		//2 page order by desc
		log.info("search1Test 실행");
		Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

		boardRepository.search1(pageable);
	}
	@Test
	public void searchAllTest() {
		log.info("searchAllTest 실행");

		String[] types = {"t", "c", "w"};
		String keyword = "1";
		Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

		Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

		//total page
		log.info(result.getTotalPages());
		//page size
		log.info(result.getSize());
		//page number
		log.info(result.getNumber());
		//prev next
		log.info(result.hasPrevious() + " : " + result.hasNext());

		result.getContent().forEach(board -> log.info(board));
	}
}
