package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.data.model.entity.BaseService;

public interface BaseInter {

    BaseService save(BaseService baseService);

    int deleteBaseServiceById(Integer id);

    BaseService findByName(String name);
}
