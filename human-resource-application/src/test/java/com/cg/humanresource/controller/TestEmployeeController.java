package com.cg.humanresource.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.cg.humanresource.dto.EmployeesDTO;
import com.cg.humanresource.entity.Employees;
import com.cg.humanresource.exception.EmployeeNotFoundException;
import com.cg.humanresource.exception.OpenJobPositionsNotFoundException;
import com.cg.humanresource.service.EmployeesService;
import com.cg.humanresource.service.JobsService;
import com.fasterxml.jackson.databind.ObjectMapper;

class TestEmployeeController {

    private MockMvc mockMvc;

    @Mock
    private EmployeesService employeesService;

    @Mock
    private JobsService jobsService;

    @InjectMocks
    private EmployeesController employeesController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeesController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetEmployeesByFirstName() throws Exception {
        String firstName = "John";
        List<EmployeesDTO> employees = List.of(new EmployeesDTO());

        when(employeesService.findByFirstName(firstName)).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees/findfname/{firstname}", firstName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));

        verify(employeesService).findByFirstName(firstName);
    }

    @Test
    void testGetEmployeesByEmail() throws Exception {
        String email = "john.doe@example.com";
        EmployeesDTO employee = new EmployeesDTO();

        when(employeesService.findByEmail(email)).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/findemail/{email}", email)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employee)));

        verify(employeesService).findByEmail(email);
    }

    @Test
    void testGetEmployeesByPhoneNumber() throws Exception {
        String phone = "123-456-7890";
        EmployeesDTO employee = new EmployeesDTO();

        when(employeesService.findByPhoneNumber(phone)).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/findphone/{phone}", phone)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employee)));

        verify(employeesService).findByPhoneNumber(phone);
    }

    @Test
    void testGetAllEmployeeWithNoCommission() throws Exception {
        List<EmployeesDTO> employees = List.of(new EmployeesDTO());

        when(employeesService.findAllEmployeeWithNoCommission()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees/findAllEmployeeWithNoCommission")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));

        verify(employeesService).findAllEmployeeWithNoCommission();
    }

    @Test
    void testFindTotalCommissionIssuedToEmployee() throws Exception {
        int departmentId = 1;
        Map<String, Integer> response = Map.of("departmentid", departmentId, "sum", 5000);

        when(employeesService.findTotalCommissionIssuedToEmployeeByDepartmentId(departmentId)).thenReturn(5000);

        mockMvc.perform(get("/api/v1/employees/findTotalCommissionIssuedToEmployee/{department_id}", departmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(employeesService).findTotalCommissionIssuedToEmployeeByDepartmentId(departmentId);
    }

    @Test
    void testListAllEmployeesByDepartmentId() throws Exception {
        int departmentId = 1;
        List<EmployeesDTO> employees = List.of(new EmployeesDTO());

        when(employeesService.listAllEmployeesByDepartmentId(departmentId)).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees/listAllEmployees/{department_id}", departmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));

        verify(employeesService).listAllEmployeesByDepartmentId(departmentId);
    }

    @Test
    void testDeleteEmployeeById() throws Exception {
        int employeeId = 1;
        Employees employee = new Employees();

        when(employeesService.deleteEmployeeById(employeeId)).thenReturn(employee);

        mockMvc.perform(delete("/api/v1/employees/{employeeId}", employeeId))
                .andExpect(status().isGone())
                .andExpect(content().string("Record deleted successfully."));

        verify(employeesService).deleteEmployeeById(employeeId);
    }

    @Test
    void testGetEmployeesCountByLocationWise() throws Exception {
        List<Map<Integer, Integer>> response = List.of(Map.of(1, 10));

        when(employeesService.countEmployeesGroupByLocation()).thenReturn(response);

        mockMvc.perform(get("/api/v1/employees/locationwisecountofemployees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(employeesService).countEmployeesGroupByLocation();
    }

    @Test
    void testFindMaxSalaryOfJobByEmployeeId() throws Exception {
        int empId = 1;
        Map<String, Object> response = Map.of("jobId", "Manager", "maxSalary", 100000);

        when(employeesService.getMaxSalaryOfJobByEmployeeId(empId)).thenReturn(response);

        mockMvc.perform(get("/api/v1/employees/{empid}/findmaxsalaryofjob", empId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(employeesService).getMaxSalaryOfJobByEmployeeId(empId);
    }


    @Test
    void testFindAllEmployeeByHireDateRange() throws Exception {
        LocalDate fromHireDate = LocalDate.of(2023, 1, 1);
        LocalDate toHireDate = LocalDate.of(2023, 12, 31);
        List<EmployeesDTO> employees = List.of(new EmployeesDTO());

        when(employeesService.getAllEmployeeByHireDate(fromHireDate, toHireDate)).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees/listallemployeebyhiredate/{from_hiredate}/{to_hiredate}", fromHireDate, toHireDate)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));

        verify(employeesService).getAllEmployeeByHireDate(fromHireDate, toHireDate);
    }

}
