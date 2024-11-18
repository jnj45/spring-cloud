package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
@RestController
@RequestMapping("")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final Environment environment;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in User Service."
                + ", port(local.server.port) = "    + environment.getProperty("local.server.port"))
                + ", port(server.port) = "          + environment.getProperty("server.port")
                + ", with token secret = "          + environment.getProperty("token.secret")
                + ", with token expiration = "      + environment.getProperty("token.expiration");
    }

    @GetMapping("/welcome")
    public String welcome() {
//        return environment.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto createdUserDto = userService.createUser(userDto);

        ResponseUser responseUser = modelMapper.map(createdUserDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        List<UserDto> userDtoList = userService.getUserByAll();
        List<ResponseUser> responseUserList = new ArrayList<>();
        for (UserDto userDto : userDtoList) {
            responseUserList.add(new ModelMapper().map(userDto, ResponseUser.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseUserList);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);
        responseUser.setOrders(userDto.getOrders());

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

}
