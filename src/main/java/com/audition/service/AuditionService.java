package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditionService {

    @Autowired
    private AuditionIntegrationClient auditionIntegrationClient;


    public List<AuditionPost> getPosts() {
        return auditionIntegrationClient.getPosts();
    }

    public AuditionPost getPostById(final String postId) {
        return auditionIntegrationClient.getPostById(postId);
    }

    public List<AuditionComment> getCommentsForPostId(String postId) {
        return auditionIntegrationClient.getCommentsForPostId(postId);
    }

    public List<AuditionComment> getComments(String postId) {
        return auditionIntegrationClient.getComments(postId);
    }
}
