package lib.controllers;

import lib.models.Person;
import lib.models.Book;
import lib.services.BookService;
import lib.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    //index (shows all books)
    @GetMapping()
    public String index(Model model, @RequestParam(value="page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value="sorted_by_year", required = false) boolean sortedByYear) {
        if (page==null || booksPerPage==null) {
            model.addAttribute("books", bookService.findAll(sortedByYear));
        }
        else {
            model.addAttribute("books", bookService.findByPages(page, booksPerPage,sortedByYear));
        }
        return "books/index";
    }

    //show (shows info about one book for its id)
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Person bookOwner = bookService.getBookOwner(id);
        if (bookOwner!=null) {
            model.addAttribute("owner", bookOwner);
        }
        else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }
    //delete (deletes one book for its id)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        bookService.delete(id);
        return "redirect:/books";
    }
    //update (updates full list of books)
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }
    //create (creates new book from main page)
    @PostMapping
    public String create(@ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/new_book";

        return "redirect:/books";
    }
    //new (returns form for new book for get request)
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new_book";
    }

    //assign (assigns book to some person)
    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        bookService.assign(id, person);
        return "redirect:/books/" + id;
    }
     //release (releases book from some person)
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }
    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.searchByTitle(query));
        return "books/search";
    }
}
