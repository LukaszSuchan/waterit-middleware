package agh.iot.waterit.service;

import agh.iot.waterit.config.LoggedInUser;
import agh.iot.waterit.model.dao.AccountRepository;
import agh.iot.waterit.model.dao.RoleRepository;
import agh.iot.waterit.model.dto.AccountDto;
import agh.iot.waterit.model.dto.WifiSettingsDto;
import agh.iot.waterit.model.jpa.Account;
import agh.iot.waterit.model.jpa.Role;
import agh.iot.waterit.model.jpa.WifiSettings;
import agh.iot.waterit.model.mapper.ModelMapper;
import agh.iot.waterit.utils.CryptoUtils;
import agh.iot.waterit.utils.exception.CoreException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static agh.iot.waterit.utils.exception.ErrorCode.NOT_FOUND;
import static agh.iot.waterit.utils.exception.ErrorSubcode.ACCOUNT_NOT_FOUND;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final LoggedInUser loggedInUser;

    public Long create(AccountDto request) throws Exception {
        final var encryptedPassword = CryptoUtils.encrypt(request.password());
        final var role = roleRepository.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        final var account = Account.builder()
                .email(request.email())
                .password(encryptedPassword)
                .name(request.name())
                .roles(roles)
                .build();
        var savedAccount = accountRepository.save(account);

        return savedAccount.getId();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new CoreException(NOT_FOUND, ACCOUNT_NOT_FOUND));
    }

    public AccountDto getUserByEmail(String email) {
        return accountRepository.findByEmail(email)
                .map(modelMapper::toDto)
                .orElseThrow(() -> new CoreException(NOT_FOUND, ACCOUNT_NOT_FOUND));
    }

    public void updateWifiCredentials(WifiSettingsDto request) {
        var account = accountRepository.findById(loggedInUser.getAccountInfo().id())
                .orElseThrow(() -> new CoreException(NOT_FOUND, ACCOUNT_NOT_FOUND));
        var settings = account.getWifiSettings();
        if (settings == null) {
            settings = new WifiSettings();
        }
        settings.setSsid(request.ssid());
        settings.setWifiPassword(request.wifiPassword());
        settings.setServerIp(request.serverIp());

        account.setWifiSettings(settings);

        accountRepository.save(account);
    }

    public WifiSettingsDto getWifiSettings() {
        final var account = accountRepository.findById(loggedInUser.getAccountInfo().id())
                .orElseThrow(() -> new CoreException(NOT_FOUND, ACCOUNT_NOT_FOUND));

        return modelMapper.toDto(account.getWifiSettings());

    }
}
