package dcc.banqueservice.service;

import dcc.banqueservice.dto.RequestCompteDto;
import dcc.banqueservice.dto.ResponseCompteDTo;

import java.util.List;

public interface CompteService {
    public ResponseCompteDTo Add_Compte(RequestCompteDto requestCompteDto);
    public List<ResponseCompteDTo> GetAllCompte();
    public ResponseCompteDTo GetCompteByID(Integer id);
    public ResponseCompteDTo Update_Compte(Integer id,RequestCompteDto requestCompteDto);
    public void DeleteCompteByID(Integer id);
    public ResponseCompteDTo Crediter(Integer id,Double m);
    public ResponseCompteDTo Debiter(Integer id,Double m);
}
