package service;

import dto.CreateAgencyDTO;
import model.agency.Agency;
import repository.AgencyRepository;

import java.util.List;

public class AgencyServiceImpl implements AgencyService {
    private AgencyRepository agencyRepository;
    public AgencyServiceImpl(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }




    @Override
    public Agency createAgency(CreateAgencyDTO agencyDTO) {
       //Agency existAgency = agencyRepository.findByName(agencyDTO.name());
        return null;
    }

    @Override
    public Agency updateAgency(Agency agency) {
        return null;
    }

    @Override
    public boolean deleteAgency(Agency agency) {
        return false;
    }

    @Override
    public Agency findAgencyById(String id) {
        return null;
    }

    @Override
    public List<Agency> findAllAgencies() {
        return List.of();
    }

    @Override
    public List<Agency> findAgencyByName(String name) {
        return List.of();
    }

    @Override
    public List<Agency> findAgencyByAddress(String address) {
        return List.of();
    }
}
