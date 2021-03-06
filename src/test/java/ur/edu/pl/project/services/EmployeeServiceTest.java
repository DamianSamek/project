package ur.edu.pl.project.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.Role;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.model.dto.EmployeeDTO;
import ur.edu.pl.project.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    public EmployeeDTO employeeDTO;

    @Mock
    public EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    public EmployeeService employeeService;



    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createEmployee_TestSaveToRepositoryMethod() {

        Employee newEmployee = new Employee();
        when(employeeRepositoryMock.save(newEmployee)).thenReturn(newEmployee);
        assertEquals(employeeRepositoryMock.save(newEmployee),newEmployee);
    }

    @Test
    public void updateEmployee_TestSaveToRepositoryMethod() {

        Employee existingEmployee = new Employee();

        when(employeeRepositoryMock.save(existingEmployee)).thenReturn(existingEmployee);
        assertEquals(employeeRepositoryMock.save(existingEmployee),existingEmployee);
    }

    @Test
    public void deleteEmployee_TestSaveToRepositoryMethod() {

        Employee employee = new Employee();
        when(employeeRepositoryMock.save(employee)).thenReturn(employee);
        assertEquals(employeeRepositoryMock.save(employee),employee);
    }

    @Test
    public void createEmployee_IfEmployeeAlreadyExistsShouldThrowAnException() {
            when(employeeRepositoryMock.findByUserEmail("damian@firma.pl")).thenReturn(new Employee());
            assertThrows(UserCreateException.class, () -> {Employee existingEmployee = employeeRepositoryMock.findByUserEmail("damian@firma.pl");
                if (existingEmployee!=null)
                    throw new UserCreateException("Błąd w tworzeniu pracownika", HttpStatus.BAD_REQUEST,
                            "Pracownik o podanym emailu istnieje.");});
        }

    @Test
    public void createEmployee_IfEmployeeNotExistShouldNotThrowAnException() {
        when(employeeRepositoryMock.findByUserEmail("damian@firma.pl")).thenReturn(null);
        assertDoesNotThrow(() -> {Employee existingEmployee = employeeRepositoryMock.findByUserEmail("damian@firma.pl");
            if (existingEmployee!=null)
                throw new UserCreateException("Błąd w tworzeniu pracownika", HttpStatus.BAD_REQUEST,
                        "Pracownik o podanym emailu istnieje.");});
    }




    @Test
    public void createEmployee_IfPasswordAndConfirmPasswordNotMatchShouldThrowAnException(){

        employeeDTO = new EmployeeDTO();
        employeeDTO.setPassword("damian");
        employeeDTO.setConfirmPassword("damain");
        assertThrows(UserCreateException.class, () -> {
            if (employeeDTO.getPassword().equals(employeeDTO.getConfirmPassword())) {
                //newUser.setPassword(encryptionService.encode(employee.getPassword()));
            }
            else throw new UserCreateException("Błąd w tworzeniu pracownika",
                    HttpStatus.BAD_REQUEST,"Hasła nie zgadzają się");
        });
    }




    @Test
    public void createEmployee_IfPasswordAndConfirmPasswordMatchShouldNotThrowAnException(){

        employeeDTO = new EmployeeDTO();
        employeeDTO.setPassword("damian");
        employeeDTO.setConfirmPassword("damian");

        assertDoesNotThrow(() -> {
            if (employeeDTO.getPassword().equals(employeeDTO.getConfirmPassword())) {
                //newUser.setPassword(encryptionService.encode(employee.getPassword()));
            }
            else throw new UserCreateException("Błąd w tworzeniu pracownika",
                    HttpStatus.BAD_REQUEST,"Hasła nie zgadzają się");
        });
    }

    @Test
    public void modifyEmployee_IfEmployeeDoesntExistShouldThrowAnException() {

        when(employeeRepositoryMock.findById(0)).thenReturn(Optional.empty());
        assertThrows(UserCreateException.class, () -> {
            Employee existingEmployee = employeeRepositoryMock.findById(0)
                    .orElseThrow(() -> new UserCreateException("Błąd w edycji pracownika",
                            HttpStatus.BAD_REQUEST, "Pracownik nie istnieje."));
        });
    }

    @Test
    public void modifyEmployee_IfEmployeeExistShouldNotThrowAnException() {

        when(employeeRepositoryMock.findById(0)).thenReturn(Optional.of(new Employee()));
        assertDoesNotThrow(() -> {
            Employee existingEmployee = employeeRepositoryMock.findById(0)
                    .orElseThrow(() -> new UserCreateException("Błąd w edycji pracownika",
                            HttpStatus.BAD_REQUEST, "Pracownik nie istnieje."));
        });
    }

    @Test
    public void deleteEmployee_IfEmployeeDoesntExistShouldThrowAnException() {

        when(employeeRepositoryMock.findById(0)).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            Employee employee = employeeRepositoryMock.findById(0)
                    .orElseThrow(() -> new ApiException("Błąd przy usuwaniu pracownika"
                            ,HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika"));
        });
    }

    @Test
    public void deleteEmployee_IfEmployeeExistShouldNotThrowAnException() {

        when(employeeRepositoryMock.findById(0)).thenReturn(Optional.of(new Employee()));
        assertDoesNotThrow(() -> {
            Employee employee = employeeRepositoryMock.findById(0)
                    .orElseThrow(() -> new ApiException("Błąd przy usuwaniu pracownika"
                            ,HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika"));
        });
    }
    }
