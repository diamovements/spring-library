package lib.services;
import lib.models.Book;
import lib.models.Person;
import lib.repositories.BookRepository;
import lib.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    @Transactional
    public Person findOne(int id) {
        Optional<Person> found = peopleRepository.findById(id);
        return found.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updPerson) {
        updPerson.setId_person(id);
        peopleRepository.save(updPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public Optional<Person> getPersonByFullName(String fullName) {
        return  peopleRepository.findByFullName(fullName);
    }
    @Transactional
    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            person.get().getBooks().forEach(book->{
                long diffInMillis=Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (diffInMillis > 432000000) {
                    book.setExpired(true);
                }
            });
            return person.get().getBooks();
        }
        else {
            return Collections.emptyList();
        }
    }
}
