package com.devjoint.library_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String fullName;
   private String nationality;
   @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
   private List<Book> books;



}
