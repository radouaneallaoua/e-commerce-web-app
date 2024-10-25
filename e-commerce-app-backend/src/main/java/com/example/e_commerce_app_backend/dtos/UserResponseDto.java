package com.example.e_commerce_app_backend.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String id;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private String address;
    private List<RoleResponseDTO> roles;
    private String imageURL;
    private List<NotificationResponseDTO> notications;


}
