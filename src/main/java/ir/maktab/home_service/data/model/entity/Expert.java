package ir.maktab.home_service.data.model.entity;

import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.enamiration.RoleStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Expert extends Person implements Comparable<Expert> {
    @Enumerated(value = EnumType.STRING)
    PersonStatus personStatus;

    @Enumerated(value = EnumType.STRING)
    RoleStatus roleStatus;
    @Lob
    @Column(length = 300000)
    private byte[] photo;
    private Double score;
    private Long credit;
    @ManyToMany(mappedBy = "experts", fetch = FetchType.EAGER)
    private Set<SubService> services = new HashSet<>();

    @Override
    public String toString() {
        return "Expert{" +
                "personStatus=" + personStatus +
                ", photo=" + Arrays.toString(photo) +
                ", score=" + score +
                ", credit=" + credit +
                ", services=" + services +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Expert expert = (Expert) o;
        return personStatus == expert.personStatus && Arrays.equals(photo, expert.photo) && Objects.equals(score, expert.score) && Objects.equals(credit, expert.credit) && Objects.equals(services, expert.services);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), personStatus, score, credit, services);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    public void addSubService(SubService subService) {
        this.services.add(subService);
        subService.getExperts().add(this);
    }

    public void deleteSubService(SubService subService) {
        this.services.remove(subService);
        subService.getExperts().remove(this);
    }

    @Override
    public int compareTo(Expert o) {
        if (this.score == o.score)
            return Double.compare(this.score, o.score);
        else if (this.score < o.score)
            return 1;
        else return -1;
    }

    public boolean isPresent() { // todo baresi shavad shaboo
        return Boolean.parseBoolean(null);
    }
}
