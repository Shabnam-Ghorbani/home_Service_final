package ir.maktab.home_service.data.model.entity;

import ir.maktab.home_service.data.model.enamiration.Role;
import ir.maktab.home_service.data.model.enamiration.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", userStatus=" + userStatus +
                ", registrationDate=" + registrationDate +
                ", credit=" + credit +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(emailAddress, user.emailAddress) && Objects.equals(password, user.password) && userStatus == user.userStatus && Objects.equals(registrationDate, user.registrationDate) && Objects.equals(credit, user.credit) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, emailAddress, password, userStatus, registrationDate, credit, role);
    }
}
