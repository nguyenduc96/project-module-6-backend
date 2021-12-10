package com.diosa.service.label;

import com.diosa.model.label.Label;
import com.diosa.repository.ILabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService implements ILabelService{

    @Autowired
    private ILabelRepository labelRepository;

    @Override
    public Iterable<Label> findAll() {
        return labelRepository.findAll();
    }

    @Override
    public Optional<Label> findById(Long id) {
        return labelRepository.findById(id);
    }

    @Override
    public Label save(Label label) {
        return labelRepository.save(label);
    }

    @Override
    public void remove(Long id) {
        labelRepository.deleteById(id);
    }

    @Override
    public List<Label> findAllByBoardId(Long id) {
        return labelRepository.findAllByBoardId(id);
    }

    @Override
    public void deleteAllByBoardId(Long id) {
        labelRepository.deleteAllByBoardId(id);
    }

    @Override
    public List<Label> findAllByTaskId(Long id) {
        return labelRepository.findAllByTaskId(id);
    }
}
