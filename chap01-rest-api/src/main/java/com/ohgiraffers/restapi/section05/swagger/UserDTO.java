package com.ohgiraffers.restapi.section05.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "회원 정보")
public class UserDTO {
    @Schema(description = "회원 번호")
    private int no;
    @Schema(description = "회원 아이디")
    public String id;
    @Schema(description = "회원 비밀번호")
    public String password;
    @Schema(description = "회원 이름")
    public String name;
    @Schema(description = "회원 가입일")
    private Date enrolledDate;
}
