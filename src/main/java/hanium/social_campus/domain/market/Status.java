package hanium.social_campus.domain.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;


public enum Status {
    ING("ing"), END("end"), DELETE("delete");

    private final String value;

    private Status(String value) {
        this.value = value;
    }


    public static Status from(String value) {
        for (Status type : Status.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }


    public String getValue() {
        return value;
    }
}
