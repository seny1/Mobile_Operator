package org.elSasen.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Post {
    int postId;
    String postName;
    String postDescription;
}
