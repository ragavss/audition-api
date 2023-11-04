package com.audition.web;

import com.audition.common.exception.SystemException;
import com.audition.common.logging.AuditionLogger;
import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import com.audition.service.AuditionService;
import com.audition.web.advice.ExceptionControllerAdvice;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class AuditionController {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Autowired
    AuditionService auditionService;

    @Autowired
    AuditionLogger auditionLogger;

    // TODO Add a query param that allows data filtering. The intent of the filter is at developers discretion.
    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPost> getPosts(@RequestParam(value = "maxIdValue",required = false) final String maxIdValue,@RequestParam(value = "titleMaxLength",required = false) final String titleMaxLength) {

        // TODO Add logic that filters response data based on the query param
        Stream<AuditionPost> auditionPosts = auditionService.getPosts().stream();
        if(StringUtils.isNotEmpty(maxIdValue)){
            if(!StringUtils.isNumeric(maxIdValue)){
                throw new SystemException("Non numeric query param for maxIdValue","Bad Request",HttpStatus.BAD_REQUEST.value());
            }
            auditionPosts= auditionPosts.filter(post -> post.getId() <= Integer.parseInt(maxIdValue));
        }else if(StringUtils.isNotEmpty(titleMaxLength)){
            if(!StringUtils.isNumeric(titleMaxLength)){
                throw new SystemException("Non numeric query param for titleMaxLength","Bad Request",HttpStatus.BAD_REQUEST.value());
            }
            auditionPosts = auditionPosts.filter(post -> post.getTitle().length()<=Integer.parseInt(titleMaxLength));
        }

        return auditionPosts.collect(Collectors.toList());

    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPosts(@PathVariable("id") final String postId) {
        // TODO Add input validation
        if(StringUtils.isEmpty(postId)){
            throw new SystemException("Post id cannot be empty", "Invalid request", HttpStatus.BAD_REQUEST.value());
        }
        return auditionService.getPostById(postId);
    }

    // TODO Add additional methods to return comments for each post. Hint: Check https://jsonplaceholder.typicode.com/
    @RequestMapping(value = "/posts/{id}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionComment> getCommentsForPost(@PathVariable("id") final String postId) {
        if(StringUtils.isEmpty(postId)){
            throw new SystemException("Post id cannot be empty", "Invalid request", HttpStatus.BAD_REQUEST.value());
        }
        return auditionService.getCommentsForPostId(postId);
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionComment> getComments(@RequestParam(value = "postId",required = false) final String postId) {
        if(StringUtils.isEmpty(postId)){
            auditionLogger.info(LOG,"Post id empty");
        }
        return auditionService.getComments(postId);
    }
}
