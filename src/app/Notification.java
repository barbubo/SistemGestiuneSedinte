package app;

import java.util.ArrayList;

public class Notification {
    private String notificationID;
    private Meeting meeting;
    private String message;
    private User recipient;

    public Notification(String notificationID, Meeting meeting, String message, User recipient) {
        this.notificationID = notificationID;
        this.meeting = meeting;
        this.message = message;
        this.recipient = recipient;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void send() {
        ArrayList<Notification> notifications = this.recipient.getNotifications();
        notifications.add(this);
        this.recipient.setNotifications(notifications);
    }

    public void printNotification() {
        System.out.println(this.message);
        this.meeting.printMeeting();
    }
}
