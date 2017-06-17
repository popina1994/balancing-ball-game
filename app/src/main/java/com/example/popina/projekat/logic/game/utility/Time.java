package com.example.popina.projekat.logic.game.utility;

/**
 * Created by popina on 04.04.2017..
 */

public class Time
{
    private long begin;
    private long end;

    public Time(long begin, long end)
    {
        this.begin = begin;
        this.end = end;
    }

    public Time()
    {
    }

    public Time(long begin)
    {
        this.begin = begin;
    }

    public long getBegin()
    {
        return begin;
    }

    public void setBegin(long begin)
    {
        this.begin = begin;
    }

    public long getEnd()
    {
        return end;
    }

    public void setEnd(long end)
    {
        this.end = end;
    }

    public long timeInt()
    {
        return end - begin;
    }
}
