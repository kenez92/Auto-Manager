package pl.car.automanager.persistence.entity.expanses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseExpanse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid = UUID.randomUUID().toString();

    @Override
    public boolean equals(Object that) {
        return this == that ||
                that instanceof BaseExpanse && Objects.equals(uuid, ((BaseExpanse) that).uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
