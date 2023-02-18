package ir.maktab.home_service.token;

import ir.maktab.home_service.data.model.entity.BaseEntity;
import ir.maktab.home_service.data.model.entity.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken extends BaseEntity {

    private String confirmationToken;
    @OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "person_id")
    private Person person;

    public boolean isNew() {

        return this.getId() == null;
    }

    public ConfirmationToken(Person person) {
        this.person = person;
    }

    public boolean getConfirmedAt() {
        return false;
    }

    public LocalDateTime getExpiresAt() {

        return null;
    }
}

