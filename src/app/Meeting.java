package app;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Meeting {
    private String meetingID;
    private String title;
    private Date date;
    private Time startTime;
    private Time endTime;
    private User organizer;
    private ArrayList<User> participants;
    private Room room;
    public Meeting(String meetingID, String title, Date date, Time startTime, Time endTime, User organizer, Room room) {
        this.meetingID = meetingID;
        this.title = title;
        this.date = new Date(date.getYear(), date.getMonth(), date.getDate(), startTime.getHours(), startTime.getMinutes());
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
        this.room = room;
        this.participants = new ArrayList<User>();
        this.participants.add(organizer);
    }
    public String getMeetingID() {
        return meetingID;
    }
    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public Time getStartTime() {
        return startTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    public Time getEndTime() {
        return endTime;
    }
    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }
    public User getOrganizer() {
        return organizer;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public ArrayList<User> getParticipants() {
        return participants;
    }
    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }
    public void addParticipant(User participant) {
        this.participants.add(participant);
    }
    public void removeParticipant(User participant) {
//        this.participants.remove(participant);
        Notification notification = new Notification(this.meetingID+participant.getUserID()+'r', this,
                "You were removed from this meeting", participant);
        UserSchedule schedule = participant.getSchedule();
        schedule.removeMeeting(this);
        participant.setSchedule(schedule);
        notification.send();
        this.participants.remove(participant);
    }
    public void deleteAllParticipants(){
        for(User participant : participants){
            this.removeParticipant(participant);
        }
    }
    public void printMeeting(){
        System.out.println("Meeting ID: " + meetingID);
        System.out.println("Title: " + title);
        System.out.println("Date: " + date);
        System.out.println("Start: " + startTime);
        System.out.println("End: " + endTime);
        System.out.println("Organizer: " + organizer.getName());
        System.out.print("Participants: ");
        for(User participant : participants){
            System.out.print(participant.getName() + ",");
        }
        System.out.println();
        System.out.println("Room: " + room.getName());
    }
}
