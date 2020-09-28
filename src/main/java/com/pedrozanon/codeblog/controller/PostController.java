package com.pedrozanon.codeblog.controller;

import com.pedrozanon.codeblog.model.Post;
import com.pedrozanon.codeblog.service.impl.PostServiceImpl;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PostController {

    private Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostServiceImpl postService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView getPosts() {
        ModelAndView mv = new ModelAndView("posts");
        List<Post> posts = postService.findAll();
        mv.addObject("posts", posts);
        return mv;
    }

    @GetMapping(value = "/posts/{id}")
    public ModelAndView getPostsDetails(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("postDetails");
        Post post = postService.findById(id);
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.GET)
    public String getPostForm() {
        return "postForm";
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attr) throws Exception{

        logger.info("Método savePost {}", post);

        if(result.hasErrors()) {
            attr.addFlashAttribute("mensagem", "Inserir campos obrigatorios corretamente");
            return "redirect:/newpost";
        }
        post.setData(LocalDate.now());
        postService.save(post);
        return "redirect:/posts";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String redirectHome() {
        return "redirect:/posts";
    }

    @RequestMapping(value = "/editPost/{id}", method = RequestMethod.GET)
    public ModelAndView editPost(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        ModelAndView mv = new ModelAndView("editPost");
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/editPost/{id}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveEditPost(@Valid Post post, @PathVariable("id") Long id, BindingResult result, RedirectAttributes attr) throws Exception{

        logger.info("Método savePost {}", post);

        if(result.hasErrors()) {
            attr.addFlashAttribute("mensagem", "Inserir campos obrigatorios corretamente");
            return "redirect:/editPost";
        }
        post.setData(LocalDate.now());
        postService.save(post);
        return "redirect:/posts";
    }
}
