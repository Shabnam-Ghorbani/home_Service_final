package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.data.model.entity.BaseService;
import ir.maktab.home_service.data.model.repository.BaseServiceRepository;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.service.interf.BaseInter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseServiceService implements BaseInter {
    private final BaseServiceRepository baseServiceRepository;

    @Override
    public BaseService save(BaseService baseService) {
        Optional<BaseService> foundedBaseService = baseServiceRepository.findByName(baseService.getName());
        if (foundedBaseService.isPresent()) {
            throw new EntityIsExistException("this baseService exist!");
        }
        BaseService savedBaseService = baseServiceRepository.save(baseService);
        return savedBaseService;
    }

    @Override
    @Transactional
    public int deleteBaseServiceById(Integer id) {
        return baseServiceRepository.deleteBaseServiceById(id);
    }

    @Override
    public BaseService findByName(String name) {
        Optional<BaseService> baseService = baseServiceRepository.findByName(name);
        return baseService.orElseThrow(() -> new EntityNotExistException("this baseService not exist!"));
    }
}
