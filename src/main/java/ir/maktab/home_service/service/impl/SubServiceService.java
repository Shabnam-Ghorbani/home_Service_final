package ir.maktab.home_service.service.impl;


import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.SubService;
import ir.maktab.home_service.data.model.repository.SubServiceRepository;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.service.interf.SubInter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubServiceService implements SubInter {
    private final SubServiceRepository subServiceRepository;
    private final BaseServiceService baseServiceService;

    @Override
    public SubService save(SubService subService) {
        baseServiceService.findByName(subService.getBaseService().getName());
        Optional<SubService> foundedSubService = subServiceRepository.findByName(subService.getName());
        if (foundedSubService.isPresent()) {
            throw new EntityIsExistException("this subService exist!");
        }
            return subServiceRepository.save(subService);
    }

    @Override
    public SubService update(SubService subService) {
        return subServiceRepository.save(subService);
    }

    @Override
    public SubService findByName(String name) {
        Optional<SubService> subService = subServiceRepository.findByName(name);
        return subService.orElseThrow(() -> new EntityNotExistException("this subService not exist!"));
    }

    @Override
    public SubService findById(Integer id) {
        Optional<SubService> subService = subServiceRepository.findById(id);
        return subService.orElseThrow(() -> new EntityNotExistException("this subService not exist!"));
    }

    @Override
    public List<SubService> findAll() {
        List<SubService> subServices = new ArrayList<>();
        Iterable<SubService> subServiceIterable = subServiceRepository.findAll();
        subServiceIterable.forEach(subServices::add);
        return subServices;
    }

    @Override
    //ToDo اضافه کردن متخصص به زیر خدمت
    public SubService addExpertToSubService(Expert expert, SubService subService) {
        subService.getExperts().add(expert);
        return update(subService);
    }

    @Override
    //ToDo حذف متخصص از زیر خدمت
    public SubService removeExpertFromSubService(Expert expert, SubService subService) {
        subService.getExperts().remove(expert);
        return update(subService);
    }

    @Transactional
    @Override
    //ToDo تغییر قیمت  و توضیح
    public int editBasePriceAndDescription(Integer id, Long basePrice, String description) {
        return subServiceRepository.editBasePriceAndDescription(id, basePrice, description);

    }
    @Override
    @Transactional
    public int deleteSubServiceById(Integer id) {

        return subServiceRepository.deleteSubServiceById(id);
    }

}