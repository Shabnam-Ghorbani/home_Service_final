package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.SubService;

import java.util.List;

public interface SubInter {

    SubService save(SubService subService);

    SubService update(SubService subService);

    SubService findByName(String name);

    SubService findById(Integer id);

    int deleteSubServiceById(Integer id);

    List<SubService> findAll();

    SubService addExpertToSubService(Expert expert, SubService subService);

    SubService removeExpertFromSubService(Expert expert, SubService subService);

    int editBasePriceAndDescription(Integer id, Long basePrice, String description);
}
