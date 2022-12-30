package agh.iot.waterit.model.mapper;

import agh.iot.waterit.model.dto.AccountDto;
import agh.iot.waterit.model.jpa.Account;
//import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface UserMapper {
    
    Account toDto(AccountDto accountDto);

    AccountDto toJpa(Account appUser);
}
