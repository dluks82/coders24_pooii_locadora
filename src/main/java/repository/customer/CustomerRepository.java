package repository.customer;

import model.customer.Customer;
import repository.Repository;

import java.util.List;

public interface CustomerRepository extends Repository<Customer> {

    List<Customer> findByName(String name);

    Customer findByDocument(String document);

}
