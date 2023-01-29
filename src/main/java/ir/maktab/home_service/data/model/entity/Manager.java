package ir.maktab.home_service.data.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends BaseEntity {

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String username;

    private String password;
}
