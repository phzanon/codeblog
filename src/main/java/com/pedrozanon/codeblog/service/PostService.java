package com.pedrozanon.codeblog.service;

import com.pedrozanon.codeblog.model.Post;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    Post findById(long id);

    Post save(Post post);
}
