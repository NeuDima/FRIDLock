package neupokoev.com.lockservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_key", schema = "rfid_lock")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "c_uid", nullable = false, length = 128, unique = true)
    private String uid;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_type", nullable = false, length = 128)
    private TypeKey type;

    @Builder.Default
    @OneToMany(mappedBy = "key", fetch = FetchType.LAZY)
    private List<KeyGroup> keyGroups = new ArrayList<>();
}