package hanium.social_campus.controller.dto.marketDto;

import hanium.social_campus.domain.market.DealType;
import lombok.Data;

import java.util.List;

@Data
public class PostCreateDto {

    private String title;

    private String description;

    private int price;

    private String dealType;

}
