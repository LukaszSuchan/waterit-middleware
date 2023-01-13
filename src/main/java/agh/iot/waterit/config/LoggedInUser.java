package agh.iot.waterit.config;

import agh.iot.waterit.model.dao.AccountRepository;
import agh.iot.waterit.model.dto.AccountDto;
import agh.iot.waterit.model.mapper.ModelMapper;
import agh.iot.waterit.utils.exception.CoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static agh.iot.waterit.utils.exception.ErrorCode.NOT_FOUND;
import static agh.iot.waterit.utils.exception.ErrorSubcode.ACCOUNT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class LoggedInUser {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountDto getAccountInfo() {
        final var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findByEmail(username)
                .map(modelMapper::toDto)
                .orElseThrow(
                        () -> new CoreException(NOT_FOUND, ACCOUNT_NOT_FOUND)
                );
    }
}
