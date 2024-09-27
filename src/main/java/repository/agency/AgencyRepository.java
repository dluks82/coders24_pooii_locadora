package repository.agency;

import model.agency.Agency;
import repository.Repository;

import java.util.List;

public interface AgencyRepository extends Repository<Agency> {

    List<Agency> searchByName(String name);

    Agency findByName(String name);

}
