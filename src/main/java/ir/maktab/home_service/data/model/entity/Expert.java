package ir.maktab.home_service.data.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Expert extends User implements Comparable<Expert> {
    @Lob
    @Column(length = 300000)
    private byte[] photo;
    private Double score;
    @ManyToMany(mappedBy = "experts", fetch = FetchType.EAGER)
    private Set<SubService> services = new HashSet<>();

    @Override
    public String toString() {
        return super.toString() +
                "score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Expert expert = (Expert) o;
        return Arrays.equals(photo, expert.photo) && Objects.equals(score, expert.score) && Objects.equals(services, expert.services);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), score, services);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public int compareTo(Expert o) {
        if (this.score == o.score)
            return Double.compare(this.score, o.score);
        else if (this.score < o.score)
            return 1;
        else return -1;
    }
}
