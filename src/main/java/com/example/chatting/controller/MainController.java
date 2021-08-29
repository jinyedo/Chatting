package com.example.chatting.controller;

import com.example.chatting.security.AuthMemberDTO;
import com.example.chatting.security.ValidationMemberDTO;
import com.example.chatting.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
    public String getJoin(ValidationMemberDTO validationMemberDTO, Model model) {
        model.addAttribute("validationMemberDTO", validationMemberDTO);
        return "join";
    }

    @PostMapping("/join")
    public String postJoin(@Valid ValidationMemberDTO validationMemberDTO, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        ValidationMemberDTO result = memberService.join(validationMemberDTO, errors);
        if (!result.isJoinSuccessYn()) {
            model.addAttribute("validationMemberDTO", validationMemberDTO);
            model.addAttribute("msg", result.getMsg());
            return "join";
        } else {
            redirectAttributes.addFlashAttribute("msg", result.getMsg());
            return "redirect:/";
        }
    }

    @GetMapping("/room")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getRoom(@AuthenticationPrincipal AuthMemberDTO authMemberDTO, Model model) {
        model.addAttribute("userId", authMemberDTO.getUsername());
        return "room";
    }

    @GetMapping("/chatting")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getChatting() {
        return  "chatting";
    }
}
