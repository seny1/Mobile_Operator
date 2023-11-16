package org.elSasen.dao;

import org.elSasen.entities.ExtraService;
import org.elSasen.entities.Post;
import org.elSasen.entities.ServiceCategory;
import org.elSasen.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostDao {
    private static final PostDao INSTANCE = new PostDao();
    public Set<Post> getPostTable() {
        String sql = """
                SELECT *
                FROM post
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var postSet = new HashSet<Post>();
            Post post;
            while (resultSet.next()) {
                post = Post.builder()
                        .postId(resultSet.getInt("post_id"))
                        .postName(resultSet.getString("post_name"))
                        .postDescription(resultSet.getString("post_description"))
                        .build();
                postSet.add(post);
            }
            return postSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMetaData() {
        String sql = """
                SELECT *
                FROM post
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var columnNames = new ArrayList<String>();
            for (int i = 1; i < resultSet.getMetaData().getColumnCount() + 1; i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }
            return columnNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PostDao getInstance() {
        return INSTANCE;
    }
}
