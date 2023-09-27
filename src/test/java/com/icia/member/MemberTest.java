package com.icia.member;

import com.icia.member.dto.MemberDTO;
import com.icia.member.repository.MemberRepository;
import com.icia.member.service.MemberService;
// Assertions에 속한 모든 static 메서드를 사용할 때 메서드 이름만 작성해서 사용 가능하도록
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    // 회원가입용 메서드 정의
    private MemberDTO newMember(int i) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test_email"+i);
        memberDTO.setMemberPassword("test_pass"+i);
        memberDTO.setMemberName("test_name"+i);
        memberDTO.setMemberBirth("2023-09-27"+i);
        memberDTO.setMemberMobile("010-1234-5678"+i);
        return memberDTO;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 데이터 붓기")
    public void dataInsert() {
//        for(int i = 1; i <= 20; i++) {
//            MemberDTO memberDTO = newMember(i);
//            memberService.save(memberDTO);
//        }
        IntStream.rangeClosed(1,20).forEach(i -> {
            MemberDTO memberDTO = newMember(i);
            memberService.save(memberDTO);
        });
    }

    //회원가입 기능 테스트
    /*
        1. 새로운 회원을 하나 가입시킴
        2. 가입된 회원의 id 값ㅂ을 받아옴
        3. 그 id로 조회 기능을 실행
        4. 1번에서 만든 회원의 이메일 값과 3번에서 조회한 이메일 값이 일치하면 선공
     */

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입 테이트")
    public void memberSaveTest() {
        // 1.
        MemberDTO newMember = new MemberDTO();
        newMember.setMemberEmail("test_email");
        newMember.setMemberPassword("test_pass");
        newMember.setMemberName("test_name");
        newMember.setMemberBirth("2023-09-27");
        newMember.setMemberMobile("010-1234-5678");
        // 2.
        Long saveId = memberService.save(newMember);
        // 3.
        MemberDTO findMember = memberService.findById(saveId);
        // 4.
        assertThat(newMember.getMemberEmail()).isEqualTo((findMember.getMemberEmail()));
    }

    //로그인 테스트
    /*
        1. 신규회원 가입
        2. 로그인을 위한 DTO 객체 생성 후 로그인
        3. 로그인 결과 값이 있는지를 통해서 성공여부 판단
     */
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public void loginTest() {
        // 1.
        MemberDTO memberDTO = newMember(999);
        memberService.save(memberDTO);
        // 2.
        MemberDTO loginMember = new MemberDTO();
        loginMember.setMemberEmail(memberDTO.getMemberEmail());
        loginMember.setMemberPassword(memberDTO.getMemberPassword());
        boolean loginResult = memberService.login(loginMember);
        // 3.
        assertThat(true).isEqualTo(loginResult);
    }

    // 삭제기능 테스트
    /*
        1. 회원가입 수행
        2. 회원가입 후 id 받아옴
        3. 삭제 기능 수행
        4. 삭제 후 해동 id로 조회했을 때
     */
    @Test
    @Transactional
    @Rollback
    @DisplayName("회원 삭제 테스트")
    public void deleteTest() {
        MemberDTO memberDTO = newMember(9999);
        Long savedId = memberService.save(memberDTO);
        memberService.delete(savedId);
        // 4.
        assertThatThrownBy(() -> memberService.findById(savedId)).isInstanceOf(NoSuchElementException.class);

    }
}
