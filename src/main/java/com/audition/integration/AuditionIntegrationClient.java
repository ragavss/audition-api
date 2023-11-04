package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class AuditionIntegrationClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${jsonplaceholder.endpoints.base}")
    private String baseUrl;

    @Value("${jsonplaceholder.endpoints.posts}")
    private String postsEndpoint;

    @Value("${jsonplaceholder.endpoints.comments}")
    private String commentsEndpoint;

    public List<AuditionPost> getPosts() {
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        ResponseEntity<AuditionPost[]> response = restTemplate.getForEntity(baseUrl + postsEndpoint, AuditionPost[].class);
        try {
            return response.getBody()!=null ?Arrays.asList(response.getBody()): Collections.emptyList();
        } catch (final HttpClientErrorException e) {
                throw new SystemException("Unknown Error message",e.getStatusCode().value(),e);
        }
    }

    public AuditionPost getPostById(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        ResponseEntity<AuditionPost> response = restTemplate.getForEntity(baseUrl+postsEndpoint+"/"+id, AuditionPost.class);

        try {
            return response.getBody();
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                    404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException("Unknown Error message",e.getStatusCode().value(),e);
            }
        }
    }



    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.
    public List<AuditionComment> getCommentsForPostId(String postId) {
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        ResponseEntity<AuditionComment[]> response = restTemplate.getForEntity(baseUrl + postsEndpoint +"/"+postId+commentsEndpoint, AuditionComment[].class);
        try {
            return response.getBody()!=null ?Arrays.asList(response.getBody()): Collections.emptyList();
        } catch (final HttpClientErrorException e) {
            throw new SystemException("Unknown Error message",e.getStatusCode().value(),e);
        }
    }

    // TODO write a method. GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.
    public List<AuditionComment> getComments(String postId) {
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        String uri = baseUrl + commentsEndpoint;
        if(StringUtils.isNotEmpty(postId)){
            uri = baseUrl + commentsEndpoint + "?postId=" + postId;
        }

        ResponseEntity<AuditionComment[]> response = restTemplate.getForEntity(uri, AuditionComment[].class);
        try {
            return response.getBody()!=null ?Arrays.asList(response.getBody()): Collections.emptyList();
        } catch (final HttpClientErrorException e) {
            throw new SystemException("Unknown Error message",e.getStatusCode().value(),e);
        }
    }
}
