package service.customer;

import dto.CreateCustomerDTO;
import exceptions.DuplicateDocumentException;
import exceptions.InvalidDocumentException;
import model.customer.Customer;
import model.customer.Individual;
import model.customer.LegalEntity;
import repository.customer.CustomerRepository;
import enums.CustomerType;
import utils.Validator;

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

        if (existCustomer != null && existCustomer.getType()== CustomerType.INDIVIDUAL) {
            throw new DuplicateDocumentException("CPF já cadastrado!");
        }
        if (existCustomer != null && existCustomer.getType()== CustomerType.LEGALENTITY) {
            throw new DuplicateDocumentException("CNPJ já cadastrado!");
        }

        Customer newCustomer = null;

        String customerId = UUID.randomUUID().toString();

        if (customerDTO.type().equals(CustomerType.LEGALENTITY)) {
            if (Validator.isValidCnpj(customerDTO.documentId())) {
                newCustomer = new LegalEntity(
                        customerId,
                        customerDTO.name(),
                        customerDTO.numberPhone(),
                        Validator.sanitizeDocument(customerDTO.documentId()),
                        customerDTO.type()
                );
            } else {
                throw new InvalidDocumentException("CNPJ Inválido");
            }

        } else if (customerDTO.type().equals(CustomerType.INDIVIDUAL)) {
            if (Validator.isValidCpf(customerDTO.documentId())) {
                newCustomer = new Individual(
                        customerId,
                        customerDTO.name(),
                        customerDTO.numberPhone(),
                        Validator.sanitizeDocument(customerDTO.documentId()),
                        customerDTO.type()
                );
            } else {
                throw new InvalidDocumentException("CPF Inválido");
            }
        }

        if (newCustomer != null) {
            customerRepository.save(newCustomer);
            return newCustomer;
        }

        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        if(customer.getType().equals(CustomerType.LEGALENTITY) && !Validator.isValidCnpj(customer.getDocumentId())) {
            throw new InvalidDocumentException("CNPJ Inválido");
        }
        if(customer.getType().equals(CustomerType.INDIVIDUAL) && !Validator.isValidCpf(customer.getDocumentId())) {
            throw new InvalidDocumentException("CPF Inválido");
        }
        Customer existCustomer = customerRepository.findByDocument(customer.getDocumentId());
        if (existCustomer != null && !existCustomer.getId().equals(customer.getId())) {
            throw new DuplicateDocumentException("Documento já Cadastrado");
        }
        return customerRepository.update(customer);

    }


    @Override
    public boolean deleteCustomer(Customer customer) {
        Customer existCustomer = customerRepository.findByDocument(customer.getDocumentId());
        if (existCustomer == null) {
            throw new IllegalArgumentException("Esse cliente não existe");
        }
        customerRepository.delete(customer);
        return true;
    }

    @Override
    public Customer findCustomerById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Customer findCustomerByDocument(String document) {
        document = Validator.sanitizeDocument(document);
        return customerRepository.findByDocument(document);
    }

}
