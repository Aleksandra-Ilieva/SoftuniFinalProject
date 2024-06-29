package org.example.softunifinalproject.model.dto.profileDto;

import java.time.LocalDateTime;
import java.util.List;

public class BusySlotsDto {

    private List<LocalDateTime> busySlots;

    public List<LocalDateTime> getBusySlots() {
        return busySlots;
    }

    public void setBusySlots(List<LocalDateTime> busySlots) {
        this.busySlots = busySlots;
    }
}
