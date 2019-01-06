package ur.edu.pl.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.repositories.EmployeeRepository;
import ur.edu.pl.project.repositories.RaiseRequestRepository;
import ur.edu.pl.project.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    ProjectService projectService;



    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getEmployeeData_IfUserNotFoundShouldThrowAnException() {

        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            User user = userRepository.findById(0).orElseThrow(
                    () -> new ApiException("Błąd", HttpStatus.BAD_REQUEST,"Nie znaleziono użytkownika")
            );
        });
    }

    @Test
    void getEmployeeData_IfEmployeeNotFoundShouldThrowAnException() {

        when(employeeRepository.findByUserId(0)).thenReturn(null);
        assertThrows(ApiException.class, () -> {
            Employee employee=employeeRepository.findByUserId(0);
            if (employee==null) throw new ApiException("Błąd",HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");
        });
    }


}