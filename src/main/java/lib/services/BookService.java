package lib.services;

import lib.models.Book;
import lib.models.Person;
import lib.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService{
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public List<Book> findAll(boolean sortedByYear) {
        if (sortedByYear) {
            return bookRepository.findAll(Sort.by("year"));
        }
        else {
            return bookRepository.findAll();
        }
    }

    @Transactional

    public List<Book> findByPages(Integer page, Integer booksPerPage, boolean sortedByYear) {
        if (sortedByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        else {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    @Transactional
    public List<Book> searchByTitle(String query) {
        return bookRepository.findByTitleStartsWith(query);
    }

    @Transactional
    public Book findOne(int id) {
        Optional<Book> found = bookRepository.findById(id);
        return found.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updBook) {
        updBook.setId_book(id);
        bookRepository.save(updBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Person getBookOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(book->
        {
            book.setOwner(null);
            book.setTakenAt(null);
        });
    }

    @Transactional
    public void assign(int id, Person person) {
        bookRepository.findById(id).ifPresent(
                book->{
                    book.setOwner(person);
                    book.setTakenAt(new Date());
                }
        );
    }
}
