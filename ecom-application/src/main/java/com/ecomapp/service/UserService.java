package com.ecomapp.service;

import com.ecomapp.dto.AddressDTO;
import com.ecomapp.dto.UserRequest;
import com.ecomapp.dto.UserResponse;
import com.ecomapp.model.Address;
import com.ecomapp.model.User;
import com.ecomapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService
{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream().map(this::userToUserResponse).collect(Collectors.toList());
    }

    public Optional<UserResponse> getUser(Long id){
        return userRepository.findById(id).map(this::userToUserResponse);
    }


    public UserResponse addNewUser(UserRequest user){
        User newuser=new User();
        userRequestToUser(newuser,user);
        User user1 = userRepository.save(newuser);
        return userToUserResponse(user1);
    }

    public boolean putnewUser(long id, UserRequest Updateduser){
       return userRepository.findById(id).map(existinguser ->{
                   userRequestToUser(existinguser,Updateduser);
                   userRepository.save(existinguser);
                   return true;
               }).orElse(false);
    }

    public boolean deleteAUser(long id){
        return userRepository.findById(id).map(user ->{
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    public UserResponse userToUserResponse(User user){
        UserResponse userResponse=new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPno(user.getPno());
        if(user.getAddress()!=null){
            AddressDTO addressDTO=new AddressDTO();
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDTO);
        }
        return  userResponse;

    }

    public void userRequestToUser(User existinguser,UserRequest updateduser){
        existinguser.setFirstName(updateduser.getFirstName());
        existinguser.setLastName(updateduser.getLastName());
        existinguser.setEmail(updateduser.getEmail());
        existinguser.setPno(updateduser.getPno());
        if(updateduser.getAddress()!=null){
            Address address = existinguser.getAddress();
            if(address==null){
                address=new Address();
            }
            address.setStreet(updateduser.getAddress().getStreet());
            address.setCity(updateduser.getAddress().getCity());
            address.setState(updateduser.getAddress().getState());
            address.setCountry(updateduser.getAddress().getCountry());
            address.setZipcode(updateduser.getAddress().getZipcode());
            existinguser.setAddress(address);
        }
    }
}
