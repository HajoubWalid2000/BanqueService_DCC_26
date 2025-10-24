package dcc.banqueservice.service;

import dcc.banqueservice.dto.RequestCompteDto;
import dcc.banqueservice.dto.ResponseCompteDTo;
import dcc.banqueservice.entities.Compte;
import dcc.banqueservice.mappers.CompteMapper;
import dcc.banqueservice.repository.CompteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompteServiceImpl implements CompteService {
    private CompteRepository compteRepository;
    private CompteMapper compteMapper;

    public CompteServiceImpl(CompteRepository compteRepository, CompteMapper compteMapper) {
        this.compteRepository = compteRepository;
        this.compteMapper = compteMapper;
    }

    @Override
    public ResponseCompteDTo Add_Compte(RequestCompteDto requestCompteDto) {
        Compte compte = compteMapper.DTO_to_Entity(requestCompteDto);
        Compte saved_compte = compteRepository.save(compte);
        return compteMapper.Entity_to_DTO(saved_compte);
    }

    @Override
    public List<ResponseCompteDTo> GetAllCompte() {
        List<Compte> comptes = compteRepository.findAll();
        List<ResponseCompteDTo> compteDTos= new ArrayList<>(); // vide

        for (Compte c: comptes){
            compteDTos.add(compteMapper.Entity_to_DTO(c));
        }
        return compteDTos;
    }

    @Override
    public ResponseCompteDTo GetCompteByID(Integer id) {
        Compte compte = compteRepository.findById(id).orElseThrow();

        return compteMapper.Entity_to_DTO(compte);
    }

    @Override
    public ResponseCompteDTo Update_Compte(Integer id, RequestCompteDto requestCompteDto) {

        Compte new_compte = compteMapper.DTO_to_Entity(requestCompteDto);

        Compte compte = compteRepository.findById(id).orElseThrow();

        if(new_compte.getNom()!=null)compte.setNom(new_compte.getNom());
        if(new_compte.getTel()!=null)compte.setTel(new_compte.getTel());
        if(new_compte.getSolde()!=null)compte.setSolde(new_compte.getSolde());

        Compte saved_compte = compteRepository.save(compte);

        return compteMapper.Entity_to_DTO(saved_compte);

    }

    @Override
    public void DeleteCompteByID(Integer id) {
        compteRepository.deleteById(id);
    }

    @Override
    public ResponseCompteDTo Crediter(Integer id, Double m) {

        Compte compte = compteRepository.findById(id).orElseThrow();
        compte.setSolde( compte.getSolde()+ m);

        Compte saved = compteRepository.save(compte);
        return compteMapper.Entity_to_DTO(saved);
    }

    @Override
    public ResponseCompteDTo Debiter(Integer id, Double m) {
        Compte compte = compteRepository.findById(id).orElseThrow();

        if(compte.getSolde() >= m){
            compte.setSolde( compte.getSolde() - m);
        }
        Compte saved = compteRepository.save(compte);
        return compteMapper.Entity_to_DTO(saved);
    }
}
