package ir.maktab.home_service.data.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private String city;

    private String state;

    private String streetAddress;

    private String houseNumber;

    @Column(unique = true)
    private Long zipCode;
}
