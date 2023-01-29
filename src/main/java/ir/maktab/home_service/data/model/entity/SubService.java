package ir.maktab.home_service.data.model.entity;


import jakarta.persistence.*;
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
@EqualsAndHashCode
public class SubService extends BaseEntity{

    @Column(unique = true)
    private String name;

    private Long basePrice;

    @Column(length = 300)
    private String description;

    @ManyToOne
    private BaseService baseService;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Expert> experts = new HashSet<>();
}
