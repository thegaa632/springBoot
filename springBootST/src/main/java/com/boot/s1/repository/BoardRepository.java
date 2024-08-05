package com.boot.s1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.boot.s1.domain.Board;
import com.boot.s1.repository.search.BoardSearch;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
//상속시 entity, @id 타입을 지정해줘야함

	@Query(value = "select now()", nativeQuery = true)
	String getTime();



}
