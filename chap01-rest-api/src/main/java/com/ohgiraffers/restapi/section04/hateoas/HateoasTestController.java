package com.ohgiraffers.restapi.section04.hateoas;

import com.ohgiraffers.restapi.section02.responsentity.ResponseMessage;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hateoas")
public class HateoasTestController {
    private List<UserDTO> users;
    public HateoasTestController() {
        users = new ArrayList<>();
        users.add(
                new UserDTO(1, "user01", "pass01", "홍길동", new java.util.Date())
        );
        users.add(
                new UserDTO(2, "user02", "pass02", "유관순", new java.util.Date())
        );
        users.add(
                new UserDTO(3, "user03", "pass03", "이순신", new java.util.Date())
        );
    }


    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {
        HttpHeaders headers = new HttpHeaders();

        //hateoas 설정
        List<EntityModel<UserDTO>> usersWithRel = users.stream().map(
                user -> EntityModel.of(user, linkTo(methodOn(HateoasTestController.class).findUserByNo(user.getNo())).withSelfRel(),
                        linkTo(methodOn(HateoasTestController.class).findAllUsers()).withRel("users")
                )
        ).toList();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", usersWithRel);
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
        UserDTO findUser = users.stream().filter(user -> user.getNo() == userNo).findFirst().orElse(null);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", findUser);
        ResponseMessage responseMessage
                = new ResponseMessage(200, "조회 성공", responseMap);
        return ResponseEntity.ok().headers(headers).body(responseMessage);
    }

}
