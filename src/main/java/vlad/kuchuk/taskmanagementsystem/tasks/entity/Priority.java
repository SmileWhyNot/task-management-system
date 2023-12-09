package vlad.kuchuk.taskmanagementsystem.tasks.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Priority {
    HIGH("high"), MEDIUM("medium"), LOW("low");

    private final String value;

    Priority(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Priority fromValue(String value) {
        for (Priority priority : values()) {
            if (priority.value.equalsIgnoreCase(value)) {
                return priority;
            }
        }
        return LOW;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
