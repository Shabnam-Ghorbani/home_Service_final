package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.data.model.repository.ConfirmRepository;
import ir.maktab.home_service.service.interf.ConfirmationService;
import ir.maktab.home_service.token.Confirmation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmRepository confirmationRepository;

    public ConfirmationServiceImpl(ConfirmRepository confirmationRepository) {
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public Optional<Confirmation> findByConfirmation(String confirmation) {
        try {
            return confirmationRepository.findByConfirmation(confirmation);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void saveOrUpdate(Confirmation confirmation) {

        confirmationRepository.save(confirmation);
    }

    @Override
    public void delete(Confirmation confirmation) {

        confirmationRepository.delete(confirmation);
    }

    @Override
    public Optional<Confirmation> findById(Integer id) {
        try {
            return confirmationRepository.findById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Confirmation> findAll() {
        try {
            return confirmationRepository.findAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void saveConfirmation(Confirmation token) {

        confirmationRepository.save(token);
    }


    public Optional<Confirmation> getToken(String token) {
        return confirmationRepository.findByConfirmation(token);
    }
}
