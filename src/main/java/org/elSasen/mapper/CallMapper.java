package org.elSasen.mapper;

import org.elSasen.dao.CallDao;
import org.elSasen.dto.CallDto;
import org.elSasen.entities.Call;

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

    public static CallMapper getInstance() {
        return INSTANCE;
    }
}
