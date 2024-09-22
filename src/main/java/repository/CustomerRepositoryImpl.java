package repository;

import model.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository{

    private List<Customer> customers;

    public CustomerRepositoryImpl(){
        customers = new ArrayList<Customer>();
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
       for(Customer customer : customers){
          if(customer.getDocumentId().equals(document)){
              return customer;
          }
       }
       return null;
    }


    @Override
    public Customer save(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public Customer update(Customer entity) {
        return null;
    }

    @Override
    public boolean delete(Customer entity) {
        return false;
    }

    @Override
    public Customer findById(String id) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return List.of();
    }
}
