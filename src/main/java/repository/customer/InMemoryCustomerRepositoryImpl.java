package repository.customer;

import model.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerRepositoryImpl implements CustomerRepository {
    private static CustomerRepository instance;
    private final List<Customer> customers;

    private InMemoryCustomerRepositoryImpl(){
        customers = new ArrayList<>();
    }

    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryCustomerRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void saveData() {

    }

    @Override
    public Customer save(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        Customer customerUpdate = findByDocument(customer.getDocumentId());
        if (customerUpdate != null) {
            customers.set(customers.indexOf(customerUpdate), customer);
        }
        return customerUpdate;
    }

    @Override
    public Customer findById(String id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }


    @Override
    public List<Customer> findAll() {
        List<Customer> temp = new ArrayList<>();
        for (Customer customer : customers) {
            temp.add(customer);
        }
        return temp;
    }

    @Override
    public List<Customer> findByName(String name) {
        List<Customer> temp = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getName().contains(name)) {
                temp.add(customer);
            }
        }
        return temp;
    }

    @Override
    public Customer findByDocument(String document) {
        for (Customer customer : customers) {
            if (customer.getDocumentId().equals(document)) {
                return customer;
            }
        }
        return null;
    }
}
