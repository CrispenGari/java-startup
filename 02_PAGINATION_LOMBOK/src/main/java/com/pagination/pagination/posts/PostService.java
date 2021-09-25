package com.pagination.pagination.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService implements PostServiceInterface{

    private final PostRepository repository;
    @Override
    public Post createPost(Post post) {
        post.setStatus(Status.UNLIKED);
        return this.repository.save(post);
    }

    @Override
    public Post react(Long id) {
        Post post = this.repository.findById(id).orElseThrow(()-> new CustomException("could not find the post of that id"));
        post.setStatus(post.getStatus() == Status.LIKED ? Status.UNLIKED: Status.LIKED);
        return post;
    }

    @Override
    public Collection<Post> getPosts(int page, int limit) {
        return this.repository.findAll(PageRequest.of(page, limit)).toList();
    }

    @Override
    public Post updatePost(Post post) {
        post.setStatus(Status.LIKED);
        return this.repository.save(post);
    }

    @Override
    public Boolean deletePost(Long id) {
       this.repository.deleteById(id);
       return true;
    }

    @Override
    public Post getPost(Long id) {
        return this.repository.findById(id).orElseThrow(()->new CustomException("the post with that id was found."));
    }
}
