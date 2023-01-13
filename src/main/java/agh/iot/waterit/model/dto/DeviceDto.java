package agh.iot.waterit.model.dto;

import jakarta.annotation.Nullable;

public record DeviceDto(
        @Nullable
        Long id,
        String name,
        String deviceName,
        boolean isActive,
        @Nullable
        Long accountId
) {
}
