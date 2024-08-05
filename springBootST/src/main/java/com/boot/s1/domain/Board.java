package com.boot.s1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@Column(length = 500, nullable = false) // 컬럼의 길이와 null값의 허용여부 표시
	private String title;
	
	@Column(length = 2000, nullable = false)
	private String content;
	
	@Column(length = 50, nullable = false)
	private String writer;

	@OneToMany(mappedBy = "board",
				cascade = {CascadeType.ALL},
				fetch = FetchType.LAZY,
				orphanRemoval = true) // BoradImage의 board 변수
	@Builder.Default
	@BatchSize(size = 20)
	private Set<BoardImage> imageSet = new HashSet<>();


	public void addImage(String uuid, String fileName) {
		BoardImage boardImage = BoardImage.builder()
				.uuid(uuid)
				.fileName(fileName)
				.board(this)
				.ord(imageSet.size())
				.build();
		imageSet.add(boardImage);
	}

	public void clearImages() {
		imageSet.forEach(boardImage -> boardImage.changeBoard(null));
		this.imageSet.clear();
	}
	public void change(String title, String content) {
		this.title = title;
		this.content = content;
	}


}
