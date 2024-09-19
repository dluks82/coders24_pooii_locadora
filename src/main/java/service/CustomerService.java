package service;

import model.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    boolean deleteCustomer(Customer customer);

    Customer findCustomerById(String id);

    List<Customer> findAllCustomers();

    List<Customer> findCustomerByName(String name);

    Customer findCustomerByDocument(String document);
}
