package com.boot.s1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@Column(length = 500, nullable = false) // 컬럼의 길이와 null값의 허용여부 표시
	private String title;
	
	@Column(length = 2000, nullable = false)
	private String content;
	
	@Column(length = 50, nullable = false)
	private String writer;
	
	public void change(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
