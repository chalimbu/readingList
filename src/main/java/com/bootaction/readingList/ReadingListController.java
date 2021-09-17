package com.bootaction.readingList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//crear un  clase que tenga endpoint  /readingList  base que tenga 2 enpoints
// 1 get /<readername> coloca los libros en el model y retorna el string readingList
// 2 post /<readername> con book guarda el libro en db local y retorna el string "redirect:/readingList/{reader}"
//probar llendo a la pagina http://localhost:8080/readingList/sebas y agregando libros
@Controller
@RequestMapping("/")
public class ReadingListController {

    private ReadingListRepository readingListRepository;
    private AmazonProperties amazonConfig;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository,
                                 AmazonProperties amazonConfig){
        this.readingListRepository=readingListRepository;
        this.amazonConfig = amazonConfig;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fail")
    public void fail(){
        throw new RuntimeException();
    }

    @ExceptionHandler(value=RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    public String error(){
        return "error";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String readerBooks(
            Reader reader,
            Model model
    ){
        List<Book> readingList = readingListRepository.findByReader(reader);
        if(readingList!=null){
            model.addAttribute("books",readingList);
            model.addAttribute("reader",reader);
            model.addAttribute("amazonID", amazonConfig.getAssociateId());
        }
        return "readingList";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addToReadingList(
            Reader reader,
            Book book){
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/";
    }

}
