package com.ohgiraffers.restapi.section03.valid;

import com.ohgiraffers.restapi.section02.responsentity.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/valid")
public class ValidTestController {
    @GetMapping("/users/{userNo}")
    public ResponseEntity<Void> findUserByNo() throws UserNotFoundException {
        boolean check = true;
        if (check){
            throw new UserNotFoundException("no");
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDTO user) throws UserNotFoundException {

return ResponseEntity.created(URI.create("/valid/users/1")).build();
    }
}
