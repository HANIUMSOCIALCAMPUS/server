package hanium.social_campus.domain.group;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ClubType {
    STUDY("study"), HOBBY("hobby"), FRIEND("friend");

    private final String value;

    ClubType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ClubType from(String value) {
        for (ClubType type : ClubType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
