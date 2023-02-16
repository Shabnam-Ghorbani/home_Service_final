package ir.maktab.home_service.data.model.entity;

import ir.maktab.home_service.data.model.enamiration.OfferStatus;
import ir.maktab.home_service.data.model.enamiration.TimeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Offer extends BaseEntity {
    private String offer;

    @ManyToOne
    private Expert expert;

    @ManyToOne
    private Order order;

    private Boolean isAccept;

    @CreationTimestamp
    private Date registrationDate;

    private Long proposedPrice;

    private int durationOfWork;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    private LocalDateTime endTime;


    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;

    @Enumerated(value = EnumType.STRING)
    private TimeType timeType;

    private Integer durationTime;

    public Offer(String offer, Long proposedPrice, TimeType timeType, Integer durationTime, LocalDateTime endTime) {
        this.offer = offer;
        this.proposedPrice = proposedPrice;
        this.timeType = timeType;
        this.durationTime = durationTime;
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        return "Offer {" +
                "offer='" + offer + '\'' +
                ", proposedPrice=" + proposedPrice +
                ", timeType=" + timeType +
                ", durationTime=" + durationTime +
                ", isAccept=" + isAccept +
                ", expertScore='" + expert.getScore() + '\'' +

                "} ";
    }
}
