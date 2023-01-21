package agh.iot.waterit.model.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "data")
public class Data {

    Date dateOfMeasurement;
    BigDecimal lightIntensity;
    BigDecimal temperature;
    BigDecimal humidity;
    BigDecimal moistureHumidity;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false, insertable = false, updatable = false)
    private Device device;
    @Column(name = "device_id")
    private Long deviceId;

}
