package com.ohgiraffers.restapi.section05.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Tag(name = "user 관련 api")
@RestController
@RequestMapping("/swagger")
public class SwaggerTestController {

    private List<UserDTO> user;

    public SwaggerTestController() {

        user = new ArrayList<>();
        user.add(new UserDTO(1, "user1", "1234", "김철수", new Date()));
        user.add(new UserDTO(2, "user2", "1234", "이철수", new Date()));
        user.add(new UserDTO(3, "user3", "1234", "박철수", new Date()));
        user.add(new UserDTO(4, "user4", "1234", "최철수", new Date()));
    }

    @Operation(summary = "전체 회원 조회", description = "목록")
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

    @Operation(summary = "회원 조회", description = "회원번호")
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

    @Operation(summary = "원 수정", description = "회원정보")
    @PutMapping("/users/{userNo}")
    public ResponseEntity<Void> modify(@PathVariable int userNo, @RequestBody UserDTO userDTO) {
        UserDTO findUser = user.stream().filter(user -> user.getNo() == userNo).findFirst().orElse(null);
        findUser.setId(userDTO.getId());
        findUser.setPassword(userDTO.getPassword());
        findUser.setName(userDTO.getName());
        return ResponseEntity.created(URI.create("/entity/users/" + user.get(user.size() - 1).getNo())).build();
    }

    @Operation(summary = "회원 삭제", description = "회원번호")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "삭제 실패")
    })
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userNo) {
        UserDTO findUser = user.stream().filter(user -> user.getNo() == userNo).findFirst().orElse(null);
        user.remove(findUser);
        return ResponseEntity.noContent().build();
    }

}
