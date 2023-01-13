package agh.iot.waterit.model.mapper;

import agh.iot.waterit.model.dto.AccountDto;
import agh.iot.waterit.model.dto.DeviceDto;
import agh.iot.waterit.model.dto.RoleDto;
import agh.iot.waterit.model.dto.WifiSettingsDto;
import agh.iot.waterit.model.jpa.Account;
import agh.iot.waterit.model.jpa.Device;
import agh.iot.waterit.model.jpa.Role;
import agh.iot.waterit.model.jpa.WifiSettings;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    AccountDto toDto(Account account);

    Account toJpa(AccountDto accountDto);

    RoleDto toDto(Role role);

    Role toJpa(RoleDto roleDto);

    DeviceDto toDto(Device device);

    Device toJpa(DeviceDto deviceDto);

    WifiSettingsDto toDto(WifiSettings wifiSettings);

    WifiSettings toJpa(WifiSettingsDto wifiSettingsDto);


}
