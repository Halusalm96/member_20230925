package com.icia.member.dto;

import com.icia.member.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberBirth;
    private String memberMobile;

    public static MemberDTO toMemberList(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMember_email());
        memberDTO.setMemberBirth(memberEntity.getMember_birth());
        memberDTO.setMemberMobile(memberEntity.getMember_mobile());
        memberDTO.setMemberName(memberEntity.getMember_name());
        memberDTO.setMemberPassword(memberEntity.getMember_password());
        return memberDTO;
    }
}
