package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.RoleResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String roleName;

    @ManyToMany
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "role_id"),inverseJoinColumns =@JoinColumn(name = "user_id"))
    private List<User> userList;

    public RoleResponseDTO toDTO(){
        return RoleResponseDTO.builder()
                .roleId(roleId)
                .roleName(roleName)
                .build();
    }
}
