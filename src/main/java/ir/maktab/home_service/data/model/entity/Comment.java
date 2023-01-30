package ir.maktab.home_service.data.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    private Double score;

    private String opinion;

    @OneToOne
    private Order order;
}
