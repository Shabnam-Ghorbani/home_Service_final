package ir.maktab.home_service.service;


import ir.maktab.home_service.data.model.entity.Manager;
import ir.maktab.home_service.data.model.repository.ManagerRepository;
import ir.maktab.home_service.exception.EntityNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    public void save(Manager manager) {
        managerRepository.save(manager);
    }

    public Manager findByUsername(String username) {
        Optional<Manager> manager = managerRepository.findByUsername(username);
        return manager.orElseThrow(() -> new EntityNotExistException("username not exist!"));
    }
}
