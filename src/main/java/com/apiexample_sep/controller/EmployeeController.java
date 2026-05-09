package com.apiexample_sep.controller;

import com.apiexample_sep.dto.APIResponse;
import com.apiexample_sep.dto.EmployeeDto;
import com.apiexample_sep.entity.Employee;
import com.apiexample_sep.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController //this will make this api layer now
@RequestMapping("/api/v1/employee") // this consist of URI is the part of URL
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //http://localhost:8080/api/v1/employee/save

//    @PostMapping("/save") //this is supplying data to beckend for saved the record
//    public String createEmployee(@RequestBody Employee emp){ //this annotation help us to received data from JSON without this data will not received
//        String status = employeeService.createEmployee(emp);
//        if(status=="done") {
//            return "done";
//        }
//        return status;
//    }



    @PostMapping("/save")
    public ResponseEntity<APIResponse> createEmoployee(@Valid @RequestBody Employee emp, BindingResult result) {
        APIResponse<String> response = new APIResponse<>();
        if(result.hasErrors()){
            response.setStatus(500);
            response.setMessage("invalid input");
            response.setData(result.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String status = employeeService.createEmployee(emp);

        if (status.equals("done")) {
            response.setMessage("transaction completed");
            response.setData("done");
            response.setStatus(201);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        response.setMessage("transaction failed");
        response.setData("duplicate entry");
        response.setStatus(500);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //http://localhost:8080/api/v1/employee/delete?id=1
    @DeleteMapping("/delete")
    public ResponseEntity<APIResponse> deleteEmployee(@RequestParam long id){
        employeeService.deleteEmployee(id);
        APIResponse<String> response = new APIResponse<>();
            response.setMessage("transaction completed");
            response.setData("deleted");
            response.setStatus(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
       }

    //http://localhost:8080/api/v1/employee/update/16
       @PutMapping("/update/{id}")
       public ResponseEntity<APIResponse<EmployeeDto>> updateEmployee(
               @RequestBody EmployeeDto employeeDto, //@RequestBody means that json data goto employeeDto
               @PathVariable long id  //@PathVariable will copy the data to  id
       ){
           EmployeeDto dto = employeeService.updateRegistration(id, employeeDto);
           APIResponse<EmployeeDto> response = new APIResponse<>();
           response.setMessage("updated");
           response.setData(employeeDto);
           response.setStatus(200);
           return new ResponseEntity<>(response,HttpStatus.OK);
       }

    //http://localhost:8080/api/v1/employee/all?pageNo=0&pageSize=5&sortBy=name
       @GetMapping("/all")
       public ResponseEntity<APIResponse<List<Employee>>> getEmployee(
               @RequestParam(name = "pageNo" , defaultValue = "0" , required = false) int pageNo,
               @RequestParam(name = "pageSize", defaultValue = "5" , required = false)  int pageSize,
               @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
               @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
               ){
           List<Employee> employees = employeeService.getEmployee(pageNo,pageSize,sortBy,sortDir);
           APIResponse<List<Employee>> response = new APIResponse<>();
           response.setMessage("Done");
           response.setData(employees);
           response.setStatus(200);
           return new ResponseEntity<>(response,HttpStatus.OK);
       }

    //http://localhost:8080/api/v1/employee/id/1
       @GetMapping("/id/{eid}")
       public ResponseEntity<APIResponse<Employee>> getEmployeeById(@PathVariable long eid){
        Employee employee = employeeService.getEmployeeById(eid);
        APIResponse<Employee> response = new APIResponse<>();
        response.setMessage("Done");
        response.setData(employee);
        response.setStatus(200);
        return new ResponseEntity<>(response,HttpStatus.OK);
       }
}
