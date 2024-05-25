package app;

import java.util.ArrayList;

public class UserSchedule {
    private String scheduleID;
    private User user;
    private ArrayList<Meeting> meetings;
    public UserSchedule(String scheduleID, User user) {
        this.scheduleID = scheduleID;
        this.user = user;
        this.meetings = new ArrayList<Meeting>();
    }
    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }
    public void viewMeetings() {
        for (Meeting meeting : this.meetings) {
            System.out.println(meeting.getTitle() + "," +meeting.getRoom().getName());
            System.out.print(meeting.getDate().getYear()+1900 + "-");
            System.out.println(meeting.getDate().getMonth()+1 + "-" + meeting.getDate().getDate() + ","
                    + meeting.getStartTime() + " - " + meeting.getEndTime());
        }
    }
}
