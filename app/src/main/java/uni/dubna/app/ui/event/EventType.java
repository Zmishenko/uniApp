package uni.dubna.app.ui.event;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public enum EventType {
    @SerializedName("Замена") REPLACEMENT, @SerializedName("Перенос") SHIFT, @SerializedName("Перенос с заменой") REPLACEMENT_SHIFT,
    ;

    @NonNull
    @Override
    public String toString() {
        switch (this) {
            case REPLACEMENT:
                return "Замена";
            case SHIFT:
                return "Перенос";
            case REPLACEMENT_SHIFT:
                return "Перенос с заменой";
        }
        throw new RuntimeException("impossible state");
    }

    public static EventType fromString(String eventType) {
        if (eventType.contains("Замена")) return EventType.REPLACEMENT;
        if (eventType.contains("Перенос")) return EventType.SHIFT;
        if (eventType.contains("Перенос с заменой")) return EventType.REPLACEMENT_SHIFT;
        throw new RuntimeException("Unknown event type");
    }

    public static boolean contains(String eventType) {
        return eventType.contains(EventType.SHIFT.toString()) ||
                eventType.contains(EventType.REPLACEMENT.toString()) ||
                eventType.contains(EventType.REPLACEMENT_SHIFT.toString());
    }
}
