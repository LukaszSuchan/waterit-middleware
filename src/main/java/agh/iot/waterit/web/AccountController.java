package agh.iot.waterit.web;

import agh.iot.waterit.config.LoggedInUser;
import agh.iot.waterit.model.dto.AccountDto;
import agh.iot.waterit.model.dto.WifiSettingsDto;
import agh.iot.waterit.service.AccountService;
import agh.iot.waterit.utils.UriBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(path = "account")
public class AccountController {

    private final AccountService accountService;
    private final UriBuilder uriBuilder = new UriBuilder();
    private final LoggedInUser loggedInUser;

    @PostMapping("register")
    public ResponseEntity<Void> createUser(@RequestBody AccountDto account) throws Exception {
        var id = accountService.create(account);
        var locationUri = uriBuilder.requestUriWithId(id);
        return ResponseEntity.created(locationUri).build();
    }

    @GetMapping
    public ResponseEntity<AccountDto> getAccount() {
        return ResponseEntity.ok(loggedInUser.getAccountInfo());
    }

    @PostMapping
    public ResponseEntity<Void> updateWifiSettings(WifiSettingsDto request) {
        accountService.updateWifiCredentials(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<WifiSettingsDto> getWifiSettings() {
        return ResponseEntity.ok(accountService.getWifiSettings());
    }
}
