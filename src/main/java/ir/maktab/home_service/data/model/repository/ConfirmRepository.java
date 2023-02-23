package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.token.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmRepository extends JpaRepository<Confirmation, Integer> {

    Optional<Confirmation> findByConfirmation(String confirmation);
}

