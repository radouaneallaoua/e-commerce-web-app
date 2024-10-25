package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.NotificationResponseDTO;
import com.example.e_commerce_app_backend.dtos.RoleResponseDTO;
import com.example.e_commerce_app_backend.dtos.UserResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")

public class User {
    @Id
    private String id;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String avatar;
    @ManyToMany
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns =@JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<ProductEvaluation> evaluations;

    @OneToMany(mappedBy = "user")
    private List<ProductReaction> reactions;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Notification> notifications;



    public UserResponseDto toDto(){
        List<NotificationResponseDTO> notificationsDtos=new ArrayList<>();
        if(notifications!=null) {
            notifications.forEach(n -> {
                notificationsDtos.add(n.toDto());
            });
        }
        List<RoleResponseDTO> roleResponseDTOS=new ArrayList<>();
        if(roles!=null) {
            roles.forEach(n -> {
                roleResponseDTOS.add(n.toDTO());
            });
        }
        return  UserResponseDto.builder()
                .id(id)
                .imageURL(avatar)
                .address(address)
                .userName(userName)
                .phone(phone)
                .password(password)
                .roles(roleResponseDTOS)
                .email(email)
                .notications(notificationsDtos)
                .build();
    }


}
