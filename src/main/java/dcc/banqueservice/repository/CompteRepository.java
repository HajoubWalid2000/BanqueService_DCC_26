package dcc.banqueservice.repository;

import dcc.banqueservice.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte,Integer> {

}
