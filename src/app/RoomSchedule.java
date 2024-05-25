package app;

import java.util.ArrayList;

public class RoomSchedule {
    private String scheduleID;
    private Room room;
    private ArrayList<Meeting> meetings;

    public RoomSchedule(String scheduleID, Room room) {
        this.scheduleID = scheduleID;
        this.room = room;
        this.meetings = new ArrayList<Meeting>();
    }

    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }
}
