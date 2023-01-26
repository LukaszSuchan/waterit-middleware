package agh.iot.waterit.model.dto.response;

import java.math.BigDecimal;

public record ExternalDataResponse(
        BigDecimal externalTemperature
) {
}
