package com.icia.member.controller;

import com.icia.member.dto.MemberDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String memberSave(){
        return "memberPages/memberSave";
    }
    @GetMapping("/member/login")
    public String memberLogin(){
        return "memberPages/memberLogin";
    }
    @GetMapping("/member/list")
    public String memberList(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList",memberDTOList);
        return "memberPages/memberList";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "memberPages/memberLogin";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail",memberDTO.getMemberEmail());
            return "memberPages/memberMain";
        }else {
            return "memberPages/memberLogin";
        }
    }
    @GetMapping("/member/{id}")
    public String memberDetail(Model model, @PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "memberPages/memberDetail";
    }
    @GetMapping("/member/update/{id}")
    public String memberUpdate(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "memberPages/memberUpdate";
    }
    @PostMapping("/member/update")
    public String memberUpdate(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "memberPages/memberDetail";
    }
}
