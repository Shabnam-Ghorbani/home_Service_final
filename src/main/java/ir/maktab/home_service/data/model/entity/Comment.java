package ir.maktab.home_service.data.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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

    private String comment;

    @OneToOne
    @JoinColumn(name = "order_id")

    private Order order;

    public Comment(Double score, String comment) {
        this.score = score;
        this.comment = comment;
    }
}




