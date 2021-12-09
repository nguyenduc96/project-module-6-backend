package com.diosa.service.assign;

import com.diosa.model.assign.Assign;
import com.diosa.repository.IAssignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignService implements IAssignService {
    @Autowired
    private IAssignRepository assignRepository;

    @Override
    public Iterable<Assign> findAll() {
        return assignRepository.findAll();
    }

    @Override
    public Optional<Assign> findById(Long id) {
        return assignRepository.findById(id);
    }

    @Override
    public Assign save(Assign assign) {
        return assignRepository.save(assign);
    }

    @Override
    public void remove(Long id) {
        assignRepository.deleteById(id);
    }

    @Override
    public void deleteAssign(Long userId, Long taskId) {
        assignRepository.deleteAssign(userId, taskId);
    }
}
