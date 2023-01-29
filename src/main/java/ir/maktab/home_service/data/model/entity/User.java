package ir.maktab.home_service.data.model.entity;

import ir.maktab.home_service.data.model.enamiration.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.management.relation.Role;
import java.util.Date;
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String firstname;

    private String lastname;

    private String emailAddress;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @CreationTimestamp
    private Date registrationDate;

    private Long credit;

    @Enumerated(EnumType.STRING)
    private Role role;
}
