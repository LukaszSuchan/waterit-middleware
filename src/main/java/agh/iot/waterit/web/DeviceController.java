package agh.iot.waterit.web;

import agh.iot.waterit.config.LoggedInUser;
import agh.iot.waterit.model.dao.ConfirmationRepository;
import agh.iot.waterit.model.dao.DeviceRepository;
import agh.iot.waterit.model.dto.DataDto;
import agh.iot.waterit.model.dto.DeviceDto;
import agh.iot.waterit.model.dto.request.AddHistoryDataRequest;
import agh.iot.waterit.model.dto.response.ExternalDataResponse;
import agh.iot.waterit.model.jpa.Confirmation;
import agh.iot.waterit.service.DataService;
import agh.iot.waterit.service.DeviceService;
import agh.iot.waterit.utils.UriBuilder;
import agh.iot.waterit.utils.exception.CoreException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static agh.iot.waterit.utils.exception.ErrorCode.NOT_FOUND;
import static agh.iot.waterit.utils.exception.ErrorSubcode.CONFIRMATION_NOT_FOUND;
import static agh.iot.waterit.utils.exception.ErrorSubcode.DEVICE_NOT_FOUND;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(path = "device")
public class DeviceController {

    private final LoggedInUser loggedInUser;
    private final DeviceService deviceService;
    private final UriBuilder uriBuilder = new UriBuilder();
    private final DataService dataService;
    private final ConfirmationRepository confirmationRepository;
    private final DeviceRepository deviceRepository;

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

    @DeleteMapping("esp/{name}")
    public ResponseEntity<Void> deleteDeviceByName(@PathVariable String name) {
        deviceService.deleteDeviceByName(name);
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

    @GetMapping("{name}/history/external-temperature")
    public ResponseEntity<ExternalDataResponse> getAllDeviceData(@PathVariable String name) {
        return ResponseEntity.ok(dataService.getLatestExternalDataResponse(name));
    }

    @PostMapping("esp/{name}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable String name) {
        final var c = Calendar.getInstance();
        final var now = new Date();
        c.setTime(now);
        c.add(Calendar.MINUTE, 3);
        Date validDate = c.getTime();
        confirmationRepository.save(Confirmation.builder().name(name).validDate(validDate).build());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("esp/{name}/confirm")
    public ResponseEntity<Confirmation> getConfirmation(@PathVariable String name) {
        final var confirmation = confirmationRepository.findFirstByNameOrderById(name).orElseThrow(
                () -> new CoreException(NOT_FOUND, CONFIRMATION_NOT_FOUND)
        );
        if(confirmation.getValidDate().after(new Date())) {
            return ResponseEntity.ok(confirmation);
        } else {
            confirmationRepository.delete(confirmation);
            throw new CoreException(NOT_FOUND, CONFIRMATION_NOT_FOUND);
        }
    }

    @DeleteMapping("esp/{name}/confirm")
    public ResponseEntity<Void> deleteConfirmation(@PathVariable String name) {
        final var confirmation = confirmationRepository.findFirstByNameOrderById(name).orElseThrow(
                () -> new CoreException(NOT_FOUND, CONFIRMATION_NOT_FOUND)
        );
        confirmationRepository.deleteById(confirmation.getId());
        return ResponseEntity.noContent().build();
    }

}
