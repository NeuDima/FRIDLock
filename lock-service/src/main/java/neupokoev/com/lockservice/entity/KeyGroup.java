package neupokoev.com.lockservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "t_key_group", schema = "rfid_lock")
public class KeyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "c_key_id")
    private Key key;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "c_group_id")
    private Group group;

    public void setKey(Key key) {
        this.key = key;
        key.getKeyGroups().add(this);
    }

    public void setGroup(Group group) {
        this.group = group;
        group.getKeyGroups().add(this);
    }
}