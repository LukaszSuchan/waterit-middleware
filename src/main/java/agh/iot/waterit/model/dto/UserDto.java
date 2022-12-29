package agh.iot.waterit.model.dto;

import lombok.NonNull;

public record UserDto(
        @NonNull String email,
        @NonNull String password,
        @NonNull String name
) {
}
