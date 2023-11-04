package com.audition.integration;

import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AuditionIntegrationClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuditionIntegrationClient auditionIntegrationClient;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(auditionIntegrationClient, "postsEndpoint", "/posts");
        ReflectionTestUtils.setField(auditionIntegrationClient, "commentsEndpoint", "/comments");
        ReflectionTestUtils.setField(auditionIntegrationClient, "baseUrl", "https://jsonplaceholder.typicode.com");
    }

    @Test
    void getPosts() {

        AuditionPost auditionPost = new AuditionPost();
        AuditionPost[] auditionPosts = new AuditionPost[1];
        auditionPosts[0] = auditionPost;
        ResponseEntity<AuditionPost[]> response = new ResponseEntity<>(auditionPosts, HttpStatusCode.valueOf(200));
        Mockito.when(restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts",AuditionPost[].class)).thenReturn(response);
        List<AuditionPost> posts = auditionIntegrationClient.getPosts();
        Assertions.assertEquals(auditionPost,posts.get(0));
    }

    @Test
    void getPostById() {
        AuditionPost auditionPost = new AuditionPost();
        ResponseEntity<AuditionPost> response = new ResponseEntity<>(auditionPost, HttpStatusCode.valueOf(200));
        Mockito.when(restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/1",AuditionPost.class)).thenReturn(response);
        AuditionPost post = auditionIntegrationClient.getPostById("1");
        Assertions.assertEquals(auditionPost,post);
    }

    @Test
    void getCommentsForPostId() {
        AuditionComment auditionComment = new AuditionComment();
        AuditionComment[] auditionComments = new AuditionComment[1];
        auditionComments[0] = auditionComment;
        ResponseEntity<AuditionComment[]> response = new ResponseEntity<>(auditionComments, HttpStatusCode.valueOf(200));
        Mockito.when(restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/1/comments",AuditionComment[].class)).thenReturn(response);
        List<AuditionComment> comments = auditionIntegrationClient.getCommentsForPostId("1");
        Assertions.assertEquals(auditionComment,comments.get(0));
    }

    @Test
    void getComments_allComments() {
        AuditionComment auditionComment = new AuditionComment();
        AuditionComment[] auditionComments = new AuditionComment[1];
        auditionComments[0] = auditionComment;
        ResponseEntity<AuditionComment[]> response = new ResponseEntity<>(auditionComments, HttpStatusCode.valueOf(200));
        Mockito.when(restTemplate.getForEntity("https://jsonplaceholder.typicode.com/comments",AuditionComment[].class)).thenReturn(response);
        List<AuditionComment> comments = auditionIntegrationClient.getComments(null);
        Assertions.assertEquals(auditionComment,comments.get(0));
    }

    @Test
    void getComments_ForParticularPost() {
        AuditionComment auditionComment = new AuditionComment();
        AuditionComment[] auditionComments = new AuditionComment[1];
        auditionComments[0] = auditionComment;
        ResponseEntity<AuditionComment[]> response = new ResponseEntity<>(auditionComments, HttpStatusCode.valueOf(200));
        Mockito.when(restTemplate.getForEntity("https://jsonplaceholder.typicode.com/comments?postId=1",AuditionComment[].class)).thenReturn(response);
        List<AuditionComment> comments = auditionIntegrationClient.getComments("1");
        Assertions.assertEquals(auditionComment,comments.get(0));
    }
}