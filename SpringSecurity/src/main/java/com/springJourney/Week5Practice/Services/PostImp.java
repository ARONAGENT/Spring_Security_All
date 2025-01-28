package com.springJourney.Week5Practice.Services;



import com.springJourney.Week5Practice.DTOs.PostDTO;

import java.util.List;

public interface PostImp{

    List<PostDTO> findAllPost();
    PostDTO addPost(PostDTO obj);

    PostDTO getPostById(int postId);

    PostDTO updatePostById(PostDTO postDTO, int postId);

    PostDTO deletePostById(int postId);
}
