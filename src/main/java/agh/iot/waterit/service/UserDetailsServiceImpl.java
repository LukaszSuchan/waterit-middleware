package agh.iot.waterit.service;

import agh.iot.waterit.model.dto.AccountDto;
import agh.iot.waterit.utils.exception.CoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static agh.iot.waterit.utils.exception.ErrorCode.INTERNAL_ERROR;
import static agh.iot.waterit.utils.exception.ErrorSubcode.ROLE_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDto accountDto = accountService.getUserByEmail(username);
        if (accountDto.roles().isEmpty() || accountDto.roles() == null) {
            throw new CoreException(INTERNAL_ERROR, ROLE_NOT_FOUND);
        }

        List<SimpleGrantedAuthority> authorities = accountDto.roles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();

        return new User(accountDto.email(), accountDto.password(), accountDto.enabled(), !accountDto.expired(),
                !accountDto.credentialsExpired(), !accountDto.locked(), authorities);
    }
}
