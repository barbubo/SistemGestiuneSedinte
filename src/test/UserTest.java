package test;
import app.*;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    public ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    public PrintStream originalOut = System.out;

    @Test
    public void testInviteParticipant() {
        System.setOut(new PrintStream(outContent));
        User user1 = new User("1", "Barbu Andrei", "barbuandrei@gmail.com");
        User user2 = new User("2", "Marin Andreea", "marinandrea@gmail.com");
        User user3 = new User("3", "Cighir David", "cighirdavid@gmail.com");
        User user4 = new User("4", "Kodsk Asdfni", "fdasfdsasdc@gmail.com");
        Room room1 = new Room("1", "Amfiteatru", 3);
        user1.createMeeting("1", "Primul Meeting", new Date(124, 4, 25), new Time(12, 0, 0),
                new Time(13, 20, 0), room1);
        Meeting meeting1 = user1.getMeetings().get(0);
        user1.inviteParticipant(meeting1, user2);
        user1.inviteParticipant(meeting1, user3);
        //Invita participant cand nu mai este loc in sala
        user1.inviteParticipant(meeting1, user4);
        String expectedOutput = "You can't invite people anymore. Room is full!" +  System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        //Invita participant cand user-ul nu este administratorul meeting-ului
        user2.inviteParticipant(meeting1, user3);
        expectedOutput += "You can't invite participants to this meeting!" +  System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        //Invita participant cand acesta a fost deja invitat
        user3.acceptMeeting(meeting1);
        user1.removeParticipant(meeting1,user3);
        user1.inviteParticipant(meeting1, user2);
        expectedOutput += "Participant already invited" +  System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testRemoveParticipant() {
        System.setOut(new PrintStream(outContent));
        User user1 = new User("1", "Barbu Andrei", "barbuandrei@gmail.com");
        User user2 = new User("2", "Marin Andreea", "marinandrea@gmail.com");
        User user3 = new User("3", "Cighir David", "cighirdavid@gmail.com");
        User user4 = new User("4", "Kodsk Asdfni", "fdasfdsasdc@gmail.com");
        Room room1 = new Room("1", "Amfiteatru", 3);
        user1.createMeeting("1", "Primul Meeting", new Date(124, 4, 25), new Time(12, 0, 0),
                new Time(13, 20, 0), room1);
        Meeting meeting1 = user1.getMeetings().get(0);
        user1.inviteParticipant(meeting1,user2);
        user1.inviteParticipant(meeting1,user3);
        user2.acceptMeeting(meeting1);
        user3.acceptMeeting(meeting1);
        //Elimina un participant care nu exista in meeting
        user1.removeParticipant(meeting1,user4);
        String expectedOutput = "Participant does not exist in this meeting" +  System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        //Elimina un participant cand user-ul nu este administratorul meeting-ului
        user2.removeParticipant(meeting1,user3);
        expectedOutput += "You can't remove participants from this meeting!" +  System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}