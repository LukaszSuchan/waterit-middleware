package agh.iot.waterit.model.mapper;

import agh.iot.waterit.model.dto.*;
import agh.iot.waterit.model.jpa.*;
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

    DataDto toDto(Data data);

    Data toJpa(DataDto dataDto);
}
