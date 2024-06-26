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

    //adauga meeting in program
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    //sterge un meeting din program
    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    //afiseaza meeting-urile din program
    public void viewMeetings() {
        for (Meeting meeting : this.meetings) {
            System.out.println(meeting.getTitle() + "," + meeting.getRoom().getName());
            System.out.print(meeting.getDate().getYear() + 1900 + "-");
            System.out.println(meeting.getDate().getMonth() + 1 + "-" + meeting.getDate().getDate() + ","
                    + meeting.getStartTime() + " - " + meeting.getEndTime());
        }
    }
}
