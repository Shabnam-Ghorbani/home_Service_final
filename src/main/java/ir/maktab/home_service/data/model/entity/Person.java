package ir.maktab.home_service.data.model.entity;

import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.enamiration.Role;
import ir.maktab.home_service.data.model.enamiration.RoleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String firstname;

    private String lastname;
    @Column(unique = true)
    private String emailAddress;

    private String password;


    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;

    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    private PersonStatus personStatus;

    @CreationTimestamp
    private Date registrationDate;
    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", roleStatus=" + roleStatus +
                ", isActive=" + isActive +
                ", personStatus=" + personStatus +
                ", registrationDate=" + registrationDate +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstname, person.firstname) && Objects.equals(lastname, person.lastname) && Objects.equals(emailAddress, person.emailAddress) && Objects.equals(password, person.password) && roleStatus == person.roleStatus && Objects.equals(isActive, person.isActive) && personStatus == person.personStatus && Objects.equals(registrationDate, person.registrationDate) && role == person.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, emailAddress, password, roleStatus, isActive, personStatus, registrationDate, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleStatus.name()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}





