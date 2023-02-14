package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.data.model.entity.Comment;
import ir.maktab.home_service.data.model.repository.CommentRepository;
import ir.maktab.home_service.service.interf.CommInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService implements CommInter {
    private final CommentRepository commentRepository;

    @Override
    public void save(Comment comment) {

        commentRepository.save(comment);
    }
}
