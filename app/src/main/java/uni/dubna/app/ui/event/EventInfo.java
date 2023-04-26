package uni.dubna.app.ui.event;

import java.util.Date;

public class EventInfo {

    private EventType eventType;
    private String subject;
    private String description;
    private String whoIsReplaced;
    private String date;

    public EventInfo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventInfo(EventType eventType, String subject, String whoIsReplaced, String date, String description) {
        this.eventType = eventType;
        this.subject = subject;
        this.whoIsReplaced = whoIsReplaced;
        this.date = date;
        this.description = description;
    }

    public EventInfo() {

    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getWhoIsReplaced() {
        return whoIsReplaced;
    }

    public void setWhoIsReplaced(String whoIsReplaced) {
        this.whoIsReplaced = whoIsReplaced;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
