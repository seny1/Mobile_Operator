package org.elSasen.mapper;

import org.elSasen.dto.insert.CallDtoInsert;
import org.elSasen.dto.select.CallDto;
import org.elSasen.entities.Call;
import org.elSasen.entities.Client;
import org.elSasen.entities.ClientPassport;

public class CallMapper implements Mapper<Call, CallDto> {

    private static final CallMapper INSTANCE = new CallMapper();

    @Override
    public CallDto mapFrom(Call call) {
        return CallDto.builder()
                .callId(call.getCallId())
                .client(call.getClient())
                .subscriberNumber(call.getSubscriberNumber())
                .callDuration(call.getCallDuration())
                .build();
    }

    public Call mapFrom(CallDtoInsert callDto) {
        return Call.builder()
                .client(Client.builder()
                        .clientId(callDto.getClientId())
                        .build())
                .subscriberNumber(callDto.getSubscriberNumber())
                .callDuration(callDto.getCallDuration())
                .build();
    }

    public static CallMapper getInstance() {
        return INSTANCE;
    }
}
