package org.elSasen.mapper;

import org.elSasen.dto.select.PostDto;
import org.elSasen.entities.Post;

public class PostMapper implements Mapper<Post, PostDto> {

    @Override
    public PostDto mapFrom(Post post) {
        return PostDto.builder()
                .postId(post.getPostId())
                .postName(post.getPostName())
                .postDescription(post.getPostDescription())
                .build();
    }
    private static final PostMapper INSTANCE = new PostMapper();
    public static PostMapper getInstance() {
        return INSTANCE;
    }
}
