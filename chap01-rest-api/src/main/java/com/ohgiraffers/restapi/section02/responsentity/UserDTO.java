package com.ohgiraffers.restapi.section02.responsentity;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private int no;

    public String id;
    public String password;
    public String name;
    private Date enrolledDate;
}
