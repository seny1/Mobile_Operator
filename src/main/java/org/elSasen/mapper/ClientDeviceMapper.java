package org.elSasen.mapper;

import org.elSasen.dto.select.ClientDeviceDto;
import org.elSasen.entities.ClientDevice;

public class ClientDeviceMapper implements Mapper<ClientDevice, ClientDeviceDto> {

    private static final ClientDeviceMapper INSTANCE = new ClientDeviceMapper();

    @Override
    public ClientDeviceDto mapFrom(ClientDevice clientDevice) {
        return ClientDeviceDto.builder()
                .deviceId(clientDevice.getDeviceId())
                .model(clientDevice.getModel())
                .clientProblem(clientDevice.getClientProblem())
                .build();
    }

    public static ClientDeviceMapper getInstance() {
        return INSTANCE;
    }
}
