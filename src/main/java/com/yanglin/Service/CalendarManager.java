package com.yanglin.Service;

import com.yanglin.Models.DayModel;
import com.yanglin.Models.Event;
import com.yanglin.Models.Month;
import com.yanglin.Repository.IDayRepo;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class CalendarManager
{
    private IDayRepo dayRepo;

    private SortedSet<DayModel> days;


    public CalendarManager(IDayRepo dayRepo)
    {
        this.dayRepo = dayRepo;
        this.days = this.dayRepo.getDays();
    }

    public ObservableList<Event> getEventsByDay(DayModel day)
    {
        return days.stream().filter(d -> d.equals(day)).findFirst().get().getEvents();
    }

    public SortedSet<DayModel> getDaysByMonthYear(int year, Month month)
    {
        return days.stream()
                .filter(d -> (d.getMonth() == month && d.getYear() == year))
                .collect(Collectors.toCollection(TreeSet<DayModel>::new));
    }

    public SortedSet<DayModel> getDaysOfPreMonth(int currentYear, Month currentMonth)
    {
        int preYear=currentYear;
        Month preMonth = currentMonth;

        if (currentMonth==Month.JANUARI)
        {
            preYear++;
            preMonth = Month.DECEMBER;
        }
        else
        {
            preMonth = currentMonth.getPreMonth();
        }
        return getDaysByMonthYear(preYear,preMonth);
    }

    public SortedSet<DayModel> getDaysOfNextMonth(int currentYear, Month currentMonth)
    {
        int nextYear=currentYear;
        Month nextMonth;

        if (currentMonth==Month.DECEMBER)
        {
            nextYear++;
            nextMonth = Month.JANUARI;
        }
        else
        {
            nextMonth = currentMonth.getNextMonth();
        }
        return getDaysByMonthYear(nextYear,nextMonth);
    }

}
