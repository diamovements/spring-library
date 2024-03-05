package lib.repositories;

import lib.models.Person;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer>{
    Optional<Person> findByFullName(String fullName);
}
