package service;

import model.agency.Agency;

import java.util.List;

public interface AgencyService {

    Agency createAgency(Agency agency);

    Agency updateAgency(Agency agency);

    boolean deleteAgency(Agency agency);

    Agency findAgencyById(String id);

    List<Agency> findAllAgencies();

    List<Agency> findAgencyByName(String name);

    List<Agency> findAgencyByAddress(String address);

}