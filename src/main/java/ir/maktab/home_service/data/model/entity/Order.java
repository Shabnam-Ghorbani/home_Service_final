package ir.maktab.home_service.data.model.entity;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
    private Date orderRegistrationDate; //ToDo

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateOfWorkPerformed; //ToDo

    @ManyToOne
    @JoinColumn(nullable = false)
    private Address address;

    private String description;

    private LocalDateTime executionTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<Offer> offers = new HashSet<>();

    @ManyToOne
    private Expert expert;

    @Override
    public String toString() {
        return "Order{" +
                "subService=" + subService +
                ", proposedPrice=" + proposedPrice +
                ", jobDescription='" + jobDescription + '\'' +
                ", orderRegistrationDate=" + orderRegistrationDate +
                ", dateOfWorkPerformed=" + dateOfWorkPerformed +
                ", address=" + address +
                ", customer=" + customer +
                ", orderStatus=" + orderStatus +
                ", offers=" + offers +
                ", expert=" + expert +
                '}';
    }
}
