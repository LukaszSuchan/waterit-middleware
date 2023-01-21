package agh.iot.waterit.model.dto;

import jakarta.annotation.Nullable;

import java.util.List;

public record DeviceDto(
        @Nullable
        Long id,
        String name,
        String deviceName,
        boolean active,
        @Nullable
        Long accountId,
        List<DataDto> data
) {
}
