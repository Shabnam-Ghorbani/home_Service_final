package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.data.model.repository.ConfirmRepository;
import ir.maktab.home_service.service.interf.ConfirmationTokenService;
import ir.maktab.home_service.token.ConfirmationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmRepository confirmationTokenRepository;

    public ConfirmationTokenServiceImpl(ConfirmRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken) {
        try {
            return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void saveOrUpdate(ConfirmationToken confirmationToken) {

        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void delete(ConfirmationToken confirmationToken) {

        confirmationTokenRepository.delete(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> findById(Integer id) {
        try {
            return confirmationTokenRepository.findById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ConfirmationToken> findAll() {
        try {
            return confirmationTokenRepository.findAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void saveConfirmationToken(ConfirmationToken token) {

        confirmationTokenRepository.save(token);
    }


    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByConfirmationToken(token);
    }


//    public int setConfirmedAt(String token) {
//        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
//    }
}
