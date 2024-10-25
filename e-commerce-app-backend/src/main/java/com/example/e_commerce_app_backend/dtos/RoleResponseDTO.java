package com.example.e_commerce_app_backend.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {
    private Long roleId;
    private String roleName;
}
