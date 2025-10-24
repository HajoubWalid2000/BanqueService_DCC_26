package dcc.banqueservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ResponseCompteDTo {
    private Integer id;
    private  String nom;
    private  String tel;
    private  Double solde;

}
