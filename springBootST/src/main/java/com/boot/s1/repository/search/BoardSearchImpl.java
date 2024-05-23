package com.boot.s1.repository.search;

import com.boot.s1.domain.Board;
import com.boot.s1.domain.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import com.boot.s1.domain.QBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

	public BoardSearchImpl() {
		super(Board.class);
	}

	@Override
	public Page<Board> search1(Pageable pageable) {
		
		QBoard board = QBoard.board;
		
		JPQLQuery<Board> query = from(board);
//		키워드 검색 ()가 필요하다면 booleanBuilder 사용
		BooleanBuilder booleanBuilder = new BooleanBuilder();

		booleanBuilder.or(board.title.contains("11"));

		booleanBuilder.or(board.content.contains("11"));

		query.where(booleanBuilder);
		query.where(board.bno.gt(0L));


//		paging 관련 (QuerydslRepositorySupport 사용)
		this.getQuerydsl().applyPagination(pageable, query);
		List<Board> list = query.fetch();
		long count = query.fetchCount();

		return null;
	}

	public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

		QBoard board = QBoard.board;
		JPQLQuery<Board> query = from(board);

		//검색조건과 키워드 관련
		if ((types != null && types.length > 0) && keyword != null) {

			BooleanBuilder booleanBuilder = new BooleanBuilder();

			for (String type: types) {
				// 제목, 내용, 작성자 검색
				switch (type) {
					case "t" :
						booleanBuilder.or(board.title.contains(keyword));
					case "c" :
						booleanBuilder.or(board.content.contains(keyword));
					case "w" :
						booleanBuilder.or(board.writer.contains(keyword));
				}
			}
			query.where(booleanBuilder);
		}
		//bno > 0 일때
		query.where(board.bno.gt(0L));

		//paging
		this.getQuerydsl().applyPagination(pageable, query);
		List<Board> list = query.fetch();
		long count = query.fetchCount();

		return null;
	}
}
