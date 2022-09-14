package hanium.social_campus.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListReturnDto<T> {

    private T list;

}
