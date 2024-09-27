package service.agency;

import dto.CreateAgencyDTO;
import model.agency.Agency;

import java.util.List;

public interface AgencyService {

    Agency createAgency(CreateAgencyDTO agencyDTO);

    Agency updateAgency(Agency agency);

    Agency findAgencyById(String id);

    List<Agency> findAllAgencies();

    List<Agency> findAgencyByName(String name);

}
