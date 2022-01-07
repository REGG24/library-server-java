package com.regg.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_book;
    @NotEmpty(message = "Name cannot be empty or null")
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long id_author;
}
