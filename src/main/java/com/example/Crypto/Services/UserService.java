package com.example.Crypto.Services;

import com.example.Crypto.DTOs.UserDTO;
import com.example.Crypto.Entities.User;
import com.example.Crypto.Mappers.UserMapper;
import com.example.Crypto.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getUserById(Long userId){
        return getUser(userId);
    }

    public User createUser(UserDTO userDTO){
        var username = userDTO.username();

        var userFoundByName = userRepository.findUserByUsername(username);
        if(userFoundByName.isPresent())
            throw new IllegalStateException("Account with username" + username + "already exists.");

        return saveUserToDatabase(userDTO);
    }

    public Double getUserBalance(Long userId) {
        if(!userRepository.existsById(userId))
            throw new EntityNotFoundException("Account ID:" + userId);
        return userRepository.findBalanceById(userId);
    }

    public void updateAccountBalance(Long userId,Float newBalance){
        if(!userRepository.existsById(userId))
            throw new EntityNotFoundException("Account ID:" + userId);
        userRepository.updateBalance(userId, newBalance.doubleValue());
    }

    public void resetAccountBalance(Long userId){
        userRepository.updateBalance(userId, 10000.0);
    }

    //util
    private User saveUserToDatabase(UserDTO userDTO)
    {
        var user = userMapper.convertDtoToEntity(userDTO);
        user.setBalance(10000.0);
        return userRepository.saveAndFlush(user);
    }

    private User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User ID:" + userId));
    }
}
