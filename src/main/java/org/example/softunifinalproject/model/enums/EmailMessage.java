package org.example.softunifinalproject.model.enums;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum EmailMessage {
    APPROVED_CONSULTATION(
            "Consultation accepted",
            "Your consultation for the following date has been approved: %s"
    ),
    REFUSED_CONSULTATION(
            "Consultation refused",
            "Your consultation for the following date has been refused: %s"
    );

    private final String subject;
    private final String text;

    EmailMessage(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public String getText(String date) {
        return String.format(text, date);
    }

    public String getTextFormatted(LocalDateTime date) {
        return String.format(text, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
