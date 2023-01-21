package agh.iot.waterit.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public record DataDto(
        BigDecimal lightIntensity,
        BigDecimal temperature,
        BigDecimal humidity,
        BigDecimal moistureHumidity,
        Date dateOfMeasurement
) {
}
