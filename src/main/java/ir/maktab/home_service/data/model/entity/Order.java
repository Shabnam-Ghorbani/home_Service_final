package ir.maktab.home_service.data.model.entity;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private SubService subService;

    @Column(nullable = false)
    private Long proposedPrice;

    @Column(length = 300, nullable = false)
    private String jobDescription;

    @CreationTimestamp
    private Date orderRegistrationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateOfWorkPerformed;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<Offer> offers = new HashSet<>();

    @ManyToOne
    private Expert expert;
}
