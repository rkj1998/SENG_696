package org.example;

import java.time.LocalDateTime;

public class TimeSlot {
    //fields
    private LocalDateTime startingDateTime;
    private Integer duration; //it's minute
    private Boolean reserved;

    public TimeSlot(LocalDateTime startingDateTime, Integer duration, Boolean reserved) {
        this.startingDateTime = startingDateTime;
        this.duration = duration;
        this.reserved = reserved;
    }

    public LocalDateTime getStartingDateTime() {
        return startingDateTime;
    }

    public void setStartingDateTime(LocalDateTime startingDateTime) {
        this.startingDateTime = startingDateTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }
}
