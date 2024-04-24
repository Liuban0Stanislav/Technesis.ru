package org.lyuban.technesis.DataBaceConnection;

import org.lyuban.technesis.models.Note;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static List<Note> noteStore = new ArrayList<>();

    public static List<Note> getNoteStore() {
        return noteStore;
    }

    public static void addSomeNotes() {
        noteStore.add(new Note(
                "Заявка №1",
                LocalDateTime.now().minusDays(3).minusHours(2),
                new StringBuilder("Текст заявки 1")));
        noteStore.add(new Note(
                "Заявка №2",
                LocalDateTime.now().minusDays(5).minusHours(7),
                new StringBuilder("Текст заявки 2")));
        noteStore.add(new Note(
                "Заявка №3",
                LocalDateTime.now().minusDays(7).minusHours(12),
                new StringBuilder("Текст заявки 3")));
        noteStore.add(new Note(
                "Заявка №4",
                LocalDateTime.now().minusDays(8).minusHours(13),
                new StringBuilder("Текст заявки 4")));
        noteStore.add(new Note(
                "Заявка №5",
                LocalDateTime.now().minusDays(6).minusHours(15),
                new StringBuilder("Текст заявки 5")));
        noteStore.add(new Note(
                "Заявка №6",
                LocalDateTime.now().minusDays(3).minusHours(2),
                new StringBuilder("Текст заявки 6")));
         noteStore.add(new Note(
                "Заявка №7",
                LocalDateTime.now().minusDays(1).minusHours(3),
                new StringBuilder("Текст заявки 7")));
         noteStore.add(new Note(
                "Заявка №8",
                LocalDateTime.now().minusDays(3).minusHours(5),
                new StringBuilder("Текст заявки 8")));
         noteStore.add(new Note(
                "Заявка №9",
                LocalDateTime.now().minusDays(2).minusHours(14),
                new StringBuilder("Текст заявки 9")));

    }
}
