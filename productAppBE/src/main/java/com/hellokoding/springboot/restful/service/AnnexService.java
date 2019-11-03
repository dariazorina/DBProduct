package com.hellokoding.springboot.restful.service;

import com.hellokoding.springboot.restful.dao.*;
import com.hellokoding.springboot.restful.model.Annex;
import com.hellokoding.springboot.restful.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AnnexService {

    private final AnnexRepository annexRepository;
    private final EventRepository eventRepository;

    public List<Annex> findAll() {
        return annexRepository.findAll();
    }

    public Optional<Annex> findById(Long id) {
        return annexRepository.findById(Math.toIntExact(id));
    }

    public Annex save(Annex stock) {
        return annexRepository.save(stock);
    }

    public void deleteById(Long id) {
        annexRepository.deleteById(Math.toIntExact(id));
    }


    public void fillAnnexTableFromEvent() {                 //// fill table annex
        List<Event> all = eventRepository.findAll();

//        for (Article article : all) {
//            String author = article.getAuthor();
//            if (author != null) {
//                author = author.substring(1, author.length() - 1); //убираем { }
//                String[] split = author.split(","); //разделяем по "," на массив строк
//
//                for (String authorNameWithQuotes : split) {
//                    String authorName = authorNameWithQuotes.substring(1, authorNameWithQuotes.length() - 1);//del "
//                    Author authorByName = authorRepository.getAuthorByName(authorName); //ищем автора в БД
//                    if (authorByName == null) {
//                        Author s1 = new Author();
//                        s1.setName(authorName);
//                        authorRepository.save(s1);
//                    }
//                }
//            }
//        }
    }
    public void initializeReferenceBetweenAnnexAndEvent(){

    }
}
