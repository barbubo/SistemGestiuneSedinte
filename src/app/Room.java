package app;

import java.sql.Time;
import java.util.Date;

public class Room {
    private String roomID;
    private String name;
    private int capacity;
    private RoomSchedule schedule;

    public Room(String roomID, String name, int capacity) {
        this.roomID = roomID;
        this.name = name;
        this.capacity = capacity;
        this.schedule = new RoomSchedule(roomID + 's', this);
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean checkAvailability(Date date, Time startTime, Time endTime) {
        for (Meeting meeting : this.schedule.getMeetings()) {
            if (meeting.getDate().getYear() == date.getYear() && meeting.getDate().getMonth() == date.getMonth()
                    && meeting.getDate().getDate() == date.getDate()
                    && (meeting.getStartTime().compareTo(startTime) >= 0
                    && meeting.getStartTime().compareTo(endTime) <= 0
                    || meeting.getEndTime().compareTo(startTime) >= 0
                    && meeting.getEndTime().compareTo(endTime) <= 0))
                return false;
        }
        return true;
    }

    public void bookRoom(Meeting meeting) {
        this.schedule.addMeeting(meeting);
    }

    public void releaseRoom(Meeting meeting) {
        this.schedule.removeMeeting(meeting);
    }
}
