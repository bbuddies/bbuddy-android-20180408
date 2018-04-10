package com.odde.bbuddy.budget.viewmodel;

import org.joda.time.LocalDate;

public class Period {
    private final LocalDate startDay;
    private final LocalDate endDay;

    public Period(LocalDate startDay, LocalDate endDay) {
        this.startDay = startDay;
        this.endDay = endDay;
    }

    private int daysBetween() {
        return endDay.getDayOfMonth() - startDay.getDayOfMonth() + 1;
    }

    public int getOverlappingDayCount(Period another) {
        LocalDate overlappingStartDay = startDay.isAfter(another.startDay) ? startDay : another.startDay;
        LocalDate overlappingEndDay = endDay.isBefore(another.endDay) ? endDay : another.endDay;

        if (overlappingStartDay.isAfter(overlappingEndDay))
            return 0;

        return new Period(overlappingStartDay, overlappingEndDay).daysBetween();
    }
}
