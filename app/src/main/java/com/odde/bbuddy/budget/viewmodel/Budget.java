package com.odde.bbuddy.budget.viewmodel;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Budget implements Serializable {

    private String month;

    private int amount;

    private int dailyAmount() {
        return amount / getDaysOfMonth(startOfMonth());
    }

    private int getDaysOfMonth(LocalDate localDate) {
        DateTime dateTime = new DateTime(localDate.getYear(),
                localDate.getMonthOfYear(), 14, 12,
                0, 0, 000);
        return dateTime.dayOfMonth().getMaximumValue();
    }

    @NonNull
    private LocalDate startOfMonth() {
        return new LocalDate(month);
    }

    @NonNull
    private LocalDate endOfMonth() {
        return startOfMonth().dayOfMonth().withMaximumValue();
    }

    public int getOverlappingAmount(Period period) {
        return dailyAmount() * period.getOverlappingDayCount(new Period(startOfMonth(), endOfMonth()));
    }
}
