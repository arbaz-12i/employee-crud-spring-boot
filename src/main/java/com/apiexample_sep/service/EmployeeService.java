package com.apiexample_sep.service;

import com.apiexample_sep.dto.EmployeeDto;
import com.apiexample_sep.entity.Employee;
import com.apiexample_sep.exception.GlobalExceptionHandler;
import com.apiexample_sep.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public String createEmployee(Employee emp) {

        employeeRepository.save(emp);
        return "done";
    }

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDto updateRegistration(long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id).get();
        employee.setName(employeeDto.getName());
        employee.setEmailId(employeeDto.getEmailId());
        employee.setMobile(employeeDto.getMobile());
        Employee savedEmp = employeeRepository.save(employee);
        BeanUtils.copyProperties(savedEmp, employeeDto);
        return employeeDto;
    }


    public List<Employee> getEmployee(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(Sort.Direction.ASC,sortBy):Sort.by(Sort.Direction.DESC,sortBy);

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort); //this will create a special object where pageNo,pageSize will kept

        Page<Employee> employees = employeeRepository.findAll(pageable);
        List<Employee> contents = employees.getContent(); //it will convert page to list
        return contents;
    }

    public Employee getEmployeeById(long eid) {
        Employee employee = employeeRepository.findById(eid).orElseThrow(
                () -> new GlobalExceptionHandler.ResourceNotFoundException("Record not found")
        );
        return employee;
    }


}
