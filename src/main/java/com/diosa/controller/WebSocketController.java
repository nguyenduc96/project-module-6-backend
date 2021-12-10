package com.diosa.controller;

import com.diosa.model.board.BoardResponse;
import com.diosa.model.comment.Comment;
import com.diosa.model.notification.Notification;
import com.diosa.model.status.Status;
import com.diosa.model.status.StatusResponse;
import com.diosa.model.task.Task;
import com.diosa.model.user.User;
import com.diosa.model.user.UserPrinciple;
import com.diosa.service.board.IBoardService;
import com.diosa.service.comment.ICommentService;
import com.diosa.service.notification.INotificationService;
import com.diosa.service.status.IStatusService;
import com.diosa.service.task.ITaskService;
import com.diosa.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private IUserService userService;

    @Autowired
    private ICommentService commentService;

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
        List<User> userList = userService.findAllByBoardId(id);
        for(User user : userList) {
            if(user.getId() != notification.getSender().getId()) {
                Notification newNotification = new Notification();
                newNotification.setSender(notification.getSender());
                newNotification.setAction(notification.getAction());
                newNotification.setDate(convertDateToString(new Date()));
                newNotification.setReceiver(user);
                newNotification.setStatus(false);
                newNotification.setLink(notification.getLink());
                notificationService.save(newNotification);
                simpMessagingTemplate.convertAndSend("/topic/notification/board/user/"+user.getId(), notificationService.findAllByReceiverIdOrderByIdDesc(user.getId()));
            }
        }
    }

    @MessageMapping("/comment/task/{id}")
    @SendTo("/topic/comment/task/{id}")
    public List<Comment> commentSocket(@DestinationVariable Long id, Comment comment) {
        comment.setDate(convertDateToString(new Date()));
        commentService.save(comment);
        return commentService.findAllByTaskId(id);
    }


    private String convertDateToString(Date date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(date);
    }
}
