package com.diosa.service.status;

import com.diosa.model.status.Status;
import com.diosa.model.status.StatusResponse;
import com.diosa.repository.IStatusRepository;
import com.diosa.service.task.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatusService implements IStatusService {

    @Autowired
    private IStatusRepository statusRepository;

    @Autowired
    private ITaskService taskService;

    @Override
    public Iterable<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public Optional<Status> findById(Long id) {
        return statusRepository.findById(id);
    }

    @Override
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public void remove(Long id) {
        taskService.deleteAllByStatusId(id);
        statusRepository.deleteById(id);
    }

    @Override
    public List<Status> findAllByBoardIdOrderByPositionAsc(Long id) {
        return statusRepository.findAllByBoardIdOrderByPositionAsc(id);
    }

    @Override
    public List<StatusResponse> findByBoardId(Long id) {
        List<Status> statuses =  statusRepository.findAllByBoardIdOrderByPositionAsc(id);
        List<StatusResponse> statusResponseList = new ArrayList<>();
        for(Status status : statuses) {
            StatusResponse statusResponse = convertToStatusResponse(status);
            statusResponseList.add(statusResponse);
        }
        return statusResponseList;
    }

    @Override
    public List<StatusResponse> findByBoardIdAndTitleTask(Long id, String title) {
        List<Status> statuses =  statusRepository.findAllByBoardIdOrderByPositionAsc(id);
        List<StatusResponse> statusResponseList = new ArrayList<>();
        for(Status status : statuses) {
            StatusResponse statusResponse = convertToStatusResponse(status, title);
            statusResponseList.add(statusResponse);
        }
        return statusResponseList;
    }

    private StatusResponse convertToStatusResponse(Status status, String title) {
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setId(status.getId());
        statusResponse.setPosition(status.getPosition());
        statusResponse.setTitle(status.getTitle());
        statusResponse.setTasks(taskService.findAllByStatusIdAndTitleContainsOrderByPositionAsc(status.getId(), title));
        return statusResponse;
    }

    @Override
    public void deleteAllByBoardId(Long boardId) {
        List<Status> statuses = statusRepository.findAllByBoardId(boardId);
        for (int i = 0; i < statuses.size() ; i++) {
            taskService.deleteAllByStatusId(statuses.get(i).getId());
        }
        statusRepository.deleteAllByBoardId(boardId);

    }

    private StatusResponse convertToStatusResponse(Status status) {
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setId(status.getId());
        statusResponse.setPosition(status.getPosition());
        statusResponse.setTitle(status.getTitle());
        statusResponse.setTasks(taskService.findAllByStatusIdOrderByPositionAsc(status.getId()));
        return statusResponse;
    }
}
