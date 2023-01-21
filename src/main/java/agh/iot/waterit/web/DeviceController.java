package agh.iot.waterit.web;

import agh.iot.waterit.config.LoggedInUser;
import agh.iot.waterit.model.dto.DataDto;
import agh.iot.waterit.model.dto.DeviceDto;
import agh.iot.waterit.model.dto.request.AddHistoryDataRequest;
import agh.iot.waterit.service.DataService;
import agh.iot.waterit.service.DeviceService;
import agh.iot.waterit.utils.UriBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(path = "device")
public class DeviceController {

    private final LoggedInUser loggedInUser;
    private final DeviceService deviceService;
    private final UriBuilder uriBuilder = new UriBuilder();
    private final DataService dataService;

    @GetMapping()
    public List<DeviceDto> getAllLoggedInUserDevices() {
        return deviceService.getAllUserActiveDevices();
    }

    @GetMapping("{id}")
    public DeviceDto getAllLoggedInUserDevice(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addDevice(@RequestBody DeviceDto request) {
        final var id = deviceService.addDevice(request);
        var locationUri = uriBuilder.requestUriWithId(id);
        return ResponseEntity.created(locationUri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("activate")
    public ResponseEntity<Void> activateDevice(@RequestParam String name) {
        deviceService.activateDevice(name);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/history")
    public ResponseEntity<Void> addHistory(@RequestBody AddHistoryDataRequest request) {
        dataService.addHistoryData(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/history")
    public ResponseEntity<List<DataDto>> getAllDeviceData(@PathVariable Long id) {
        return ResponseEntity.ok(dataService.getAllDeviceData(id));
    }

}
