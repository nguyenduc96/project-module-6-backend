package com.diosa.controller;

import com.diosa.model.comment.Comment;
import com.diosa.model.task.Task;
import com.diosa.model.user.User;
import com.diosa.model.user.UserPrinciple;
import com.diosa.service.comment.CommentService;
import com.diosa.service.comment.ICommentService;
import com.diosa.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserService userService;


    @GetMapping
    public ResponseEntity<Iterable<Comment>> showComment(){
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> AddComment(@RequestBody Comment comment, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = new User();
        user.setId(userPrinciple.getId());
        comment.setDate(convertDateToString(new Date()));
        comment.setUser(user);
        return new ResponseEntity<>(commentService.save(comment),HttpStatus.CREATED);
    }

    private String convertDateToString(Date date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(date);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id){
        Optional<Comment> commentOptional = commentService.findById(id);
        if(!commentOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Comment>> findByTaskId(@PathVariable Long taskId) {
        return new ResponseEntity<>(commentService.findAllByTaskId(taskId), HttpStatus.OK);
    }


}
