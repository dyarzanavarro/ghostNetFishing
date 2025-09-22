package ghostnetfishing.demo.repo;
import ghostnetfishing.demo.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PersonRepository extends JpaRepository<Person, Long> {
  
  List<Person> findByRescuerTrueOrderByNameAsc();
  List<Person> findByPhoneIsNotNullOrderByNameAsc();
}