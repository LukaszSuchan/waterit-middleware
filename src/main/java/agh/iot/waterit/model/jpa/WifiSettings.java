package agh.iot.waterit.model.jpa;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wifi_settings")
public class WifiSettings {

    @Id
    Long id;
    private String ssid;
    private String wifiPassword;
    private String serverIp;
    @OneToOne(mappedBy = "wifiSettings")
    private Account account;
}
