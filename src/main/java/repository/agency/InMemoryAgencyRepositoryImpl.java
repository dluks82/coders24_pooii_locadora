package repository.agency;

import model.agency.Agency;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAgencyRepositoryImpl implements AgencyRepository {
    private static AgencyRepository instance;
    private final List<Agency> agencies;

    private InMemoryAgencyRepositoryImpl() {
        agencies = new ArrayList<Agency>();
    }

    public static AgencyRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryAgencyRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void saveData() {
    }

    @Override
    public Agency save(Agency agency) {
        agencies.add(agency);
        return agency;
    }

    @Override
    public Agency update(Agency agency) {
        Agency agencyUpdate = findById(agency.getId());
        if (agencyUpdate != null) {
            agencies.set(agencies.indexOf(agencyUpdate), agency);
        }
        return agencyUpdate;
    }

    @Override
    public Agency findById(String id) {
        for (Agency agency : agencies) {
            if (agency.getId().equals(id)) {
                return agency;
            }
        }
        return null;
    }

    @Override
    public List<Agency> findAll() {
        return agencies;
    }

    @Override
    public List<Agency> searchByName(String name) {
        List<Agency> temp = new ArrayList<>();
        for (Agency agency : agencies) {
            if (agency.getName().contains(name)) {
                temp.add(agency);
            }
        }
        return temp;
    }

    @Override
    public Agency findByName(String name) {
        for (Agency agency : agencies) {
            if (agency.getName().equals(name)) {
                return agency;
            }
        }
        return null;
    }
}
