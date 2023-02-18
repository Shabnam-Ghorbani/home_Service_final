package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.token.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmRepository extends JpaRepository<ConfirmationToken, Integer> {

    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);
}

