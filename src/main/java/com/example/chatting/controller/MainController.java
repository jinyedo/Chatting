package com.example.chatting.controller;

import com.example.chatting.service.member.MemberDTO;
import com.example.chatting.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/")
    public String postLogin() {
        return "login";
    }

    @GetMapping("/join")
    public String getJoin(MemberDTO memberDTO) {
        return "join";
    }

    @PostMapping("/join")
    public String postJoin(@Valid MemberDTO memberDTO, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("memberDTO", memberDTO); // 회원가입 실패시, 입력 데이터를 유지
            log.info("-----유효성 검사 오류 종류-----");
            for (FieldError error : errors.getFieldErrors()) {
                log.info(String.format("valid_%s", error.getField()) + " : " + error.getDefaultMessage());
            }
            log.info("----------------------------");
            return "join";
        }
        String result = memberService.join(memberDTO);
        if (result.equals("fail")) {
            model.addAttribute("memberDTO", memberDTO);
            model.addAttribute("msg", memberDTO.getUsername() + "은 이미 존재하는 아이디입니다.");
            return "/join";
        } else if (result.equals("success")) {
            redirectAttributes.addFlashAttribute("msg", "회원가입 성공");
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/room")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getRoom() {
        return "room";
    }

    @GetMapping("/chatting")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getChatting() {
        return  "chatting";
    }
}
