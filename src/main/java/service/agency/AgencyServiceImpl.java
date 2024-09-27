package service.agency;

import dto.CreateAgencyDTO;
import model.agency.Agency;
import repository.agency.AgencyRepository;

import java.util.List;
import java.util.UUID;

public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;
    public AgencyServiceImpl(AgencyRepository agencyRepository) {
           this.agencyRepository = agencyRepository;
    }


    @Override
    public Agency createAgency(CreateAgencyDTO agencyDTO) {
        Agency existAgency = agencyRepository.findByName(agencyDTO.name());

        Agency newAgency = null;

        String agencyId = UUID.randomUUID().toString();

        if(existAgency == null) {
                newAgency = new Agency(
                    agencyId,
                    agencyDTO.name(),
                    agencyDTO.address(),
                    agencyDTO.phone());
        }else{
            throw new IllegalArgumentException(String.format("Agência '%s' já existe", agencyDTO.name()));
        }

        return agencyRepository.save(newAgency);
    }

    @Override
    public Agency updateAgency(Agency agency) {
        Agency existAgency = agencyRepository.findById(agency.getId());
        if(existAgency == null) {
            throw new IllegalArgumentException("Agencia não Existe");
        }

        Agency agencyName = agencyRepository.findByName(agency.getName());
        if(agencyName != null && !agencyName.getId().equals(agency.getId())) {
            throw new IllegalArgumentException(String.format("Agência '%s' já existe", agency.getName()));
        }

        return agencyRepository.update(agency);
    }

    @Override
    public Agency findAgencyById(String id) {
        return agencyRepository.findById(id);
    }

    @Override
    public List<Agency> findAllAgencies() {
        return agencyRepository.findAll();
    }

    @Override
    public List<Agency> findAgencyByName(String name) {
        return agencyRepository.searchByName(name);
    }

}
