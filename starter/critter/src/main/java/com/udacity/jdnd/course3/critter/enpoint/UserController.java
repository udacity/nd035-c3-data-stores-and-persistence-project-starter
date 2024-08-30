package com.udacity.jdnd.course3.critter.enpoint;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final CustomerService customerService;
    private final PetService petService;
    private final EmployeeService employeeService;
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        List<Long> petIds = customerDTO.getPetIds();
        List<Pet> pets = new ArrayList<>();
        if (petIds != null) {
            petIds.forEach(petId -> {
                pets.add(petService.getPetById(petId));
            });
        }
        Customer customer = convertCustomerDTOToEntity(customerDTO);
        customer.setPets(pets);
        Customer savedCustomer = customerService.saveCustomer(customer);
        return convertCustomerEntityToDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach(customer -> {
            customerDTOS.add(convertCustomerEntityToDTO(customer));
        });
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = customerService.getOwnerByPet(petId);
        return convertCustomerEntityToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee convertedEmployee = convertEmployeeDTOToEntity(employeeDTO);
        Employee savedEmployee = employeeService.saveEmployee(convertedEmployee);
        employeeDTO.setId(savedEmployee.getId());
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employeeDTO, Employee.class);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return convertEmployeeEntityToDTO(employee);
    }

    private EmployeeDTO convertEmployeeEntityToDTO(Employee employee) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        LocalDate daysAvailable = employeeDTO.getDate();
        List<Employee> employees = employeeService.findEmployeesForService(employeeDTO.getSkills(), daysAvailable);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.forEach(employee -> {
            employeeDTOS.add(convertEmployeeEntityToDTO(employee));
        });
        return employeeDTOS;
    }

    private Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            petIds.forEach(petId -> {
                customer.addPet(petService.getPetById(petId));
            });
        }
        return customer;
    }

    private CustomerDTO convertCustomerEntityToDTO(Customer customer) {
        ModelMapper modelMapper = new ModelMapper();
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        List<Long> petIds = new ArrayList<>();
        customer.getPets().forEach(pet -> {
            petIds.add(pet.getId());
        });
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }
}
