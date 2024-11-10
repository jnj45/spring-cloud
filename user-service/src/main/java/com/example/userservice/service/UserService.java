package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <pre>
 * << 개정이력 >>
 * - 2024-11-10 [ecbank-lyj] 최초 생성
 * </pre>
 *
 * @author ecbank-lyj
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("user not found. id=%s", userId));
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<ResponseOrder> orderList = new ArrayList<>();
        userDto.setOrders(orderList);

        return userDto;
    }

    public List<UserDto> getUserByAll() {
        Iterable<UserEntity> userEntities = userRepository.findAll();

        List<UserDto> userList = new ArrayList<>();
        userEntities.forEach(v -> {
            userList.add(new ModelMapper().map(v, UserDto.class));
        });

        return userList;
    }
}
