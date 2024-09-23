package service;

import dto.CreateCustomerDTO;
import model.customer.Customer;
import model.customer.Individual;
import model.customer.LegalEntity;
import org.w3c.dom.html.HTMLTableRowElement;
import repository.CustomerRepository;
import utils.CustomerType;

import java.util.List;
import java.util.UUID;

public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
          this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CreateCustomerDTO customerDTO) {
        Customer existCustomer = customerRepository.findByDocument(customerDTO.documentId());

        if (existCustomer != null) {
            throw new IllegalArgumentException("Já existe um cliente com esse documento");
        }

        Customer newCustomer = null;

        String customerId = UUID.randomUUID().toString();

        if (customerDTO.type().equals(CustomerType.LEGALENTITY)) {
            newCustomer = new LegalEntity(
                    customerId,
                    customerDTO.name(),
                    customerDTO.numberPhone(),
                    customerDTO.documentId()
            );
        } else if (customerDTO.type().equals(CustomerType.INDIVIDUAL)) {
            newCustomer = new Individual(
                    customerId,
                    customerDTO.name(),
                    customerDTO.numberPhone(),
                    customerDTO.documentId()
            );
        }

        if(newCustomer != null) {
            customerRepository.save(newCustomer);
            return newCustomer;
        }

        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        Customer existCustomer = customerRepository.findByDocument(customer.getDocumentId());
        if (existCustomer == null) {
            throw  new IllegalArgumentException("Esse cliente não existe");
        }
        customerRepository.delete(customer);
        return true;
    }

    @Override
    public Customer findCustomerById(String id) {
        return null;
    }

    @Override
    public List<Customer> findAllCustomers() {

        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomerByName(String name) {
        return List.of();
    }

    @Override
    public Customer findCustomerByDocument(String document) {
        return null;
    }
}
