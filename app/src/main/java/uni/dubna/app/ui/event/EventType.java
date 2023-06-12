package uni.dubna.app.ui.event;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public enum EventType {
    @SerializedName("Замена") REPLACEMENT,@SerializedName("Перенос") SHIFT,@SerializedName("Перенос с заменой") REPLACEMENT_SHIFT,
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
        if (eventType.equals("Замена")) return EventType.REPLACEMENT;
        if (eventType.equals("Перенос")) return EventType.SHIFT;
        if (eventType.equals("Перенос с заменой")) return EventType.REPLACEMENT_SHIFT;
        throw  new RuntimeException("Unknown event type");
    }
}
