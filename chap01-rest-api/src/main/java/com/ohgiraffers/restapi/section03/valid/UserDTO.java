package com.ohgiraffers.restapi.section03.valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    public String id;
    @Length(max = 10, message = "Length 10")
    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    public String password;
    @Size(max = 10,message = "Size 10")
    @NotNull(message = "NotNull")
    @NotBlank(message = "NotBlank")
    public String name;

    @Past(message = "Past")
    private Date enrolledDate;
}
