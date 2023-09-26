package com.icia.member.controller;

import com.icia.member.dto.MemberDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String memberSave() {
        return "memberPages/memberSave";
    }

    @GetMapping("/member/login")
    public String memberLogin() {
        return "memberPages/memberLogin";
    }

    @GetMapping("/member/list")
    public String memberList(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/memberList";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "memberPages/memberLogin";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "memberPages/memberMain";
        } else {
            return "memberPages/memberLogin";
        }
    }

    @GetMapping("/member/{id}")
    public String memberDetail(Model model, @PathVariable Long id) {
        try {
            MemberDTO memberDTO = memberService.findById(id);
            model.addAttribute("member", memberDTO);
            return "memberPages/memberDetail";
        } catch (Exception e) {
            return "memberPages/NotFound";
        }

    }

    @GetMapping("/member/update/{id}")
    public String memberUpdate(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberUpdate";
    }

    @PostMapping("/member/update")
    public String memberUpdate(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "memberPages/memberDetail";
    }

//    @PostMapping("/member/checkEmail")
//    public ResponseEntity emailCheck(@RequestParam("memberEmail") String memberEmail){
//        boolean memberEmailCheck = memberService.emailCheck(memberEmail);
//        if (memberEmailCheck) {
//            return new ResponseEntity<>(HttpStatus.OK); // http status code 200
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/member/checkEmail")
    public ResponseEntity checkEmail(@RequestBody MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        boolean result = memberService.checkEmail(memberDTO.getMemberEmail());
        if (result) {
            return new ResponseEntity<>("사용가능", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("사용불가능", HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/member/detail")
    public ResponseEntity memberDetail(@RequestBody MemberDTO memberDTO, Model model) {
        boolean result = memberService.memberDetail(memberDTO.getMemberEmail());
        if(result) {
            MemberDTO memberDTO1 = memberService.findByEmail(memberDTO.getMemberEmail());
            model.addAttribute("member",memberDTO1);
            return new ResponseEntity<>("있음",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("없음",HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginEmail");
        return "redirect:/member/login";
    }

    @GetMapping("/member/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable("id") Long id) {
        try {
            MemberDTO memberDTO = memberService.findById(id);
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
