import app.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        User user1 = new User("1", "Barbu Andrei", "barbuandrei@gmail.com");
        User user2 = new User("2", "Marin Andreea", "marinandrea@gmail.com");
        User user3 = new User("3", "Cighir David", "cighirdavid@gmail.com");
        Room room1 = new Room("1", "Amfiteatru", 2);
        Room room2 = new Room("2", "Aula", 2);
        user1.createMeeting("1", "Primul Meeting", new Date(124, 4, 25), new Time(12, 0, 0),
                new Time(13, 20, 0), room1);
        Meeting meeting1 = user1.getMeetings().get(0);
        user1.inviteParticipant(meeting1, user2);
        user1.inviteParticipant(meeting1, user3);
    }
}