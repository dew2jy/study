package com.example.demo.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors) {
        if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {
            errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong.");
            errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is wrong.");
        }

        LocalDateTime endEventDateTime =  eventDto.getEndEventDateTime();

        if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("endEventDateTime", "wrongValue", "EndEventDateTime is wrong.");
        }

        LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();

        if(beginEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                beginEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())
        ) {
            errors.rejectValue("beginEventDateTime", "wrongValue", "BeginEventDateTime is wrong.");
        }

        LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();

        if(closeEnrollmentDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("closeEnrollmentDateTime", "wrongValue", "CloseEnrollmentDateTime is wrong.");
        }
    }
}
