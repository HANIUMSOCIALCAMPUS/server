package hanium.social_campus.controller.dto.marketDto;

import hanium.social_campus.domain.market.Post;
import hanium.social_campus.domain.market.PostImage;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostDetailDto {

    private String title;

    private String description;

    private int price;

    private String nickname;

    private String dealType;

    private String status;

    private List<String> postImageUrls;

    public PostDetailDto(Post post) {
        title = post.getTitle();
        description = post.getDescription();
        price = post.getPrice();
        nickname = post.getMember().getNickname();
        dealType = post.getDealType().getValue();
        status = post.getStatus().getValue();
        postImageUrls = post.getPostImages().stream().map(PostImage::getUrl).collect(Collectors.toList());
    }
}
