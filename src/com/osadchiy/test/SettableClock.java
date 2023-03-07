package com.osadchiy.test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class SettableClock extends Clock {
    private Instant now;

    public SettableClock(Instant now) {
        this.now = now;
    }

    @Override
    public ZoneId getZone() {
        return null;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return null;
    }

    @Override
    public Instant instant() {
        return now;
    }


    public void setClock(Instant now) {
        this.now = now;
    }
}
