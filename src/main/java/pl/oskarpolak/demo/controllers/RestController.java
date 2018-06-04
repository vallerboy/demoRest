package pl.oskarpolak.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.oskarpolak.demo.model.BookEntity;
import pl.oskarpolak.demo.model.Config;
import pl.oskarpolak.demo.model.repositories.BookRepository;

import java.util.Optional;

@Controller
public class RestController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping(value = "/book/{id}", produces = "application/json")
    public ResponseEntity getBook(@PathVariable("id") int id){
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        return bookEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(value = "/book", produces = "application/json")
    public ResponseEntity getAllBooks(){
        return ResponseEntity.ok(bookRepository.findAll());
    }



    @PostMapping(value = "/book", consumes = "application/json")
    public ResponseEntity createBook(@RequestHeader("key") String key, @RequestBody BookEntity bookEntity){
        if (checkKey(key)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if(bookRepository.existsByTitle(bookEntity.getTitle())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        bookRepository.save(bookEntity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/book/{id}")
    public ResponseEntity deleteBook(@RequestHeader("key") String key, @PathVariable("id") int id){
        if (checkKey(key)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }



    @PutMapping(value = "/book", consumes = "application/json")
    public ResponseEntity editBook(@RequestHeader("key") String key, @RequestBody BookEntity bookEntity){
        if (checkKey(key)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if(!bookRepository.existsById(bookEntity.getId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        bookRepository.save(bookEntity);
        return ResponseEntity.ok().build();
    }

    private boolean checkKey(String key) {
        if(!key.equals(Config.API_KEY)){
            return true;
        }
        return false;
    }
}
