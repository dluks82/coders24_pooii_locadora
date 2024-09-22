package service;

import java.util.List;

import dto.CreateRentalDTO;
import model.customer.Customer;
import model.rental.Rental;
import repository.RentalRepository;

public class RentalServiceImpl implements RentalService{

    private RentalRepository rentalRepository;

    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Rental createRental(CreateRentalDTO rentalDTO) {
        Rental existRental = rentalRepository.findById(rentalDTO.id());
        if(existRental!=null) throw new IllegalArgumentException("Locadora já existe!");
        Rental newRental = null;
        newRental = new Rental(
              rentalDTO.id()
            , rentalDTO.customer()
            , rentalDTO.vehicle()
            , rentalDTO.pickUpAgency()
            , rentalDTO.pickUpDate()
            , rentalDTO.returnAgency()
            , rentalDTO.estimatedReturnDate()
            , rentalDTO.actualReturnDate()
        );
        if (newRental != null) {
            rentalRepository.save(newRental);
            return newRental;
        }
        return null;
    }

    @Override
    public Rental updateRental(Rental rental) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRental'");
    }

    @Override
    public boolean deleteRental(Rental rental) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRental'");
    }

    @Override
    public Rental findRentalById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRentalById'");
    }

    @Override
    public List<Rental> findAllRentals() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllRentals'");
    }

    @Override
    public List<Rental> findRentalByCustomer(Customer customer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRentalByCustomer'");
    }

    @Override
    public List<Rental> findOpenRentals() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOpenRentals'");
    }
}
