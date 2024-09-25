package repository;

import model.agency.Agency;

import java.util.List;

public interface AgencyRepository extends Repository<Agency> {

    List<Agency> searchByName(String name);

    Agency findByName(String name);

    List<Agency> findByAddress(String address);
}
