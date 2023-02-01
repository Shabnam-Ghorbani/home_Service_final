package ir.maktab.home_service.data.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseService extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "baseService")
    private Set<SubService> subServices = new HashSet<>();

    @Override
    public String toString() {
        return "BaseService{" +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseService that = (BaseService) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
