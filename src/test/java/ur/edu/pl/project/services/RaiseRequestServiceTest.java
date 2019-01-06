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
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.RaiseRequest;
import ur.edu.pl.project.repositories.AgreementRepository;
import ur.edu.pl.project.repositories.EmployeeRepository;
import ur.edu.pl.project.repositories.ProjectRepository;
import ur.edu.pl.project.repositories.RaiseRequestRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class RaiseRequestServiceTest {

    @Mock
    AgreementRepository agreementRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    RaiseRequestRepository raiseRequestRepository;

    @InjectMocks
    ProjectService projectService;



    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createRaiseRequest_IfAgreementNotFoundShouldThrowAnException() {
        when(agreementRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            Agreement agreement = agreementRepository.findById(0)
                    .orElseThrow(() -> new ApiException("Błąd przy tworzeniu zapytania o podwyżkę",
                            HttpStatus.BAD_REQUEST, "Nie znaleziono umowy"));
        });
    }

    @Test
    void createRaiseRequest_IfEmployeeNotFoundShouldThrowAnException() {
        when(employeeRepository.findByUserEmail("")).thenReturn(null);
        assertThrows(ApiException.class, () -> {
            Employee employee = employeeRepository.findByUserEmail("");
            if (employee==null) throw new ApiException("Błąd przy tworzeniu zapytania o podwyżkę",
                    HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");
        });
    }

    @Test
    void considerRaiseRequest() {

        when(raiseRequestRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            RaiseRequest raiseRequest = raiseRequestRepository.findById(0).orElseThrow(() ->
                    new ApiException("Błąd przy rozpatrzaniu zapytania",
                            HttpStatus.BAD_REQUEST,"Nie znaleziono zapytania"));
        });
    }
}