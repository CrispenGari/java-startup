package com.pagination.pagination.posts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    public final PostService service;

    @GetMapping(path = "/all")
    public ResponseEntity<Response> getPosts(
            @RequestParam(name = "page", required = true) int page,
            @RequestParam(name = "limit", required = true) int limit
    ) {
        System.out.println("Getting all the posts");
        Collection<Post> posts = service.getPosts(page, limit);
        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .limit(limit)
                        .page(page)
                        .posts(Map.of("posts", posts))
                        .error(null)
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Response> createPost(@RequestBody Post post){
        System.out.println("hitted on post");
        return ResponseEntity.ok(
                Response.builder()
                        .posts(Map.of("posts", this.service.createPost(post)))
                        .error(null)
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Response> deletePost(@PathVariable("id") Long id) {
        boolean postFound = true;
        ResponseError error = new ResponseError("the post of that id does not exists", "id");
        try {
             this.service.getPost(id);
        }catch (CustomException e){
            postFound = false;
        }
        return ResponseEntity.ok(
                Response.builder()
                        .posts(Map.of("posts", this.service.deletePost(id)))
                        .error(postFound? null : error)
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Response> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        boolean postFound = true;
        ResponseError error = new ResponseError("the post of that id does not exists", "id");
        try {
            this.service.getPost(id);
        }catch (CustomException e){
            postFound = false;
        }
        return ResponseEntity.ok(
                Response.builder()
                        .posts(Map.of("posts", this.service.updatePost(post)))
                        .error(postFound? null : error)
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping(path = "/react/{id}")
    public ResponseEntity<Response> reactToPost(@PathVariable("id") Long id) {
        boolean postFound = true;
        ResponseError error = new ResponseError("the post of that id does not exists", "id");
        try {
            this.service.getPost(id);
        }catch (CustomException e){
            postFound = false;
        }
        return ResponseEntity.ok(
                Response.builder()
                        .posts(Map.of("posts", this.service.react(id)))
                        .error(postFound? null : error)
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
