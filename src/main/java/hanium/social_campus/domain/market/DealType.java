package hanium.social_campus.domain.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import hanium.social_campus.domain.group.ClubType;
import lombok.Getter;

@Getter
public enum DealType {
    TRADE("trade"), RENTAL("rental"), SHARE("share");

    private final String value;

    private DealType(String value) {
        this.value = value;
    }


    public static DealType from(String value) {
        for (DealType type : DealType.values()) {
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
