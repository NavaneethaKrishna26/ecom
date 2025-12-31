package com.ecomapp.controller;

import com.ecomapp.dto.UserRequest;
import com.ecomapp.dto.UserResponse;

import com.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("ecomapp/users")
public class UserController
{
    @Autowired
    private UserService userservice;
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.status(200).body(userservice.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getSingleUser(@PathVariable Long id){

        Optional<UserResponse> optionalUser = userservice.getUser(id);
        if(optionalUser.isPresent()){
            UserResponse user=optionalUser.get();
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserResponse> postUser(@RequestBody UserRequest user){
        UserResponse userResponse=userservice.addNewUser(user);
        return ResponseEntity.status(201).body(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putUser(@PathVariable long id,@RequestBody UserRequest user){
        if(userservice.putnewUser(id,user)) {
          return ResponseEntity.status(201).body("Update Success");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> deleteUser(@PathVariable long id){
        if(userservice.deleteAUser(id)){
            return ResponseEntity.status(201).body("User Deleted");
        }
        return ResponseEntity.notFound().build();
    }
}
