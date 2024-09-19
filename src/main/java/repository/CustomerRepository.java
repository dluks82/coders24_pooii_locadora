package repository;

import model.customer.Customer;

import java.util.List;

public interface CustomerRepository extends Repository<Customer> {

    List<Customer> findByName(String name);

    Customer findByDocument(String document);

}
