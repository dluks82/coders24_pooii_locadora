package repository;

import model.agency.Agency;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAgencyRepositoryImpl implements AgencyRepository {
    private List<Agency> agencies;
    public InMemoryAgencyRepositoryImpl() {
        agencies = new ArrayList<Agency>();
    }




    @Override
    public List<Agency> findByName(String name) {
        List<Agency> temp = new ArrayList<>();
        for (Agency agency : agencies) {
            if(agency.getName().equals(name)) {
                temp.add(agency);
            }
        }
        return temp;
    }

    @Override
    public List<Agency> findByAddress(String address) {
        return List.of();
    }

    @Override
    public Agency save(Agency agency) {
        agencies.add(agency);
        return agency;
    }

    @Override
    public Agency update(Agency agency) {
        return null;
    }

    @Override
    public boolean delete(Agency agency) {
        agencies.remove(agency);
        return true;
    }

    @Override
    public Agency findById(String id) {
        return null;
    }

    @Override
    public List<Agency> findAll() {
        return List.of();
    }
}
