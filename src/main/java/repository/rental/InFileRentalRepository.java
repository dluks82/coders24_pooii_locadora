package repository.rental;

import data.DataPersistence;
import model.customer.Customer;
import model.rental.Rental;

import java.util.ArrayList;
import java.util.List;

public class InFileRentalRepository implements RentalRepository {
    private static RentalRepository instance;
    private List<Rental> rentals;

    private InFileRentalRepository() {
        loadData();
    }

    public static RentalRepository getInstance() {
        if (instance == null) {
            instance = new InFileRentalRepository();
        }
        return instance;
    }

    public void saveData(){
        DataPersistence.save(rentals, "rental-DB");
    }

    private void loadData(){
       rentals =  DataPersistence.load("rental-DB");
    }


    @Override
    public Rental save(Rental entity) {
        rentals.add(entity);
        saveData();
        return entity;
    }

    @Override
    public Rental update(Rental entity) {
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getId().equals(entity.getId())) {
                rentals.set(i, entity);
                saveData();
                return entity;
            }
        }
        return null;
    }

    @Override
    public Rental findById(String id) {
        for (Rental r : rentals) {
            if (r.getId().equals(id))
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
        for (Rental r : rentals) {
            if (r.getCustomer() == customer)
                rentalsFound.add(r);
        }
        return rentalsFound;
    }

    @Override
    public List<Rental> findOpenRentals() {
        List<Rental> openRentals = new ArrayList<>();
        for (Rental r : rentals) {
            if (r.getActualReturnDate() == null)
                openRentals.add(r);
        }
        return openRentals;
    }

    @Override
    public List<Rental> findClosedRentals() {
        List<Rental> closedRentals = new ArrayList<>();
        for (Rental r : rentals) {
            if (r.getActualReturnDate() != null)
                closedRentals.add(r);
        }
        return closedRentals;
    }

}