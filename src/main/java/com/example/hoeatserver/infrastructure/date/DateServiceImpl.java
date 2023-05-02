package com.example.hoeatserver.infrastructure.date;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class DateServiceImpl implements DateService {

    @Override
    public String betweenDate(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(dateTime.toLocalTime(), now.toLocalTime());
        Period period = Period.between(dateTime.toLocalDate(), now.toLocalDate());

        if(diff.getSeconds() < 60) { return "방금전"; }
        if(diff.toMinutes() < 60) { return diff.toMinutes() + "분전"; }
        if(diff.toHours() < 24) { return diff.toHours() + "시간전"; }
        if(period.getDays() <= 30) { return diff.toDays() + "일전"; }
        if(period.getMonths() <= 12) { return period.getMonths() + "달전"; }
        return period.getYears() + "년전";
    }
}
