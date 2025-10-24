package dcc.banqueservice.mappers;

import dcc.banqueservice.dto.RequestCompteDto;
import dcc.banqueservice.dto.ResponseCompteDTo;
import dcc.banqueservice.entities.Compte;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompteMapper {

    public Compte DTO_to_Entity(RequestCompteDto requestCompteDto){
        Compte compte = new Compte();
        BeanUtils.copyProperties(requestCompteDto,compte);
        return compte;
    }

    public ResponseCompteDTo Entity_to_DTO(Compte compte){
        ResponseCompteDTo responseCompteDTo= new ResponseCompteDTo();
        BeanUtils.copyProperties(compte,responseCompteDTo);
        return responseCompteDTo;
    }

}
