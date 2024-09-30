package repository.agency;

import data.DataPersistence;
import model.agency.Agency;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class InFileAgencyRepositoryImpl implements AgencyRepository {
    private List<Agency> agencies;


    public InFileAgencyRepositoryImpl() {
        loadData();
    }

    private void saveData(){
        DataPersistence.save(agencies, "agency-DB");
    }

    private void loadData(){
        agencies = DataPersistence.load("agency-DB");
    }


    @Override
    public Agency save(Agency agency) {
        agencies.add(agency);
        saveData();
        return agency;
    }

    @Override
    public Agency update(Agency agency) {
        Agency agencyUpdate = findById(agency.getId());
        if (agencyUpdate != null) {
            agencies.set(agencies.indexOf(agencyUpdate), agency);
        }
        saveData();
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
