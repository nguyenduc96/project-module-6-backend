package com.diosa.controller;

import com.diosa.model.board.BoardResponse;
import com.diosa.model.comment.Comment;
import com.diosa.model.notification.Notification;
import com.diosa.model.status.Status;
import com.diosa.model.status.StatusResponse;
import com.diosa.model.task.Task;
import com.diosa.service.board.IBoardService;
import com.diosa.service.notification.INotificationService;
import com.diosa.service.status.IStatusService;
import com.diosa.service.task.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class WebSocketController {

    @Autowired
    private IBoardService boardService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/task/board/{id}")
    @SendTo("/topic/task/board/{id}")
    public BoardResponse boardSocket(@DestinationVariable Long id, BoardResponse boardResponse) {
        boardService.saveBoardResponse(boardResponse, boardResponse.getProject());
        Set<StatusResponse> statuses = boardResponse.getStatuses();
        for( StatusResponse statusResponse : statuses) {
            statusService.saveStatusResponse(statusResponse, boardResponse.getId());
            for(Task task : statusResponse.getTasks()) {
                taskService.save(task);
            }
         }
        return boardService.findBoardById(id);
    }

    @MessageMapping("/notification/board/{id}")
    public void notificationSocket(@DestinationVariable Long id, Notification notification) {
        notification.setStatus(false);
        notificationService.save(notification);
        simpMessagingTemplate.convertAndSend("/topic/notification/board/user/"+id, notificationService.findAllByReceiverIdOrderByIdDesc(id));
    }

    @MessageMapping("/comment/task/{id}")
    @SendTo("/topic/comment/task/{id}")
    public ResponseEntity<List<Comment>> commentSocket(@DestinationVariable Long id) {
        return null;
    }
}
