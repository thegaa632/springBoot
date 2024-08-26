package com.boot.s1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @RequestMapping("/login")
    public void loginGET(String error, String logout) {
        log.info("login get .............");
        log.info("logout: " + logout);

        if(logout != null){
            log.info("유저가 로그아웃............");
        }
    }
}
