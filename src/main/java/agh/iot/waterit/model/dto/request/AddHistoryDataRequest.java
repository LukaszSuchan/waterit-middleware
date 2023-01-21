package agh.iot.waterit.model.dto.request;

import agh.iot.waterit.model.dto.DataDto;

import java.util.List;

public record AddHistoryDataRequest(
        String name,
        int interval,
        List<DataDto> data
) {
}
