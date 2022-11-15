package hanium.social_campus.controller.dto.marketDto;

import hanium.social_campus.domain.market.Post;
import lombok.Data;

@Data
public class PostListDto {

    private Long id;

    private String title;

    private int price;

    private String status;

    private String nickname;

    // 대표이미지
    private String postImageUrl;

    public PostListDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        price = post.getPrice();
        status = post.getStatus().getValue();
        nickname = post.getMember().getNickname();
        postImageUrl = post.getPostImages().get(0).getUrl();
    }
}
