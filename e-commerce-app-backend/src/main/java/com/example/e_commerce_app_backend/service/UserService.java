package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.Exception.UserNotFoundException;
import com.example.e_commerce_app_backend.Exception.UserRoleNotFoundException;
import com.example.e_commerce_app_backend.dtos.NotificationResponseDTO;
import com.example.e_commerce_app_backend.dtos.UserRequestDto;
import com.example.e_commerce_app_backend.dtos.UserResponseDto;
import com.example.e_commerce_app_backend.entities.Notification;
import com.example.e_commerce_app_backend.entities.Role;
import com.example.e_commerce_app_backend.enums.NotificationStatus;

import com.example.e_commerce_app_backend.entities.User;
import com.example.e_commerce_app_backend.repository.NotificationRepository;
import com.example.e_commerce_app_backend.repository.RoleRepository;
import com.example.e_commerce_app_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, NotificationRepository notificationRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.roleRepository = roleRepository;
    }
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<User> users=userRepository.findAll();
        List<UserResponseDto> dtos=new ArrayList<>();
        users.forEach(u->{
            dtos.add(u.toDto());
        });
        return  ResponseEntity.of(Optional.of(dtos));
    }
    public ResponseEntity <UserResponseDto> getUserById(String id){
        User user=  userRepository.findById(id).orElse(null);
        if(user==null){
            throw new UserNotFoundException("user not found with id"+id);
        }
        return  ResponseEntity.ok(user.toDto());
    }

    public ResponseEntity<UserResponseDto> authenticateUserWithEmailAndPassword(String email,String password){
        User foundUser=userRepository.findUserByEmailAndPassword(email,password);
        if(foundUser!=null){
            return  ResponseEntity.ok(foundUser.toDto());
        }
        return ResponseEntity.ok(UserResponseDto.builder().userName("not found").build());

    }
    public ResponseEntity<String> deleteUserById(String userId){
        userRepository.deleteById(userId);
        return ResponseEntity.ok("User deleted with id"+userId);
    }

    public ResponseEntity<UserResponseDto> saveUser(UserRequestDto userRequestDto) throws IOException {
        Path path= Paths.get(System.getProperty("user.home"),"e-commerce-app","user_images");
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        String fileId=UUID.randomUUID().toString();
        String fileExtension= Objects.requireNonNull(userRequestDto.getImageURL().getOriginalFilename()).substring(userRequestDto.getImageURL().getOriginalFilename().lastIndexOf("."));
        Path filePath=Paths.get(System.getProperty("user.home"),"e-commerce-app","user_images",fileId+fileExtension);
        Files.copy(userRequestDto.getImageURL().getInputStream(),filePath);
       List<Role> userRoles=new ArrayList<>();
       userRequestDto.getRoleIds().forEach(r->{
           try {
               Role role=roleRepository.findById(r).orElseThrow(()-> new UserRoleNotFoundException("role not found"));
               userRoles.add(role);
           } catch (UserRoleNotFoundException e) {
               throw new RuntimeException(e);
           }

       });

        User user=User.builder()
                .id(UUID.randomUUID().toString())
                .address(userRequestDto.getAddress())
                .userName(userRequestDto.getUserName())
                .phone(userRequestDto.getPhone())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .avatar(filePath.toUri().toString())
                .roles(userRoles)
                .build();
        User savedUser=userRepository.save(user);
        return  ResponseEntity.ok(savedUser.toDto());

    }


    public ResponseEntity<UserResponseDto> getUserRoles(String userId){
        User user=userRepository.findById(userId).orElse(null);
        if(user==null){
            throw new UserNotFoundException("user not found with id "+userId);
        }
        return  ResponseEntity.ok(user.toDto());
    }
    public byte[] getUserImage(@PathVariable String userId) throws IOException {
        ResponseEntity<UserResponseDto> user=this.getUserById(userId);
        if(user!=null){
            return Files.readAllBytes(Path.of(URI.create(Objects.requireNonNull(user.getBody()).getImageURL())));
        }
        return new byte[0];
    }

    public ResponseEntity<List<NotificationResponseDTO>> getAllUserNotifications(String userId){
        List<Notification> notifications= notificationRepository.findAllByUserId(userId);
        List<NotificationResponseDTO> responseDTOS=new ArrayList<>();
        if(notifications!=null) {
            notifications.forEach(n -> {
                responseDTOS.add(n.toDto());
            });
        }
        return  ResponseEntity.ok(responseDTOS);
    }

    public ResponseEntity<List<NotificationResponseDTO>> getAllUserNotificationsWithDate(String userId, LocalDateTime date){
        List<Notification> notifications= notificationRepository.findAllByUserIdAndDate(userId,date);
        List<NotificationResponseDTO> responseDTOS=new ArrayList<>();
        if(notifications!=null) {
            notifications.forEach(n -> {
                responseDTOS.add(n.toDto());
            });
        }
        return  ResponseEntity.ok(responseDTOS);
    }

    public ResponseEntity<List<NotificationResponseDTO>> getAllUserNotificationsWithDateAfter(String userId, LocalDateTime date){
        List<Notification> notifications= notificationRepository.findAllByUserIdAndDateAfter(userId,date);
        List<NotificationResponseDTO> responseDTOS=new ArrayList<>();
        if(notifications!=null) {
            notifications.forEach(n -> {
                responseDTOS.add(n.toDto());
            });
        }
        return  ResponseEntity.ok(responseDTOS);
    }

    public ResponseEntity<List<NotificationResponseDTO>> getAllUserNotificationsWithDateBefore(String userId, LocalDateTime date){
        List<Notification> notifications= notificationRepository.findAllByUserIdAndDateBefore(userId,date);
        List<NotificationResponseDTO> responseDTOS=new ArrayList<>();
        if(notifications!=null) {
            notifications.forEach(n -> {
                responseDTOS.add(n.toDto());
            });
        }
        return  ResponseEntity.ok(responseDTOS);
    }

    public ResponseEntity<List<NotificationResponseDTO>> getAllUserNotificationsWithDateBetween(String userId, LocalDateTime date1,LocalDateTime date2){
        List<Notification> notifications= notificationRepository.findAllByUserIdAndDateBetween(userId,date1,date2);
        List<NotificationResponseDTO> responseDTOS=new ArrayList<>();
        notifications.forEach(n->{
            responseDTOS.add(n.toDto());
        });
        return  ResponseEntity.ok(responseDTOS);
    }

    public ResponseEntity<List<NotificationResponseDTO>> getAllUserNotificationsWithStatus(String userId, NotificationStatus status){
        List<Notification> notifications= notificationRepository.findAllByUserIdAndNotificationStatus(userId,status);
        List<NotificationResponseDTO> responseDTOS=new ArrayList<>();
        if(notifications!=null) {
            notifications.forEach(n -> {
                responseDTOS.add(n.toDto());
            });
        }
        return  ResponseEntity.ok(responseDTOS);
    }

    public ResponseEntity<UserResponseDto> updateUser(String userId, UserRequestDto userRequestDto) throws IOException {
        User user=userRepository.findById(userId).orElse(null);
        if(user==null){
            throw new UserNotFoundException("user not found with id"+userId);
        }
        String newAvatarName=UUID.randomUUID().toString();
        String extension=userRequestDto.getImageURL().getOriginalFilename().substring(userRequestDto.getImageURL().getOriginalFilename().lastIndexOf("."));
        Path newAvatarPath=Paths.get(System.getProperty("user.home"),"e-commerce-app","user_images",newAvatarName+extension);
        String avatarName=user.getAvatar();
        Files.delete(Path.of(URI.create(avatarName)));
        Files.copy(userRequestDto.getImageURL().getInputStream(),newAvatarPath);
        List<Role> roles=new ArrayList<>();
        userRequestDto.getRoleIds().forEach(id->{
            try {
                Role role=roleRepository.findById(id).orElseThrow(()-> new UserRoleNotFoundException("role not found"));
                roles.add(role);
            } catch (UserRoleNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
        user.setAvatar(newAvatarPath.toUri().toString());
        user.setAddress(userRequestDto.getAddress());
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setPhone(userRequestDto.getPhone());
        user.setRoles(roles);
        return  ResponseEntity.ok(userRepository.save(user).toDto());

    }
}
