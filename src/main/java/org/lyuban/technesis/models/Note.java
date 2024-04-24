package org.lyuban.technesis.models;

import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.Objects;

public class Note {
    private long id;
    private String header;
    private LocalDateTime dateTime;
    private StringBuilder noteText;
    private static long counter = 0;

    public Note(String header, LocalDateTime dateTime, StringBuilder noteText) {
        this.header = header;
        this.dateTime = dateTime;
        this.noteText = noteText;
        this.id = counter;
        counter++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public StringBuilder getNoteText() {
        return noteText;
    }

    public void setNoteText(StringBuilder noteText) {
        this.noteText = noteText;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(header, note.header) && Objects.equals(dateTime, note.dateTime) && Objects.equals(noteText, note.noteText) && Objects.equals(counter, note.counter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header, dateTime, noteText, counter);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", dateTime=" + dateTime +
                ", noteText=" + noteText +
                ", counter=" + counter +
                '}';
    }
}
