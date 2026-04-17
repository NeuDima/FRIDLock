package neupokoev.com.lockservice.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "t_users", schema = "rfid_lock")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "c_name", nullable = false, length = 128)
    private String name;

    @Column(name = "c_password", nullable = false, length = 128)
    private String password;


}