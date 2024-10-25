package com.example.e_commerce_app_backend.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private String userName;
    private String email;
    private String phone;
    private String password;
    private String address;
    private MultipartFile imageURL;
    private List<Long> roleIds;

}
