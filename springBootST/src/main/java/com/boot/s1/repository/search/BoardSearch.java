package com.boot.s1.repository.search;

import com.boot.s1.dto.BoardListAllDTO;
import com.boot.s1.dto.BoardListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.boot.s1.domain.Board;

public interface BoardSearch {
	Page<Board> search1(Pageable pageable);

	Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

	Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

	Page<BoardListAllDTO> searchWithAll(String[] types,
										String keyword,
										Pageable pageable);
}
