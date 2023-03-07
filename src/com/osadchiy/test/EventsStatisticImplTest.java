package com.osadchiy.test;

import com.osadchiy.java.EventsStatisticImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventsStatisticImplTest {
    SettableClock settableClock;
    EventsStatisticImpl eventsStatistic;
    private final double EPS = 0.00001;
    @Before
    public void setUp() {
        settableClock = new SettableClock(Instant.now());
        eventsStatistic = new EventsStatisticImpl(settableClock);
    }


    @Test
    public void noEvents() {
        assertTrue(eventsStatistic.getEventStatisticByName("A") < EPS);
    }

    @Test
    public void testStatisticByName() {
        eventsStatistic.incEvent("A");
        eventsStatistic.incEvent("A");
        eventsStatistic.incEvent("A");

        assertTrue(eventsStatistic.getEventStatisticByName("A") - 0.05 < EPS);
    }
    @Test
    public void unoOldEventTest() {
        eventsStatistic.incEvent("oldEvent");
        settableClock.setClock(Instant.now().plus(2, ChronoUnit.HOURS));
        assertTrue(eventsStatistic.getEventStatisticByName("A") < EPS);
    }

    @Test
    public void printStatistics() {
        eventsStatistic.incEvent("oldEvent");
        eventsStatistic.incEvent("A");
        eventsStatistic.incEvent("A");
        eventsStatistic.incEvent("B");
        eventsStatistic.incEvent("B");
        eventsStatistic.incEvent("B");

        eventsStatistic.printStatistic();
    }

}
