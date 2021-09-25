package com.pagination.pagination.posts;

import java.util.Collection;

public interface PostServiceInterface {
    Post createPost(Post post);
    Post react(Long id);
    Collection<Post> getPosts(int page, int limit);
    Post updatePost(Post post);
    Boolean deletePost(Long id);
    Post getPost(Long id);
}