package com.icia.member.entity;

import com.icia.member.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String member_email;
    @Column(nullable = false, length = 20)
    private String member_password;
    @Column(nullable = false, length = 20)
    private String member_name;
    @Column(length = 20)
    private String member_birth;
    @Column(length = 30)
    private String member_mobile;

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMember_email(memberDTO.getMemberEmail());
        memberEntity.setMember_password(memberDTO.getMemberPassword());
        memberEntity.setMember_birth(memberDTO.getMemberBirth());
        memberEntity.setMember_mobile(memberDTO.getMemberMobile());
        memberEntity.setMember_name(memberDTO.getMemberName());
        return memberEntity;
    }

}
