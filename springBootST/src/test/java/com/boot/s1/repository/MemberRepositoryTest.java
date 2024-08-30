package com.boot.s1.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.boot.s1.domain.MemberRole;
import com.boot.s1.domain.Member;

import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //100명 생성 90명 이후는 admin 부여
    @Test
    public void insertMember() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email" + i + "@aaa.bbb")
                    .build();

            member.addRole(MemberRole.USER);

            if(i >= 90) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }
    
    //회원 조회 test
    @Test
    public void selectMember() {
        Optional<Member> result = memberRepository.getWithRoles("member100");
        
        Member member = result.orElseThrow();
        
        log.info(member);
        log.info(member.getRoleSet());
        
        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
        
    }
}
