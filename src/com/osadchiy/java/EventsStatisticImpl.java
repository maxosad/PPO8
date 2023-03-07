package com.osadchiy.java;

import java.time.Instant;
import java.time.Clock;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class EventsStatisticImpl implements EventsStatistic{
    private Map<String, ArrayList<Instant>> events = new HashMap<String, ArrayList<Instant>>();
    private final Clock clock;

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        events.putIfAbsent(name, new ArrayList<Instant>());
        events.get(name).add(clock.instant());
    }

    @Override
    public double getEventStatisticByName(String name) {
        deleteOld();
        if (!events.containsKey(name)) {
            return 0;
        }
        return _getEventStatisticByName(name);
    }
    private double _getEventStatisticByName(String name) {
        return events.get(name).size() / 60.0;
    }

    public void deleteOld() {
        Instant hourAgoInstant = clock.instant().minus(1, ChronoUnit.HOURS);

        for (String name : events.keySet()) {
            ArrayList<Instant> newInstant = new ArrayList<>();
            for(Instant eventTime : events.get(name)) {
                if (eventTime.isAfter(hourAgoInstant)) {
                    newInstant.add(eventTime);
                }
            }
            if (newInstant.size() == 0) {
                events.remove(name);
            } else {
                events.put(name, newInstant);
            }
        }
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        deleteOld();
        Map<String, Double> ans = new HashMap<>();
        for (String eventKey : events.keySet()) {
            ans.put(eventKey, _getEventStatisticByName(eventKey));
        }
        return ans;
    }

    @Override
    public void printStatistic() {
        getAllEventStatistic().forEach((name, rpm) -> System.out.println(name + ": " + rpm + " rpm"));
    }
}
