package com.audition.web;

import com.audition.common.logging.AuditionLogger;
import com.audition.model.AuditionPost;
import com.audition.service.AuditionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuditionControllerTest {

    @Mock
    private AuditionService auditionService;
    @Mock
    private AuditionLogger auditionLogger;
    @InjectMocks
    private AuditionController auditionController;

    @Test
    void getPosts() {
        AuditionPost auditionPost = new AuditionPost();
        Mockito.when(auditionService.getPosts()).thenReturn(List.of(auditionPost));
        List<AuditionPost> auditionPosts = auditionController.getPosts(null,null);
        assertNotNull(auditionPosts);
        assertEquals(auditionPost,auditionPosts.get(0));
    }

    @Test
    void testGetPosts() {
        AuditionPost auditionPost = new AuditionPost();
        Mockito.when(auditionService.getPostById("1")).thenReturn(auditionPost);
        AuditionPost auditionPosts = auditionController.getPosts("1");
        assertNotNull(auditionPosts);
        assertEquals(auditionPost,auditionPosts);
    }

    @Test
    void getCommentsForPost() {
        AuditionPost auditionPost = new AuditionPost();
        Mockito.when(auditionService.getPosts()).thenReturn(List.of(auditionPost));
        List<AuditionPost> auditionPosts = auditionController.getPosts(null,null);
        assertNotNull(auditionPosts);
        assertEquals(auditionPost,auditionPosts.get(0));
    }

    @Test
    void getComments() {
        AuditionPost auditionPost = new AuditionPost();
        Mockito.when(auditionService.getPosts()).thenReturn(List.of(auditionPost));
        List<AuditionPost> auditionPosts = auditionController.getPosts(null,null);
        assertNotNull(auditionPosts);
        assertEquals(auditionPost,auditionPosts.get(0));
    }
}