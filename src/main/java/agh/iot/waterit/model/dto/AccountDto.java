package agh.iot.waterit.model.dto;

import java.util.List;
import java.util.Set;

public record AccountDto(
        Long id,
        String email,
        String password,
        String name,
        Set<RoleDto> roles,
        boolean enabled,
        boolean expired,
        boolean credentialsExpired,
        boolean locked,
        List<DeviceDto> devices
) {
}
