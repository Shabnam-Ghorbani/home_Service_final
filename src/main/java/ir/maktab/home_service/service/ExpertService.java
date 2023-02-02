package ir.maktab.home_service.service;


import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.repository.ExpertRepository;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.exception.InCorrectException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private static final Logger logger = LoggerFactory.getLogger(ExpertService.class);

    private final ExpertRepository expertRepository;

    public Expert save(Expert expert) {
        Optional<Expert> foundedExpert = expertRepository.findByEmailAddress(expert.getEmailAddress());
        if (foundedExpert.isPresent()) {
            throw new EntityIsExistException("this emailAddress exist!");
        } else {
            return expertRepository.save(expert);
        }
    }

    public Expert findByEmailAddress(String emailAddress) {
        Optional<Expert> expert = expertRepository.findByEmailAddress(emailAddress);
        return expert.orElseThrow(() -> new EntityNotExistException("emailAddress not exist!"));
    }

    public Expert updatee(Expert expert) {
        return expertRepository.save(expert);
    }


    public void update(Expert expert) {
        expertRepository.save(expert);
    }


    public void updateScore(Expert expert, Double instructionsScore) {
        Double expertScore = expert.getScore();
        Double newScore = (expertScore + instructionsScore) / 2;
        expert.setScore(newScore);
        update(expert);
    }

    public Expert findById(Integer id) {
        Optional<Expert> expert = expertRepository.findById(id);
        return expert.orElseThrow(() -> new EntityNotExistException("expert not exist!"));
    }

    public Expert changePassword(Expert expert, String currentPassword, String newPassword) {
        String password = expert.getPassword();
        if (password.equals(currentPassword)) {
            expert.setPassword(newPassword);
            logger.info("your password change successfully");
            return updatee(expert);
        } else {
            throw new InCorrectException("password is wrong!");
        }
    }
}

