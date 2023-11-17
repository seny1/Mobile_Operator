package org.elSasen.service;

import org.elSasen.dao.ExtraServiceDao;
import org.elSasen.dao.PostDao;
import org.elSasen.dto.OrderDto;
import org.elSasen.dto.PostDto;
import org.elSasen.mapper.ExtraServiceMapper;
import org.elSasen.mapper.PostMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PostService {
    private static final PostService INSTANCE = new PostService();
    private final PostDao postDao = PostDao.getInstance();
    private final PostMapper postMapper = PostMapper.getInstance();

    public List<PostDto> getPostTable(String orderBy) {
        var postTable = postDao.getPostTable(orderBy);
        return postTable.stream()
                .map(postMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfPost() {
        return postDao.getMetaData();
    }

    public static PostService getInstance() {
        return INSTANCE;
    }

}
