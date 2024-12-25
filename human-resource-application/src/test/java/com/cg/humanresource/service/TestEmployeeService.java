package com.cg.humanresource.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.cg.humanresource.dto.EmployeesDTO;
import com.cg.humanresource.entity.Departments;
import com.cg.humanresource.entity.Employees;
import com.cg.humanresource.entity.Jobs;
import com.cg.humanresource.exception.DepartmentNotFoundException;
import com.cg.humanresource.exception.EmployeeNotFoundException;
import com.cg.humanresource.repository.DepartmentsRepository;
import com.cg.humanresource.repository.EmployeesRepository;
import com.cg.humanresource.repository.JobsRepository;

class TestEmployeeService {

    @InjectMocks
    private EmployeesService employeesService;

    @Mock
    private EmployeesRepository employeesRepository;

    @Mock
    private JobsRepository jobsRepository;

    @Mock
    private DepartmentsRepository departmentsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByFirstName() {
        // Arrange
        String firstName = "John";
        Employees employee = new Employees();
        employee.setFirstName(firstName);
        employee.setEmployeeId(1);
        Departments department = new Departments();
        department.setDepartmentId(10L);
        employee.setDepartment(department);
        Jobs job = new Jobs();
        job.setJobId("HR");
        employee.setJob(job);
        employee.setManager(employee);

        when(employeesRepository.findByFirstName(firstName)).thenReturn(Collections.singletonList(employee));

        // Act
        List<EmployeesDTO> result = employeesService.findByFirstName(firstName);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(firstName, result.get(0).getFirstName());
    }


    @Test
    void testFindByEmail() {
        // Arrange
        String email = "john.doe@example.com";
        Employees employee = new Employees();
        employee.setEmail(email);
        employee.setEmployeeId(1);
        Departments department = new Departments();
        department.setDepartmentId(10L);
        employee.setDepartment(department);
        Jobs job = new Jobs();
        job.setJobId("HR");
        employee.setJob(job);
        employee.setManager(employee);
        when(employeesRepository.findByEmail(email)).thenReturn(employee);

        // Act
        EmployeesDTO result = employeesService.findByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testFindByPhoneNumber() {
        // Arrange
        String phoneNumber = "1234567890";
        Employees employee = new Employees();
        employee.setPhoneNumber(phoneNumber);
        employee.setEmployeeId(1);
        Departments department = new Departments();
        department.setDepartmentId(10L);
        employee.setDepartment(department);
        Jobs job = new Jobs();
        job.setJobId("HR");
        employee.setJob(job);
        employee.setManager(employee);
        when(employeesRepository.findByPhoneNumber(phoneNumber)).thenReturn(employee);

        // Act
        EmployeesDTO result = employeesService.findByPhoneNumber(phoneNumber);

        // Assert
        assertNotNull(result);
        assertEquals(phoneNumber, result.getPhoneNumber());
    }

    @Test
    void testFindTotalCommissionIssuedToEmployeeByDepartmentId() {
        // Arrange
        int departmentId = 1;
        Employees employee = new Employees();
        employee.setSalary(1000.0);
        employee.setCommissionPct(0.1);
        
        when(employeesRepository.findByDepartmentId(departmentId)).thenReturn(Collections.singletonList(employee));

        // Act
        int result = employeesService.findTotalCommissionIssuedToEmployeeByDepartmentId(departmentId);

        // Assert
        assertEquals(100, result);
    }

    @Test
    void testFindTotalCommissionIssuedToEmployeeByDepartmentId_ThrowsException() {
        // Arrange
        int departmentId = 1;
        when(employeesRepository.findByDepartmentId(departmentId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> employeesService.findTotalCommissionIssuedToEmployeeByDepartmentId(departmentId));
    }
    
    @Test
    public void testListAllEmployeesByDepartmentId_DepartmentNotFound() {
        // Arrange
        int deptId = 1;
        when(employeesRepository.findByDepartmentId(deptId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> {
            employeesService.listAllEmployeesByDepartmentId(deptId);
        });
    }

    @Test
    public void testCountEmployeesGroupByDepartment() {
        // Arrange
        List<Map<String, Integer>> mockCount = new ArrayList<>();
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("IT", 5);
        mockCount.add(countMap);

        when(employeesRepository.countAllEmployeesGroupByDepartment()).thenReturn(mockCount);

        // Act
        List<Map<String, Integer>> result = employeesService.countEmployeesGroupByDepartment();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeesRepository, times(1)).countAllEmployeesGroupByDepartment();
    }

    @Test
    public void testCountEmployeesGroupByLocation() {
        // Arrange
        List<Map<Integer, Integer>> mockCount = new ArrayList<>();
        Map<Integer, Integer> countMap = new HashMap<>();
        countMap.put(1, 10);  // Location ID 1 has 10 employees
        mockCount.add(countMap);

        when(employeesRepository.countAllEmployeesGroupByLocation()).thenReturn(mockCount);

        // Act
        List<Map<Integer, Integer>> result = employeesService.countEmployeesGroupByLocation();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeesRepository, times(1)).countAllEmployeesGroupByLocation();
    }

    @Test
    public void testGetMaxSalaryOfJobByEmployeeId_EmployeeNotFound() {
        // Arrange
        int employeeId = 1;
        when(employeesRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeesService.getMaxSalaryOfJobByEmployeeId(employeeId);
        });
    }

    @Test
    public void testGetAllOpenPositions() {
        // Arrange
        List<Object> openPositions = Arrays.asList("Position1", "Position2");
        when(jobsRepository.findAllOpenPositions()).thenReturn(openPositions);

        // Act
        List<Object> result = employeesService.getAllOpenPositions();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jobsRepository, times(1)).findAllOpenPositions();
    }

    @Test
    public void testGetAllOpenPositionsByDepartment_DepartmentNotFound() {
        // Arrange
        int deptId = 1;
        when(employeesRepository.findByDepartmentId(deptId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> {
            employeesService.getAllOpenPositionsByDepartment(deptId);
        });
    }

    @Test
    public void testGetAllOpenPositionsByDepartment() {
        // Arrange
        int deptId = 1;
        List<Object> openPositions = Arrays.asList("Position1", "Position2");
        when(employeesRepository.findByDepartmentId(deptId)).thenReturn(Collections.singletonList(new Employees()));
        when(jobsRepository.findAllOpenPositionsByDepartmentId(deptId)).thenReturn(openPositions);

        // Act
        List<Object> result = employeesService.getAllOpenPositionsByDepartment(deptId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jobsRepository, times(1)).findAllOpenPositionsByDepartmentId(deptId);
    }

    @Test
    public void testGetAllEmployeeByHireDate_EmployeeNotFound() {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 1);

        when(employeesRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeesService.getAllEmployeeByHireDate(startDate, endDate);
        });
    }

    @Test
    void testAddOrModifyEmployee() throws Exception {
        // Arrange
        Employees employee = new Employees();
        Departments department = new Departments();
        department.setDepartmentId(1L);
        Jobs job = new Jobs();
        job.setJobId("DEV");
        
        employee.setDepartment(department);
        employee.setJob(job);

        when(departmentsRepository.findById(1L)).thenReturn(Optional.of(department));
        when(jobsRepository.findById("DEV")).thenReturn(Optional.of(job));
        when(employeesRepository.save(employee)).thenReturn(employee);

        // Act
        Employees result = employeesService.addOrModifyEmployee(employee);

        // Assert
        assertNotNull(result);
        verify(employeesRepository, times(1)).save(employee);
    }

    @Test
    void testDeleteEmployeeById() {
        // Arrange
        int employeeId = 1;
        Employees employee = new Employees();
        employee.setEmployeeId(employeeId);

        when(employeesRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        doNothing().when(employeesRepository).deleteById(employeeId);

        // Act
        Employees result = employeesService.deleteEmployeeById(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getEmployeeId());
        verify(employeesRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void testDeleteEmployeeById_ThrowsException() {
        // Arrange
        int employeeId = 1;
        when(employeesRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeesService.deleteEmployeeById(employeeId));
    }
}
