package agh.iot.waterit.model.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wifi_settings")
public class WifiSettings {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "wifi_settings_id_seq"
    )
    @SequenceGenerator(
            name = "wifi_settings_id_seq",
            sequenceName = "wifi_settings_id_sequence",
            allocationSize = 1
    )
    @Column(
            name = "id",
            unique = true,
            updatable = false,
            nullable = false
    )
    Long id;
    private String ssid;
    private String wifiPassword;
    private String serverIp;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
