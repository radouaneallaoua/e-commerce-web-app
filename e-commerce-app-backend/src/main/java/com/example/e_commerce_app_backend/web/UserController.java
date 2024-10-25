package com.example.e_commerce_app_backend.web;

import com.example.e_commerce_app_backend.dtos.NotificationResponseDTO;
import com.example.e_commerce_app_backend.dtos.UserRequestDto;
import com.example.e_commerce_app_backend.dtos.UserResponseDto;
import com.example.e_commerce_app_backend.enums.NotificationStatus;
import com.example.e_commerce_app_backend.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return  userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id){
        return  userService.getUserById(id);
    }

    @PostMapping(value = "",consumes = "multipart/form-data")
    public ResponseEntity<UserResponseDto> saveUser(@ModelAttribute UserRequestDto userRequestDto
    ) throws IOException {

        return userService.saveUser(userRequestDto);

    }

    @PutMapping(value = "/{userId}",consumes = "multipart/form-data")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable String userId,
          @ModelAttribute UserRequestDto userRequestDto
    ) throws IOException {

        return userService.updateUser(userId,userRequestDto);

    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<UserResponseDto> getUserRoles(@PathVariable String userId){
        return  userService.getUserRoles(userId);
    }

    @PostMapping("/users/authenticate")
    public ResponseEntity<UserResponseDto> authenticateUserWithCredentials(String email,String password){
        return userService.authenticateUserWithEmailAndPassword(email,password);

    }

   @GetMapping(value = "/users/{userId}/avatar",produces = "multipart/form-data")
    public byte[] getUserImage(@PathVariable String userId) throws IOException {
        return  userService.getUserImage(userId);
   }


   @GetMapping("/users/{userId}/notifications/all")
    public ResponseEntity<List<NotificationResponseDTO>> allUserNotifications(@PathVariable String userId){
        return  userService.getAllUserNotifications(userId);
   }

    @GetMapping("/users/{userId}/notifications/all/dateAfter")
    public ResponseEntity<List<NotificationResponseDTO>> allUserNotificationsDateAfter(@PathVariable String userId, @RequestParam LocalDateTime date){
        return  userService.getAllUserNotificationsWithDateAfter(userId,date);
    }

    @GetMapping("/users/{userId}/notifications/all/dateBefore")
    public ResponseEntity<List<NotificationResponseDTO>> allUserNotificationsDateBefore(@PathVariable String userId, @RequestParam LocalDateTime date){
        return  userService.getAllUserNotificationsWithDateBefore(userId,date);
    }

    @GetMapping("/users/{userId}/notifications/all/dateBetween")
    public ResponseEntity<List<NotificationResponseDTO>> allUserNotificationsDateBetween(@PathVariable String userId, @RequestParam("date1") LocalDateTime date1,@RequestParam("date2") LocalDateTime date2){
        return  userService.getAllUserNotificationsWithDateBetween(userId,date1,date2);
    }

    @GetMapping("/users/{userId}/notifications/all/withstatus")
    public ResponseEntity<List<NotificationResponseDTO>> allUserNotificationsWithStatus(@RequestParam NotificationStatus status,@PathVariable String userId){
        return  userService.getAllUserNotificationsWithStatus(userId,status);
    }

    @GetMapping("/users/{userId}/notifications/all/atDate")
    public ResponseEntity<List<NotificationResponseDTO>> allUserNotificationsAtDate(@RequestParam LocalDateTime date,@PathVariable String userId){
        return  userService.getAllUserNotificationsWithDate(userId,date);
    }



}
