package com.boot.s1.repository.search;

import com.boot.s1.domain.*;
import com.boot.s1.dto.BoardListReplyCountDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import com.boot.s1.domain.QBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

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
		QReply reply = QReply.reply;

		JPQLQuery<Board> query = from(board);
		query.leftJoin(reply).on(reply.board.eq(board));

		//검색조건과 키워드 관련
		if ((types != null && types.length > 0) && keyword != null) {

			BooleanBuilder booleanBuilder = new BooleanBuilder();

			for (String type: types) {
				// 제목, 내용, 작성자 검색
				switch (type) {
					case "t" :
						booleanBuilder.or(board.title.contains(keyword));
						break;
					case "c" :
						booleanBuilder.or(board.content.contains(keyword));
						break;
					case "w" :
						booleanBuilder.or(board.writer.contains(keyword));
						break;
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

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

		QBoard board = QBoard.board;
		QReply reply = QReply.reply;

		JPQLQuery<Board> query = from(board);
		//leftJion 시 on을 사용해서 조인조건지정, groupBy로 처리 후 게시물당 처리
		query.leftJoin(reply).on(reply.board.eq(board));

		query.groupBy(board);

		//검색조건 추가
		if((types != null && types.length > 0) && keyword != null) {
			BooleanBuilder booleanBuilder = new BooleanBuilder();
			for (String type: types) {
				switch (type) {
					case "t" :
						booleanBuilder.or(board.title.contains(keyword));
						break;
					case "c" :
						booleanBuilder.or(board.content.contains(keyword));
						break;
					case "w" :
						booleanBuilder.or(board.writer.contains(keyword));
						break;
				}
			} // end for
			query.where(booleanBuilder);
		}

		//bno > 0
		query.where(board.bno.gt(0L));

		JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections
				.bean(BoardListReplyCountDTO.class,
						board.bno,
						board.title,
						board.writer,
						board.regDate,
						reply.count().as("replyCount")
						));

		this.getQuerydsl().applyPagination(pageable, dtoQuery);
		List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();
		long count = dtoQuery.fetchCount();

		return new PageImpl<>(dtoList, pageable, count);
	}
}
