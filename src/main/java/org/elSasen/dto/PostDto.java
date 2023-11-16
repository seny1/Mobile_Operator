package org.elSasen.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PostDto {
    int postId;
    String postName;
    String postDescription;
}
