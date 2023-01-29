package ir.maktab.home_service.data.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Order> instruction;

    @Override
    public String toString() {
        return super.toString();
    }
}
