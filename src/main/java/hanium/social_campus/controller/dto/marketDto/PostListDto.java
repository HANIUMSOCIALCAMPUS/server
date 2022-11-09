package hanium.social_campus.controller.dto.marketDto;

import hanium.social_campus.domain.market.Post;
import lombok.Data;

@Data
public class PostListDto {

    private Long id;
    private String title;

    private String dealType;

    private String status;

    private String sellerName;

    private String sellerSex;

    public PostListDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        dealType = post.getDealType().getValue();
        status = post.getStatus().getValue();
        sellerName = post.getMember().getNickname();
        sellerSex = post.getMember().getSex();
    }
}
