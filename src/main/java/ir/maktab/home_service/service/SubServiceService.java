package ir.maktab.home_service.service;


import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.SubService;
import ir.maktab.home_service.data.model.repository.SubServiceRepository;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubServiceService {
    private final SubServiceRepository subServiceRepository;
    private final BaseServiceService baseServiceService;

    public SubService save(SubService subService) {
        baseServiceService.findByName(subService.getBaseService().getName());
        Optional<SubService> foundedSubService = subServiceRepository.findByName(subService.getName());
        if (foundedSubService.isPresent()) {
            throw new EntityIsExistException("this subService exist!");
        } else {
            return subServiceRepository.save(subService);
        }
    }

    public SubService update(SubService subService) {
        return subServiceRepository.save(subService);
    }

    public SubService findByName(String name) {
        Optional<SubService> subService = subServiceRepository.findByName(name);
        return subService.orElseThrow(() -> new EntityNotExistException("this subService not exist!"));
    }

    public SubService findById(Integer id) {
        Optional<SubService> subService = subServiceRepository.findById(id);
        return subService.orElseThrow(() -> new EntityNotExistException("this subService not exist!"));
    }

    public List<SubService> findAll() {
        List<SubService> subServices = new ArrayList<>();
        Iterable<SubService> subServiceIterable = subServiceRepository.findAll();
        subServiceIterable.forEach(subServices::add);
        return subServices;
    }

    public SubService addExpertToSubService(Expert expert, SubService subService) {
        subService.getExperts().add(expert);
        return update(subService);
    }

    public SubService removeExpertFromSubService(Expert expert, SubService subService) {
        subService.getExperts().remove(expert);
        return update(subService);
    }

}
