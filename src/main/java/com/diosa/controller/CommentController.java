package com.diosa.controller;

import com.diosa.model.comment.Comment;
import com.diosa.service.comment.CommentService;
import com.diosa.service.comment.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;


    @GetMapping
    public ResponseEntity<Iterable<Comment>> showComment(){
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> AddComment(Comment comment) {
        return new ResponseEntity<>(commentService.save(comment),HttpStatus.CREATED);
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

}
