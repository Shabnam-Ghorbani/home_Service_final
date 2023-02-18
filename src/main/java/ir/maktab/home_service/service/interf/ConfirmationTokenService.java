package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.token.ConfirmationToken;

import java.util.List;
import java.util.Optional;

public interface ConfirmationTokenService {

    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);

    void saveOrUpdate(ConfirmationToken confirmationToken);

    void delete(ConfirmationToken confirmationToken);

    Optional<ConfirmationToken> findById(Integer id);

    List<ConfirmationToken> findAll();

    void saveConfirmationToken(ConfirmationToken token);

    Optional<ConfirmationToken> getToken(String token);

//    int setConfirmedAt(String token);


}
