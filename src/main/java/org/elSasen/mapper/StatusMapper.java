package org.elSasen.mapper;

import org.elSasen.dto.select.StatusDto;
import org.elSasen.entities.Status;

public class StatusMapper implements Mapper<Status, StatusDto> {
    @Override
    public StatusDto mapFrom(Status status) {
        return StatusDto.builder()
                .statusId(status.getStatusId())
                .name(status.getName())
                .description(status.getDescription())
                .build();
    }
    private static final StatusMapper INSTANCE = new StatusMapper();
    public static StatusMapper getInstance() {
        return INSTANCE;
    }
}
