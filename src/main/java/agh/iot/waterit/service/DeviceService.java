package agh.iot.waterit.service;

import agh.iot.waterit.config.LoggedInUser;
import agh.iot.waterit.model.dao.DeviceRepository;
import agh.iot.waterit.model.dto.DeviceDto;
import agh.iot.waterit.model.mapper.ModelMapper;
import agh.iot.waterit.utils.exception.CoreException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static agh.iot.waterit.utils.exception.ErrorCode.NOT_FOUND;
import static agh.iot.waterit.utils.exception.ErrorSubcode.DEVICE_NOT_FOUND;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final LoggedInUser loggedInUser;
    private final ModelMapper modelMapper;

    public DeviceDto getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .map(modelMapper::toDto)
                .orElseThrow(
                        () -> new CoreException(NOT_FOUND, DEVICE_NOT_FOUND)
                );
    }

    public List<DeviceDto> getAllUserActiveDevices() {
        var devices = loggedInUser.getAccountInfo().devices();
        var activeDevices = devices.stream()
                .toList();
        return activeDevices;
    }

    public Long addDevice(DeviceDto request) {
        var deviceOpt = deviceRepository.findByName(request.name());
        if(deviceOpt.isPresent()) {
            var usedDevice = deviceOpt.get();
            usedDevice.setName(request.name() + "-UNUSED");
            usedDevice.setActive(false);
            deviceRepository.save(usedDevice);
        }
        final var device = modelMapper.toJpa(request);
        device.setAccountId(loggedInUser.getAccountInfo().id());
        device.setActive(true);
        return deviceRepository.save(device).getId();
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    public void deleteDeviceByName(String name) {
        final var device =deviceRepository.findByName(name).orElseThrow(
                () -> new CoreException(NOT_FOUND, DEVICE_NOT_FOUND)
        );
        deviceRepository.deleteById(device.getId());
    }

    public void activateDevice(String name) {
        final var device = deviceRepository.findAllByName(name).stream().findFirst().orElseThrow(
                () -> new CoreException(NOT_FOUND, DEVICE_NOT_FOUND)
        );
        device.setActive(true);
        deviceRepository.save(device);
    }

    public DeviceDto getDeviceByName(String name) {
        return deviceRepository.findByName(name)
                .map(modelMapper::toDto)
                .orElseThrow(
                        () -> new CoreException(NOT_FOUND, DEVICE_NOT_FOUND)
                );
    }
}
