package agh.iot.waterit.service;

import agh.iot.waterit.model.dao.DataRepository;
import agh.iot.waterit.model.dao.DeviceRepository;
import agh.iot.waterit.model.dto.DataDto;
import agh.iot.waterit.model.dto.request.AddHistoryDataRequest;
import agh.iot.waterit.model.jpa.Data;
import agh.iot.waterit.model.mapper.ModelMapper;
import agh.iot.waterit.utils.exception.CoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static agh.iot.waterit.utils.exception.ErrorCode.NOT_FOUND;
import static agh.iot.waterit.utils.exception.ErrorSubcode.DEVICE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DataService {

    final private DataRepository dataRepository;
    final private DeviceService deviceService;
    final private DeviceRepository deviceRepository;
    final private ModelMapper modelMapper;

    public void addHistoryData(AddHistoryDataRequest request) {
        final var device = deviceRepository.findByName(request.name())
                .orElseThrow(
                        () -> new CoreException(NOT_FOUND, DEVICE_NOT_FOUND)
                );
        final var dataList = request.data().stream()
                .map(modelMapper::toJpa)
                .toList();

        final var deviceId = device.getId();
        final var interval = request.interval();
        final var c = Calendar.getInstance();
        final var now = new Date();
        c.setTime(now);
        final var backInTime = -1 * dataList.size() * interval - interval;
        c.add(Calendar.SECOND, backInTime);
        Date dateOfMeasurement = c.getTime();


        for (Data data : dataList) {
            data.setTemperature(data.getTemperature().divide(BigDecimal.valueOf(100)));
            data.setHumidity(data.getHumidity().divide(BigDecimal.valueOf(100)));
            data.setLightIntensity(data.getLightIntensity().divide(BigDecimal.valueOf(100)));
            data.setMoistureHumidity(data.getMoistureHumidity().divide(BigDecimal.valueOf(100)));
            c.setTime(dateOfMeasurement);
            c.add(Calendar.SECOND, interval);
            dateOfMeasurement = c.getTime();
            data.setDateOfMeasurement(dateOfMeasurement);
            data.setDeviceId(deviceId);
        }

        dataRepository.saveAll(dataList);

    }

    public List<DataDto> getAllDeviceData(Long id) {
        return deviceService.getDeviceById(id).data();
    }
}
