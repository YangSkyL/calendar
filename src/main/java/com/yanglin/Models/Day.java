package com.yanglin.Models;

import java.util.LinkedList;
import java.util.List;

public class Day implements Comparable<Day>
{
    private long id;
    private int day;
    private Month month;
    private int year;
    private Weekday weekday;
    private List<Event> events;

    public Day()
    {
        this.events = new LinkedList<>();
    }

    public Day(int day, int month, int year)
    {
        this();
        this.day = day;
        this.month = Month.getMonthFromDigit(month);
        this.year = year;
        this.weekday = Helper.getWeekdayFromDate(this.year,this.month,this.day);
    }

    public Day(int day, int month, int year, List<Event> events)
    {
        this(day,month,year);
        this.events = events;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }


    public List<Event> getEvents()
    {
        return events;
    }


    public Month getMonth()
    {
        return month;
    }

    public void setMonth(Month month)
    {
        this.month = month;
    }

    public Weekday getWeekday()
    {
        return weekday;
    }

    public void setWeekday(Weekday weekday)
    {
        this.weekday = weekday;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setEvents(List<Event> events)
    {
        this.events = events;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void addEvent(Event event)
    {
        this.events.add(event);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Day)) return false;

        Day day1 = (Day) o;

        if (day != day1.day) return false;
        if (year != day1.year) return false;
        return month == day1.month;

    }

    @Override
    public int hashCode()
    {
        int result = day;
        result = 31 * result + month.hashCode();
        result = 31 * result + year;
        result = 31 * result + weekday.hashCode();
        return result;
    }

    @Override
    public int compareTo(Day o)
    {
        if (year == o.year)
        {
            if (month == o.month)
            {
                return day - o.day;
            }
            return month.getDigit() - o.getMonth().getDigit();
        }
        return year - o.year;
    }
}
