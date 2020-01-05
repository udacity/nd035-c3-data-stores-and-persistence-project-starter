package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.CritterApplication;
import com.udacity.jdnd.course3.critter.pet.PetController;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.schedule.ScheduleController;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.UserController;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is a set of functional tests to validate the basic capabilities desired for this application.
 * Students will need to configure the application to run these tests by adding application.properties file
 * to the test/resources directory that specifies the datasource. It can run using an in-memory H2 instance
 * and should not try to re-use the same datasource used by the rest of the app.
 *
 * These tests should all pass once the project is complete.
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CritterApplication.class)
public class CritterFunctionalTest {

    @Autowired
    private UserController userController;

    @Autowired
    private PetController petController;

    @Autowired
    private ScheduleController scheduleController;

    @Test
    public void testCreateCustomer(){
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);
        Assert.assertEquals(newCustomer.getName(), customerDTO.getName());
    }

    @Test
    public void testCreateEmployee(){
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO newEmployee = userController.saveEmployee(employeeDTO);
        Assert.assertEquals(employeeDTO.getSkills(), newEmployee.getSkills());
    }

    @Test
    public void testAddPetsToCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);

        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.savePet(petDTO);

        List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId());
        Assert.assertEquals(newPet.getId(), pets.get(0).getId());
        Assert.assertEquals(newPet.getName(), pets.get(0).getName());
    }

    @Test
    public void testFindPetsByOwner() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);

        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.savePet(petDTO);
        petDTO.setType(PetType.DOG);
        petDTO.setName("DogName");
        PetDTO newPet2 = petController.savePet(petDTO);

        List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId());
        Assert.assertEquals(pets.size(), 2);
    }

    @Test
    public void testFindOwnerByPet() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);

        PetDTO petDTO = createPetDTO();
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.savePet(petDTO);

        CustomerDTO owner = userController.getOwnerByPet(newPet.getId());
        Assert.assertEquals(owner.getId(), newCustomer.getId());
    }

    @Test
    public void testChangeEmployeeAvailability() {
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO emp1 = userController.saveEmployee(employeeDTO);
        Assert.assertNull(emp1.getDaysAvailable());

        Set<DayOfWeek> availability = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        userController.setAvailability(availability, emp1.getId());

        EmployeeDTO emp2 = userController.getEmployee(emp1.getId());
        Assert.assertEquals(availability, emp2.getDaysAvailable());
    }

    @Test
    public void testFindEmployeesByServiceAndTime() {
        EmployeeDTO emp1 = createEmployeeDTO();
        EmployeeDTO emp2 = createEmployeeDTO();
        EmployeeDTO emp3 = createEmployeeDTO();

        emp1.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        emp2.setDaysAvailable(Sets.newHashSet(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        emp3.setDaysAvailable(Sets.newHashSet(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

        emp1.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        emp2.setSkills(Sets.newHashSet(EmployeeSkill.PETTING, EmployeeSkill.MEDICATING));
        emp3.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        EmployeeDTO emp1n = userController.saveEmployee(emp1);
        EmployeeDTO emp2n = userController.saveEmployee(emp2);
        EmployeeDTO emp3n = userController.saveEmployee(emp3);

        //make a request that matches employee 1 or 2
        EmployeeRequestDTO er1 = new EmployeeRequestDTO();
        er1.setDate(LocalDate.of(2019, 12, 25)); //wednesday
        er1.setSkills(Sets.newHashSet(EmployeeSkill.PETTING));

        Set<Long> eIds1 = userController.findEmployeesForService(er1).stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        Set<Long> eIds1expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
        Assert.assertEquals(eIds1, eIds1expected);

        //make a request that matches only employee 3
        EmployeeRequestDTO er2 = new EmployeeRequestDTO();
        er2.setDate(LocalDate.of(2019, 12, 27)); //friday
        er2.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        Set<Long> eIds2 = userController.findEmployeesForService(er2).stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
        Assert.assertEquals(eIds2, eIds2expected);
    }

    @Test
    public void testSchedulePetsForServiceWithEmployee() {
        EmployeeDTO employeeTemp = createEmployeeDTO();
        employeeTemp.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        EmployeeDTO employeeDTO = userController.saveEmployee(employeeTemp);
        CustomerDTO customerDTO = userController.saveCustomer(createCustomerDTO());
        PetDTO petTemp = createPetDTO();
        petTemp.setOwnerId(customerDTO.getId());
        PetDTO petDTO = petController.savePet(petTemp);

        LocalDate date = LocalDate.of(2019, 12, 25);
        List<Long> petList = Lists.newArrayList(petDTO.getId());
        List<Long> employeeList = Lists.newArrayList(employeeDTO.getId());
        Set<EmployeeSkill> skillSet =  Sets.newHashSet(EmployeeSkill.PETTING);

        ScheduleDTO scheduleDTO = scheduleController.createSchedule(createScheduleDTO(
                petList, employeeList, date, skillSet));

        Assert.assertEquals(scheduleDTO.getActivities(), skillSet);
        Assert.assertEquals(scheduleDTO.getDate(), date);
        Assert.assertEquals(scheduleDTO.getEmployeeIds(), employeeList);
        Assert.assertEquals(scheduleDTO.getPetIds(), petList);
    }

    @Test
    public void testFindScheduleByEntities() {
        ScheduleDTO sched1 = populateSchedule(1, 2, LocalDate.of(2019, 12, 25), Sets.newHashSet(EmployeeSkill.FEEDING));
        ScheduleDTO sched2 = populateSchedule(3, 1, LocalDate.of(2019, 12, 26), Sets.newHashSet(EmployeeSkill.PETTING));

        List<ScheduleDTO> scheds1e = scheduleController.getScheduleForEmployee(sched1.getEmployeeIds().get(0));
        List<ScheduleDTO> scheds1p = scheduleController.getScheduleForPet(sched1.getPetIds().get(0));
        List<ScheduleDTO> scheds1c = scheduleController.getScheduleForCustomer(userController.getOwnerByPet(sched1.getPetIds().get(0)).getId());
        compareSchedules(sched1, scheds1e.get(0));
        compareSchedules(sched1, scheds1p.get(0));
        compareSchedules(sched1, scheds1c.get(0));

        List<ScheduleDTO> scheds2e = scheduleController.getScheduleForEmployee(sched2.getEmployeeIds().get(0));
        List<ScheduleDTO> scheds2p = scheduleController.getScheduleForPet(sched2.getPetIds().get(0));
        List<ScheduleDTO> scheds2c = scheduleController.getScheduleForCustomer(userController.getOwnerByPet(sched2.getPetIds().get(0)).getId());
        compareSchedules(sched2, scheds2e.get(0));
        compareSchedules(sched2, scheds2p.get(0));
        compareSchedules(sched2, scheds2c.get(0));
    }


    private static EmployeeDTO createEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("TestEmployee");
        employeeDTO.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        return employeeDTO;
    }
    private static CustomerDTO createCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("TestEmployee");
        customerDTO.setPhoneNumber("123-456-789");
        return customerDTO;
    }

    private static PetDTO createPetDTO() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("TestPet");
        petDTO.setType(PetType.CAT);
        return petDTO;
    }

    private static EmployeeRequestDTO createEmployeeRequestDTO() {
        EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO();
        employeeRequestDTO.setDate(LocalDate.of(2019, 12, 25));
        employeeRequestDTO.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
        return employeeRequestDTO;
    }

    private static ScheduleDTO createScheduleDTO(List<Long> petIds, List<Long> employeeIds, LocalDate date, Set<EmployeeSkill> activities) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setDate(date);
        scheduleDTO.setActivities(activities);
        return scheduleDTO;
    }

    private ScheduleDTO populateSchedule(int numEmployees, int numPets, LocalDate date, Set<EmployeeSkill> activities) {
        List<Long> employeeIds = IntStream.range(0, numEmployees)
                .mapToObj(i -> createEmployeeDTO())
                .map(e -> {
                    e.setSkills(activities);
                    e.setDaysAvailable(Sets.newHashSet(date.getDayOfWeek()));
                    return userController.saveEmployee(e).getId();
                }).collect(Collectors.toList());
        CustomerDTO cust = userController.saveCustomer(createCustomerDTO());
        List<Long> petIds = IntStream.range(0, numPets)
                .mapToObj(i -> createPetDTO())
                .map(p -> {
                    p.setOwnerId(cust.getId());
                    return petController.savePet(p).getId();
                }).collect(Collectors.toList());
        return scheduleController.createSchedule(createScheduleDTO(petIds, employeeIds, date, activities));
    }

    private static void compareSchedules(ScheduleDTO sched1, ScheduleDTO sched2) {
        Assert.assertEquals(sched1.getPetIds(), sched2.getPetIds());
        Assert.assertEquals(sched1.getActivities(), sched2.getActivities());
        Assert.assertEquals(sched1.getEmployeeIds(), sched2.getEmployeeIds());
        Assert.assertEquals(sched1.getDate(), sched2.getDate());
    }

}
