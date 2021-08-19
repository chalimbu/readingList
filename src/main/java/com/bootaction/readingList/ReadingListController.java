package com.bootaction.readingList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//crear un  clase que tenga endpoint  /readingList  base que tenga 2 enpoints
// 1 get /<readername> coloca los libros en el model y retorna el string readingList
// 2 post /<readername> con book guarda el libro en db local y retorna el string "redirect:/readingList/{reader}"
//probar llendo a la pagina http://localhost:8080/readingList/sebas y agregando libros
@Controller
@RequestMapping("/readingList")
public class ReadingListController {

    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository){
        this.readingListRepository=readingListRepository;
    }

    @RequestMapping(value="/{reader}",method = RequestMethod.GET)
    public String readerBooks(
            @PathVariable("reader") String reader,
            Model model
    ){
        List<Book> readingList = readingListRepository.findByReader(reader);
        if(readingList!=null){
            model.addAttribute("books",readingList);
        }
        return "readingList";
    }

    @RequestMapping(value="/{reader}", method = RequestMethod.POST)
    public String addToReadingList(
            @PathVariable("reader") String reader, Book book){
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/readingList/{reader}";
    }

}
