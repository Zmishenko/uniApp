package uni.dubna.app.ui.event;

public class Event {

    private String eventName;
    private String creatorName;
    private String creatorEmail;
    private String creatorMessage;

    public Event(String eventName, String creatorName, String creatorEmail, String creatorMessage) {
        this.eventName = eventName;
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.creatorMessage = creatorMessage;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getCreatorMessage() {
        return creatorMessage;
    }

    public void setCreatorMessage(String creatorMessage) {
        this.creatorMessage = creatorMessage;
    }
}
