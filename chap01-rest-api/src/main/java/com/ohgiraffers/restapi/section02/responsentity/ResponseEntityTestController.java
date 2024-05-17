package com.ohgiraffers.restapi.section02.responsentity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/entity")
public class ResponseEntityTestController {
    private List<UserDTO> user;

    public ResponseEntityTestController() {

        user = new ArrayList<>();
        user.add(new UserDTO(1, "user1", "1234", "김철수", new Date()));
        user.add(new UserDTO(2, "user2", "1234", "이철수", new Date()));
        user.add(new UserDTO(3, "user3", "1234", "박철수", new Date()));
        user.add(new UserDTO(4, "user4", "1234", "최철수", new Date()));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType("application", "json", StandardCharsets.UTF_8)
        );
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", user);
        ResponseMessage responseMessage
                = new ResponseMessage(200, "조회 성공", responseMap);
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType("application", "json", StandardCharsets.UTF_8)
        );
        UserDTO findUser = user.stream().filter(user -> user.getNo() == userNo).findFirst().orElse(null);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", findUser);
        ResponseMessage responseMessage
                = new ResponseMessage(200, "조회 성공", responseMap);
        return ResponseEntity.ok().headers(headers).body(responseMessage);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO userDTO) {
        int lastUserNo = user.get(user.size() - 1).getNo();
        userDTO.setNo(lastUserNo + 1);
        userDTO.setEnrolledDate(new Date());
        user.add(userDTO);

        return ResponseEntity.created(URI.create("/entity/users/" + user.get(user.size() - 1).getNo())).build();
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<Void> modify(@PathVariable int userNo, @RequestBody UserDTO userDTO) {
        UserDTO findUser = user.stream().filter(user -> user.getNo() == userNo).findFirst().orElse(null);
        findUser.setId(userDTO.getId());
        findUser.setPassword(userDTO.getPassword());
        findUser.setName(userDTO.getName());
        return ResponseEntity.created(URI.create("/entity/users/" + user.get(user.size() - 1).getNo())).build();
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userNo) {
        UserDTO findUser = user.stream().filter(user -> user.getNo() == userNo).findFirst().orElse(null);
        user.remove(findUser);
        return ResponseEntity.noContent().build();
    }

}

