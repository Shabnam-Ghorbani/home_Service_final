package ir.maktab.home_service.data.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BaseService extends BaseEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "baseService")
    private Set<SubService> subServices = new HashSet<>();
}
