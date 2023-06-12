package uni.dubna.app.ui.event;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Event {

    @SerializedName("id")
    private Long id;
    @SerializedName("type")
    private EventType eventType;
    @SerializedName("fio")
    private String teacherName;
    @SerializedName("grpupsNum")
    private String group;

    @SerializedName("reason")
    private String reason;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SerializedName("dateFrom")
    private String dateFrom;

    @SerializedName("dateTo")
    private String dateTo;

    @SerializedName("predmet")
    private String subject;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Event(EventType eventName, String teacherName) {
        this.teacherName = teacherName;
    }

    public Event(){}


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Event) {
            return Objects.equals(id, ((Event) obj).id) && Objects.equals(eventType, ((Event) obj).getEventType()) && Objects.equals(teacherName, ((Event) obj).getTeacherName()) &&
                    Objects.equals(group, ((Event) obj).getGroup()) && Objects.equals(reason, ((Event) obj).getReason()) && Objects.equals(subject, ((Event) obj).getSubject());
        }

        return false;
    }

    public Event copy() {
        Event event = new Event();
        event.setGroup(group);
        event.setEventType(eventType);
        event.setSubject(subject);
        event.setReason(reason);
        event.setTeacherName(teacherName);
        event.setDateTo(dateTo);
        event.setDateFrom(dateFrom);
        return event;
    }
}
