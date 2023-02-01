package ir.maktab.home_service.service;


import ir.maktab.home_service.data.model.entity.User;
import ir.maktab.home_service.data.model.repository.UserRepository;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.exception.InCorrectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        Optional<User> foundedUser = userRepository.findByEmailAddress(user.getEmailAddress());
        if (foundedUser.isPresent()) {
            throw new EntityIsExistException("this emailAddress exist!");
        } else {
            return userRepository.save(user);
        }
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User findByEmailAddress(String emailAddress) {
        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotExistException("emailAddress not exist!");
        }
    }

    public User changePassword(User user, String currentPassword, String newPassword) {
        String password = user.getPassword();
        if (password.equals(currentPassword)) {
            user.setPassword(newPassword);
            System.out.println("your password change successfully.");
            return update(user);
        } else {
            throw new InCorrectException("password is wrong!");
        }
    }
}
