package com.springJourney.Week5Practice.Controllers;

import com.springJourney.Week5Practice.DTOs.PostDTO;
import com.springJourney.Week5Practice.Services.PostImp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostImp postImp;

    @GetMapping
    public List<PostDTO> allPost(){
        return postImp.findAllPost();
    }

    @PostMapping("/add")
    public PostDTO addPost(@RequestBody PostDTO postDTO){
        return postImp.addPost(postDTO);
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable  int postId){
        return postImp.getPostById(postId);
    }

    @PutMapping("/update/{postId}")
    public PostDTO updatePostById(@RequestBody PostDTO postDTO,@PathVariable  int postId){
        return postImp.updatePostById(postDTO,postId);
    }

    @DeleteMapping("/delete/{postId}")
    public PostDTO deletePostById(@PathVariable  int postId){
        return postImp.deletePostById(postId);
    }

}
