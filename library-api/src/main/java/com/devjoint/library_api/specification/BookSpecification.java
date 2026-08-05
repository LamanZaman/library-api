package com.devjoint.library_api.specification;

import com.devjoint.library_api.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) ->
                title == null ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthorName(String authorName) {
        return (root, query, cb) ->
                authorName == null ? null : cb.like(cb.lower(root.join("author").get("fullName")), "%" + authorName.toLowerCase() + "%");
    }

    public static Specification<Book> hasCategory(String categoryName) {
        return (root, query, cb) -> {
            if (categoryName == null) return null;
            query.distinct(true);
            return cb.like(cb.lower(root.join("categories").get("name")), "%" + categoryName.toLowerCase() + "%");
        };
    }
}