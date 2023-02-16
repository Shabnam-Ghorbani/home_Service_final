package ir.maktab.home_service.data.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubService extends BaseEntity {

    @Column(unique = true)
    private String name;

    private Long basePrice;

    @Column(length = 300)
    private String description;

    @ManyToOne
    private BaseService baseService;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Expert> experts = new HashSet<>();

    @OneToMany(mappedBy = "subService", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return "SubService{" +
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubService that = (SubService) o;
        return Objects.equals(name, that.name) && Objects.equals(basePrice, that.basePrice) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, basePrice, description);
    }
}
