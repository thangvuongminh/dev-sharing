package com.example.identityservice.service.impl;

import com.dev.sharing.api.commom.exception.DevSharingException;
import com.example.identityservice.dto.UserRegisterDto;
import com.example.identityservice.dto.response.UserRegistrationResponseDto;
import com.example.identityservice.entity.Role;
import com.example.identityservice.entity.RoleEnum;
import com.example.identityservice.entity.User;
import com.example.identityservice.entity.UserRole;
import com.example.identityservice.exception.ExceptionEnum;
import com.example.identityservice.mapper.UserRegistrationMapper;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.repository.UserRepository;
import com.example.identityservice.repository.UserRoleRepository;
import com.example.identityservice.service.UserAccountService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserAccountServiceImpl  implements UserAccountService {
    RoleRepository roleRepository;
    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;
    UserRegistrationMapper userRegistrationMapper;
    @Override
    @Transactional
    public UserRegistrationResponseDto registerUser(UserRegisterDto userRegisterDto) {
        Role role=getRoleConsumer();
        checkUserExists(userRegisterDto.getUsername());
        User user=saveUserFromUserRegisterDto(userRegisterDto);
        saveUserRoleFromUserRegisterDto(role,user);
        UserRegistrationResponseDto responseDto=userRegistrationMapper.toUserRegistrationResponseDto(user);
        return responseDto;
    }
    public Role getRoleConsumer(){
        Role role=roleRepository.findByRoleName(RoleEnum.CONSUMER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return role;
    }
    public Boolean checkUserExists(String username){
        User userExist=userRepository.findByUserName(username);
        if(userExist!=null){
            throw new DevSharingException(ExceptionEnum.USERNAME_ALREADY_EXISTED,new Object[]{username});
        }
        return true;
    }
    public User saveUserFromUserRegisterDto(UserRegisterDto userRegisterDto){
        User user=User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .userName(userRegisterDto.getUsername())
                .createAt(Instant.now())
                .updateAt(Instant.now())
                .createBy(0)
                .updateBy(0)
                .build();
        userRepository.save(user);
        return user;
    }
    public void saveUserRoleFromUserRegisterDto(Role role,User user){
        UserRole userRole=new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setCreateAt(Instant.now());
        userRole.setUpdateAt(Instant.now());
        userRole.setCreateBy(0);
        userRole.setUpdateBy(0);
        userRoleRepository.save(userRole);
    }
}
