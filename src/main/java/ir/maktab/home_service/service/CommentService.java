package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.entity.Comment;
import ir.maktab.home_service.data.model.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
