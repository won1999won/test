package com.ohigraffers.practice.post.controller;

import com.ohigraffers.practice.post.dto.request.PostCreateRequest;
import com.ohigraffers.practice.post.dto.request.PostUpdateRequest;
import com.ohigraffers.practice.post.dto.response.PostResponse;
import com.ohigraffers.practice.post.dto.response.ResponseMessage;
import com.ohigraffers.practice.post.model.Post;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/* Swagger 문서화 시 Grouping 작성 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private List<Post> posts;

    public PostController() {
        posts = new ArrayList<>();
        posts.add(new Post(1L, "제목1", "내용1", "홍길동"));
        posts.add(new Post(2L, "제목2", "내용2", "유관순"));
        posts.add(new Post(3L, "제목3", "내용3", "신사임당"));
        posts.add(new Post(4L, "제목4", "내용4", "이순신"));
        posts.add(new Post(5L, "제목5", "내용5", "장보고"));
    }

    /* 1. 전체 포스트 조회 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "전체 포스트 조회", description = "목록")
    @GetMapping("/allPost")
    public ResponseEntity<ResponseMessage> findAllPosts() {
        HttpHeaders headers = new HttpHeaders();
        /* 응답 데이터 설정 */
        List<PostResponse> postResponses = posts.stream().map(PostResponse::from).toList();
        /* Post 타입은 PostResponse 타입으로 변환해서 반환 */
        List<EntityModel<PostResponse>> postsRel = postResponses.stream()
                .map(postResponse -> EntityModel.of(postResponse,
                        linkTo(methodOn(PostController.class).findAllPosts()).withSelfRel()))
                .collect(Collectors.toList());

        /* hateoas 적용 */
        Map<String, Object> results = new HashMap<>();
        results.put("posts", postsRel);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "success", results);

        /* ResponseEntity 반환 */
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

    /* 2. 특정 코드로 포스트 조회 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */

    @Operation(summary = "특정 코드로 포스트 조회", description = "목록")
    @GetMapping("/{postCode}")
    public ResponseEntity<ResponseMessage> findPostByCode(@PathVariable Long postCode/* 필요 매개변수 선언 */) {
        HttpHeaders headers = new HttpHeaders();

        /* 응답 데이터 설정 */
        Optional<PostResponse> optionalPostResponse = posts.stream()
                .filter(post -> post.getCode() == postCode)
                .map(PostResponse::from)
                .findFirst();

        PostResponse postResponse = optionalPostResponse.orElse(null);
        /* Post 타입은 PostResponse 타입으로 변환해서 반환 */
        List<EntityModel<PostResponse>> postsRel = new ArrayList<>();
        postsRel.add(EntityModel.of(postResponse,
                linkTo(methodOn(PostController.class).findPostByCode(postResponse.getCode())).withSelfRel())
        );

        /* hateoas 적용 */
        Map<String, Object> results = new HashMap<>();
        results.put("posts", postsRel);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "success", results);
        /* ResponseEntity 반환 */
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    /* 3. 신규 포스트 등록 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "신규 포스트 등록", description = "목록")
    @PutMapping("/regist")
    public ResponseEntity<Void> registPost(@RequestBody @Valid PostCreateRequest newPost/* 필요 매개변수 선언, 유효성 검사 */) {
        Post post = new Post(
                (long) posts.size() + 1,
                newPost.getTitle(),
                newPost.getContent(),
                newPost.getWriter()
        );

        // 리스트에 추가
        posts.add(post);

        // ResponseEntity 반환
        return ResponseEntity.created(URI.create("/posts/" + post.getCode())).build();
    }

    /* 4. 포스트 제목과 내용 수정 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "포스트 제목과 내용 수정", description = "목록")
    @PutMapping("/{postCode}")
    public ResponseEntity<Void> modifyPost(@PathVariable Long postCode, @RequestBody @Valid PostUpdateRequest updateRequest/* 필요 매개변수 선언, 유효성 검사 */) {
        /* 리스트에서 찾아서 수정 */
        Post post = posts.stream().filter(post1 -> post1.getCode().equals(postCode)).findFirst().orElse(null);

        /* 수정 메소드 활용 */
        post.modifyTitleAndContent(updateRequest.getTitle(), updateRequest.getContent());

        /* ResponseEntity 반환 */
        return ResponseEntity.created(URI.create("/posts/" + postCode)).build();
    }

    /* 5. 포스트 삭제 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "포스트 삭제", description = "목록")
    @DeleteMapping("/{postCode}")
    public ResponseEntity<Void> removeUser(@PathVariable Long postCode/* 필요 매개변수 선언 */) {
        Post post = posts.stream().filter(post1 -> post1.getCode() == postCode).findAny().orElse(null);
        posts.remove(post);
        /* 리스트에서 찾아서 삭제 */
        /* ResponseEntity 반환 */
        return ResponseEntity.noContent().build();
    }

}
