package com.boot.s1.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
//@EntityListeners를 사용하기 위해서 메인 Application에 @EnableJpaAuditing을 추가해야함
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

	// 	AuditingEntityListener를 적용하면 데이터베이스 추가, 변경시 자동으로 시간값을 지정함
	@CreatedDate
	@Column(name = "regDate", updatable = false)
	private LocalDateTime regDate;
	
	@LastModifiedDate
	@Column(name = "modDate")
	private LocalDateTime modDate;
}
