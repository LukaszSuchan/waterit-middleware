package agh.iot.waterit.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record AccountDto(
        @NotNull String email,
        @NotNull String password,
        @NotNull String name
) {
}
