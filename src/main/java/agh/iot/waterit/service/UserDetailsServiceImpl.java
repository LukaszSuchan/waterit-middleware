package agh.iot.waterit.service;

import agh.iot.waterit.model.jpa.Account;
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
        Account account = accountService.getUserByEmail(username);
        if(account.getRoles().isEmpty() || account.getRoles() == null) {
            throw new CoreException(INTERNAL_ERROR, ROLE_NOT_FOUND);
        }

        List<SimpleGrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        return new User(account.getEmail(), account.getPassword(), account.isEnabled(), !account.isExpired(),
                !account.isCredentialsExpired(), !account.isLocked(), authorities);
    }
}
