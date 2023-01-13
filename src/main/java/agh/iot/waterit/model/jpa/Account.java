package agh.iot.waterit.model.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull
    private String email;
    @Column
    private String name;
    @NotNull
    @Column(name = "password")
    private String password;
    private boolean enabled = true;
    private boolean expired = false;
    private boolean credentialsExpired = false;
    private boolean locked = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "AccountRole",
            joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id")
    )
    private Set<Role> roles;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    List<Device> devices;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wifi_settings_id", referencedColumnName = "id")
    WifiSettings wifiSettings;
}