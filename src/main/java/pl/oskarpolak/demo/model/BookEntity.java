package pl.oskarpolak.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@Data
public class BookEntity {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String author;
    private int pages;
}
