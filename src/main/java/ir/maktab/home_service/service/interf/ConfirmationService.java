package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.token.Confirmation;

import java.util.List;
import java.util.Optional;

public interface ConfirmationService {

    Optional<Confirmation> findByConfirmation(String confirmation);

    void saveOrUpdate(Confirmation confirmation);

    void delete(Confirmation confirmation);

    Optional<Confirmation> findById(Integer id);

    List<Confirmation> findAll();

    void saveConfirmation(Confirmation token);

    Optional<Confirmation> getToken(String token);



}
