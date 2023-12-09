package vlad.kuchuk.taskmanagementsystem.tasks.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    PENDING("pending"), IN_PROGRESS("in_progress"), COMPLETED("completed");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Status fromValue(String value) {
        for (Status status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return PENDING;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
