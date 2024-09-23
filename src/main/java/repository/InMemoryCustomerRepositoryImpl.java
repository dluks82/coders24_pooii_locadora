package repository;

import model.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerRepositoryImpl implements CustomerRepository{

    private List<Customer> customers;

    public InMemoryCustomerRepositoryImpl(){

        customers = new ArrayList<Customer>();
    }


    @Override
    public Customer save(Customer customer) {
        customers.add(customer);
        return customer;
    }

    //Update DocumentId
    @Override
    public Customer update(Customer customer) {
        Customer customerUpdate = findByDocument(customer.getDocumentId());
        if(customerUpdate != null){
            customers.set(customers.indexOf(customerUpdate), customer);
        }
      return customerUpdate;
    }



    @Override
    public boolean delete(Customer customer) {
        customers.remove(customer);
        return true;
    }


    @Override
    public Customer findById(String id) {
        for(Customer customer : customers){
            if(customer.getId().equals(id)){
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
        for(Customer customer : customers){
            if(customer.getDocumentId().equals(document)){
                return customer;
            }
        }
        return null;
    }
}
