package com.yanglin.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.SortedSet;

/**
 * Created by yanglin on 25/11/16.
 */
public class CalendarViewModel
{
    private SortedSet<DayModel> days;
    private SimpleIntegerProperty currentYear;
    private SimpleObjectProperty<Month> currentMonth;
    private SimpleStringProperty currentMonthStr;

    public CalendarViewModel()
    {
        this.currentYear = new SimpleIntegerProperty();
        this.currentMonth = new SimpleObjectProperty<>();
        this.currentMonthStr = new SimpleStringProperty();
    }

    public Month getCurrentMonth()
    {
        return currentMonth.getValue();
    }

    public SimpleObjectProperty<Month> getMonthProp()
    {
        return this.currentMonth;
    }

    public SimpleStringProperty getCurrentMonthStrProp()
    {
        return this.currentMonthStr;
    }

    public int getCurrentYear()
    {
        return currentYear.getValue();
    }

    public SortedSet<DayModel> getCurrentDays()
    {
        return days;
    }

    public void setCurrentMonthYear(int year, Month currentMonth)
    {
        this.currentMonth.setValue(currentMonth);
        this.currentYear.setValue(year);
    }

    public void setDays(SortedSet<DayModel> days)
    {
        this.days = days;
        updateCurrentDate();

    }

    private void updateCurrentDate()
    {
        this.currentMonth.setValue(this.days.first().getMonth());
        this.currentYear.setValue(this.days.first().getYear());
        this.currentMonthStr.setValue(this.currentMonth.getValue().toString());
    }
}
