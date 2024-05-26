package app;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class User {
    private String userID;
    private String name;
    private String email;
    private ArrayList<Notification> notifications;
    private UserSchedule schedule;
    private ArrayList<Meeting> meetings;
    private ArrayList<Meeting> meetingInvitations;

    public User(String userID, String name, String email) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.notifications = new ArrayList<Notification>();
        this.schedule = new UserSchedule(userID + 's', this);
        this.meetings = new ArrayList<Meeting>();
        this.meetingInvitations = new ArrayList<Meeting>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Meeting> getMeetings() {
        return meetings;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<Meeting> getMeetingInvitations() {
        return this.meetingInvitations;
    }

    public void setMeetingInvitations(ArrayList<Meeting> meetingInvitations) {
        this.meetingInvitations = meetingInvitations;
    }

    public UserSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(UserSchedule schedule) {
        this.schedule = schedule;
    }

    //creeaza meeting
    public void createMeeting(String meetingID, String title, Date date, Time startTime, Time endTime, Room room) {
        //verifica daca mai exista un meeting in intervalul de timp dorit la care user-ul deja este participant
        boolean meetInTheSameInterval = false;
        for (Meeting m : this.schedule.getMeetings()) {
            if (date.getYear() == m.getDate().getYear() && date.getMonth() == m.getDate().getMonth() && date.getDate() == m.getDate().getDate() && (startTime.compareTo(m.getStartTime()) >= 0 && startTime.compareTo(m.getEndTime()) <= 0 || endTime.compareTo(m.getStartTime()) >= 0 && endTime.compareTo(m.getEndTime()) <= 0)) {
                meetInTheSameInterval = true;
                break;
            }
        }
        if (!meetInTheSameInterval) { //daca nu mai exista
            if (room.checkAvailability(date, startTime, endTime)) { //verifica daca sala este disponibila
                Meeting meeting = new Meeting(meetingID, title, date, startTime, endTime, this, room);
                meetings.add(meeting);
                room.bookRoom(meeting);
                this.schedule.addMeeting(meeting);
            } else {
                System.out.println("Room not available! Meet not created");
            }
        } else {
            System.out.println("You already have a meet in that interval!");
        }
    }

    //sterge meeting
    public void deleteMeeting(Meeting meeting) {
        if (this.meetings.contains(meeting)) {//verifica daca user-ul a creat meeting-ul
            for (int i = 1; i <= meeting.getParticipants().size(); i++) {
                meeting.removeParticipant(meeting.getParticipants().get(i));
            }
            meeting.removeParticipant(this);
            meetings.remove(meeting);
            Room room = meeting.getRoom();
            room.releaseRoom(meeting);
        } else {
            System.out.println("You can't delete this meeting");
        }
    }

    //accepta un meeting la care a fost invitat
    public void acceptMeeting(Meeting meeting) {
        if (this.meetingInvitations.contains(meeting)) {//verifica daca user-ul este invitat la meeting
            //verifica daca mai exista un meeting in intervalul de timp dorit la care user-ul deja este participant
            boolean meetInTheSameInterval = false;
            for (Meeting m : this.schedule.getMeetings()) {
                if (meeting.getDate().getYear() == m.getDate().getYear() && meeting.getDate().getMonth() == m.getDate().getMonth() && meeting.getDate().getDate() == m.getDate().getDate() && (meeting.getStartTime().compareTo(m.getStartTime()) >= 0 && meeting.getStartTime().compareTo(m.getEndTime()) <= 0 || meeting.getEndTime().compareTo(m.getStartTime()) >= 0 && meeting.getEndTime().compareTo(m.getEndTime()) <= 0)) {
                    meetInTheSameInterval = true;
                    break;
                }
            }
            if (!meetInTheSameInterval) {
                this.schedule.addMeeting(meeting);
                meeting.addParticipant(this);
                this.meetingInvitations.remove(meeting);
            } else {
                System.out.println("You already have a meet in this interval!");
            }
        } else {
            System.out.println("You were not invited to this meeting!");
        }
    }

    //elimina un participant din meeting
    public void removeParticipant(Meeting meeting, User participant) {
        if (this.meetings.contains(meeting)) {//verifica daca user-ul a creat meeting-ul
            //verifca daca user-ul care trebuie eliminat este participant al meeting-ului
            if (meeting.getParticipants().contains(participant)) meeting.removeParticipant(participant);
            else System.out.println("Participant does not exist in this meeting");
        } else {
            System.out.println("You can't remove participants from this meeting!");
        }
    }

    //invita un participant la meeting
    public void inviteParticipant(Meeting meeting, User participant) {
        if (this.meetings.contains(meeting)) {//verifica daca user-ul a creat meeting-ul
            //verifica daca mai exista loc in sala meeting-ului
            if (meeting.getRoom().getCapacity() > meeting.getInvitedParticipants().size() + 1) { //verifica daca participantul a fost deja invitat
                if(!meeting.getInvitedParticipants().contains(participant)&&participant!=this) {
                    Notification notification = new Notification(meeting.getMeetingID() + participant.getUserID() + 'a', meeting, "You were invited to this meeting", participant);
                    notification.send();
                    ArrayList<Meeting> participantMeetings = participant.getMeetingInvitations();
                    participantMeetings.add(meeting);
                    participant.setMeetingInvitations(participantMeetings);
                    meeting.addInvitedParticipant(participant);
                }
                else System.out.println("Participant already invited");
            } else System.out.println("You can't invite people anymore. Room is full!");
        } else System.out.println("You can't invite participants to this meeting!");
    }

    //afiseaza programul de meeting-uri
    public void viewSchedule() {
        System.out.println("------SCHEDULE-------");
        this.schedule.viewMeetings();
    }

    //afiseaza programul unei sali
    public void viewRoomSchedule(Room room) {
        System.out.println("------ROOM SCHEDULE-------");
        room.viewSchedule();
    }

    //afiseaza toate notificarile
    public void viewNotifications() {
        System.out.println("------NOTIFICATIONS-------");
        for (Notification notification : notifications) {
            notification.printNotification();
        }
    }

    //afiseaza id-urile meeting-urilor la care este invitat
    public void viewMeetingInvitations() {
        System.out.println("------MEETING INVITATIONS-------");
        for (Meeting meetingInvitation : meetingInvitations) {
            System.out.print(meetingInvitation.getMeetingID());
        }
    }
}
