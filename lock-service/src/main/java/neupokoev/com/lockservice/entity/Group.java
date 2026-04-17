package neupokoev.com.lockservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_group", schema = "rfid_lock")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "c_name", nullable = false, length = 256)
    private String name;

    @OneToMany(mappedBy = "group")
    private List<KeyGroup> keyGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Lock> locks = new ArrayList<>();
}