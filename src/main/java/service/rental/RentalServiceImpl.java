package service.rental;

import java.util.List;
import java.util.UUID;

import dto.CreateRentalDTO;
import model.customer.Customer;
import model.rental.Rental;
import repository.rental.RentalRepository;

public class RentalServiceImpl implements RentalService {

    private RentalRepository rentalRepository;

    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Rental createRental(CreateRentalDTO rentalDTO) {
        String rentalId = UUID.randomUUID().toString();
        Rental newRental = null;
        /*
        LÓGICA DE CUSTOMER ALUGAR SOMENTE 1 CARRO NÃO APLICADA
        */
        newRental = new Rental(
              rentalId
            , rentalDTO.customer()
            , rentalDTO.vehicle()
            , rentalDTO.pickUpAgency()
            , rentalDTO.pickUpDate()
            , null
            , rentalDTO.estimatedReturnDate()
            , null
        );
        if (newRental != null) {
            rentalRepository.save(newRental);
            return newRental;
        }
        return null;
    }

    @Override
    public Rental updateRental(Rental rental) {
        Rental existingRental = rentalRepository.findById(rental.getId());
        if(existingRental==null)
            throw new IllegalArgumentException("Locacao nao existe!");
        rentalRepository.update(rental);
        return rental;
    }

    @Override
    public boolean deleteRental(Rental rental) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRental'");
    }

    @Override
    public Rental findRentalById(String id) {
        if(id.isEmpty() || id.equals(null)) {
            throw new IllegalArgumentException("ID vazio ou nulo");
        }
        return rentalRepository.findById(id);
    }

    @Override
    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public List<Rental> findRentalByCustomer(Customer customer) {
        if(customer == null)
            throw new IllegalArgumentException("Custumer is null");
        return rentalRepository.findByCustomer(customer);
    }

    @Override
    public List<Rental> findOpenRentals() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOpenRentals'");
    }
}
