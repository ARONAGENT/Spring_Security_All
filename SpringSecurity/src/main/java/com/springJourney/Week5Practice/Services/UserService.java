package com.springJourney.Week5Practice.Services;

import com.springJourney.Week5Practice.DTOs.SignUpDTO;
import com.springJourney.Week5Practice.DTOs.UserDTO;
import com.springJourney.Week5Practice.Entities.UserEntity;
import com.springJourney.Week5Practice.Repositories.UserRepository;
import com.springJourney.Week5Practice.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new BadCredentialsException("Not found By email "+username));
    }


    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElse(null);
    }
    public UserEntity findById(Long userid){
        return userRepository.findById(userid)
                .orElseThrow(()->new ResourceNotFoundException("Not found By Id "+userid));
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> userEntity=userRepository.findByEmail(signUpDTO.getEmail());
        if(userEntity.isPresent()){
            throw new BadCredentialsException("User already exits with email"+signUpDTO.getEmail());
        }
        UserEntity createdUser =modelMapper.map(signUpDTO,UserEntity.class);
        createdUser.setPassword(passwordEncoder.encode(createdUser.getPassword()));
        userRepository.save(createdUser);
        return modelMapper.map(createdUser,UserDTO.class);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
