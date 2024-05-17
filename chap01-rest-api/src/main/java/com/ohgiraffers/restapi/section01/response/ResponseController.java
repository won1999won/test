package com.ohgiraffers.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/response")
public class ResponseController {
    //    문자열 응답
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    //기본 자료형 응답
    @GetMapping("/random")
    public int getRandonNumber() {
        return (int) (Math.random() * 10) + 1;
    }

    //    오브젝트 응답
    @GetMapping("/message")
    public Message getMessage() {
        return new Message(200, "Hello World!");
    }

    //    오브젝트 응답
    @GetMapping("list")
    public List<String> getList() {
        return List.of(new String[]{"Hello", "World"});
    }

    //    맵 응답
    @GetMapping("/map")
    public Map<Integer, String> getMap() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(200, "Hello World1"));
        messageList.add(new Message(404, "Error World"));
        messageList.add(new Message(500, "SHIT"));
        return messageList.stream().collect(Collectors.toMap(Message::getHttpStatusCode, Message::getMessage));
    }
//    파일 응답
    @GetMapping(value = "/image",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        return getClass().getResourceAsStream("/images/spring.png").readAllBytes();
    }
//    ResponseEntity 응답
    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity() {
        Message message = new Message(200, "Hello World!");
        return ResponseEntity.ok(message);
    }
}

