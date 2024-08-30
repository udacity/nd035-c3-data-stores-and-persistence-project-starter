package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;

import java.util.List;

public interface CustomerService {
    public Customer saveCustomer(Customer customer);

    public Customer getCustomer(Long customerId);

    public List<Customer> getAllCustomers();

    Customer getOwnerByPet(Long petId);
}
