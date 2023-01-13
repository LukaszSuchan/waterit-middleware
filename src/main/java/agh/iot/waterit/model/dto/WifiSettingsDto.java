package agh.iot.waterit.model.dto;

public record WifiSettingsDto(
        String ssid,
        String wifiPassword,
        String serverIp
) {
}
