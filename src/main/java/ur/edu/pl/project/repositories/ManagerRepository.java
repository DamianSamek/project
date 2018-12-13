package ur.edu.pl.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.Manager;
import ur.edu.pl.project.model.User;

@RepositoryRestResource(collectionResourceRel = "manager", path = "manager")
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

//    List<Employee> findByPhone(@Param("phone") String phone);

}
