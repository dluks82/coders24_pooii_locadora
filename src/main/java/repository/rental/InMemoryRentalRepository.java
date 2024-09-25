package repository.rental;

import java.util.ArrayList;
import java.util.List;

import model.customer.Customer;
import model.rental.Rental;

public class InMemoryRentalRepository implements RentalRepository {

    private List<Rental> rentals;

    public InMemoryRentalRepository() {
        this.rentals = new ArrayList<>();
    }

    @Override
    public Rental save(Rental entity) {
        rentals.add(entity);

        return entity;
    }

    @Override
    public Rental update(Rental entity) {
        for(int i=0; i<rentals.size(); i++) {
            if(rentals.get(i).getId().equals(entity.getId())) {
                rentals.set(i, entity);
                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Rental entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Rental findById(String id) {
        for(Rental r : rentals) {
            if(r.getId().equals(id))
                return r;
        }
        return null;
    }

    @Override
    public List<Rental> findAll() {
        return this.rentals;
    }

    @Override
    public List<Rental> findByCustomer(Customer customer) {
        ArrayList<Rental> rentalsFound = new ArrayList<>();
        for(Rental r : rentals) {
            if(r.getCustomer() == customer)
                rentalsFound.add(r);
        }
        return rentalsFound;
    }

    @Override
    public List<Rental> findOpenRentals() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOpenRentals'");
    }
    
}