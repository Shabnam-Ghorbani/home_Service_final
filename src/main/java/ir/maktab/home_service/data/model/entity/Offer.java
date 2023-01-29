package ir.maktab.home_service.data.model.entity;

import ir.maktab.home_service.data.model.enamiration.OfferStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Offer extends BaseEntity{

    @ManyToOne
    private Expert expert;

    @ManyToOne
    private Order order;

    @CreationTimestamp
    private Date registrationDate;

    private Long proposedPrice;

    private int durationOfWork;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;

}
