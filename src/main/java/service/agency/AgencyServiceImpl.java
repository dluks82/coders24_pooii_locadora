package service.agency;

import dto.CreateAgencyDTO;
import model.agency.Agency;
import repository.agency.AgencyRepository;

import java.util.List;
import java.util.UUID;

public class AgencyServiceImpl implements AgencyService {

    private AgencyRepository agencyRepository;
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
            throw new IllegalArgumentException("Agency already exists");
        }

        if(newAgency != null) {
         return  agencyRepository.save(newAgency);

        }
        return null;
    }

    @Override
    public Agency updateAgency(Agency agency) {
        Agency existAgency = agencyRepository.findByName(agency.getName());
        if(existAgency == null) {
            throw new IllegalArgumentException("Agencia não Existe");
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

    @Override
    public List<Agency> findAgencyByAddress(String address) {
        return agencyRepository.findByAddress(address);
    }
}
