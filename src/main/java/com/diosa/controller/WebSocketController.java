package com.diosa.controller;

import com.diosa.model.board.BoardResponse;
import com.diosa.model.comment.Comment;
import com.diosa.model.notification.Notification;
import com.diosa.service.board.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class WebSocketController {

    @Autowired
    private IBoardService boardService;

    @MessageMapping("/board/{id}")
    @SendTo("/topic/board/{id}")
    public BoardResponse boardSocket(@DestinationVariable Long id) {
        BoardResponse boardResponse = boardService.findBoardById(id);
        return boardService.findBoardById(id);
    }

    @MessageMapping("/notification/board/{id}")
    @SendTo("/topic/notification/board/{id}")
    public ResponseEntity<List<Notification>> notificationSocket(@DestinationVariable Long id, Notification notification) {
        return null;
    }

    @MessageMapping("/comment/task/{id}")
    @SendTo("/topic/comment/task/{id}")
    public ResponseEntity<List<Comment>> commentSocket(@DestinationVariable Long id) {
        return null;
    }
}
