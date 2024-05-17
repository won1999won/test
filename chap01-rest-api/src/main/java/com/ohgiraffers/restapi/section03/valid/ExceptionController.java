package com.ohgiraffers.restapi.section03.valid;

import com.ohgiraffers.restapi.section01.response.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        String code = "ERROR_CODE_000000";
        String description = "User Not Found";
        String detail = ex.getMessage();
        return new ResponseEntity<>(new ErrorResponse(code, description, detail), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(
            MethodArgumentNotValidException e
    ) {
        String code = "";
        String description = "";
        String detail = "";
        /* 에러가 있다면 */
        if (e.getBindingResult().hasErrors()) {
            detail
                    = e.getBindingResult().getFieldError().getDefaultMessage();
            String bindResultCode
                    = e.getBindingResult().getFieldError().getCode();
            System.out.println(bindResultCode);
            switch (bindResultCode) {
                case "NotNull":
                    code = "ERROR_CODE_00001";
                    description = "필수 값이 누락되었습니다.";
                    break;
                case "NotBlank":
                    code = "ERROR_CODE_00002";
                    description = "필수 값이 공백으로 처리되었습니다.";
                    break;
                case "Size":
                    code = "ERROR_CODE_00003";
                    description = "알맞은 크기의 값이 입력되지 않았습니다.";
                    break;
            }
        }
        ErrorResponse errorResponse
                = new ErrorResponse(code, description, detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
