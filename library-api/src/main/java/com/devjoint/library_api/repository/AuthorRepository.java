package com.devjoint.library_api.repository;
import com.devjoint.library_api.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuthorRepository extends JpaRepository<Author,Long>{
}
