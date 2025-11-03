package dcc.banqueservice.web;

import dcc.banqueservice.dto.RequestCompteDto;
import dcc.banqueservice.dto.ResponseCompteDTo;
import dcc.banqueservice.service.CompteServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// information général sur API de microservice
@OpenAPIDefinition(
        info = @Info(
                title = "Gestion des comptes bancaire",
                description = "cette offre tous les méthodes pour gérer les comptes",
                version = "1.0.0"
        ),
        servers = @Server(
                url = "http://localhost:8081"
        )
)

@RestController
@RequestMapping("/v1/comptes")
public class ApiResfull {

    private CompteServiceImpl compteService;

    public ApiResfull(CompteServiceImpl compteService) {
        this.compteService = compteService;
    }
// documentation de chaque méthode.
    @Operation(
            summary = " Ajouter un compte",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RequestCompteDto.class )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "bien enregiter",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCompteDTo.class )
                    )
                    ),

                    @ApiResponse(responseCode = "4xx",description = "erreur client"),
                    @ApiResponse(responseCode = "5xx",description = "erreur serveur"),
            }
    )

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseCompteDTo> add(@RequestBody RequestCompteDto requestCompteDto){
        ResponseCompteDTo responseCompteDTo = compteService.Add_Compte(requestCompteDto);
        return ResponseEntity.ok(responseCompteDTo);
    }
// NB : Ici la manière de retourné une liste
    @Operation(
            summary = " récuperer liste des compte",

            responses = {
                    @ApiResponse(responseCode = "200", description = "bien enregiter",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ResponseCompteDTo.class ))
                            )
                    ),
                    @ApiResponse(responseCode = "4xx",description = "erreur client"),
                    @ApiResponse(responseCode = "5xx",description = "erreur serveur"),
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ResponseCompteDTo>> getall(){
        List<ResponseCompteDTo> compteDTos = compteService.GetAllCompte();
        return ResponseEntity.ok(compteDTos);

    }

    @Operation(
            summary = " récupérer compte par Id",
            parameters = @Parameter(name = "id", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "bien récuperer",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseCompteDTo.class )
                            )
                    ),
                    @ApiResponse(responseCode = "4xx",description = "erreur client"),
                    @ApiResponse(responseCode = "5xx",description = "erreur serveur"),
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCompteDTo> get(@PathVariable Integer id){
        ResponseCompteDTo responseCompteDTo = compteService.GetCompteByID(id);
        return ResponseEntity.ok(responseCompteDTo);

    }
    // la méthode prendre paramétre et contenue dans body
    @Operation(
            summary = " Modifier un compte",
            parameters = @Parameter(name = "id", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RequestCompteDto.class )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "bien modifier",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseCompteDTo.class )
                            )
                    ),

                    @ApiResponse(responseCode = "4xx",description = "erreur client"),
                    @ApiResponse(responseCode = "5xx",description = "erreur serveur"),
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseCompteDTo> update(@PathVariable Integer id,@RequestBody RequestCompteDto requestCompteDto){
        ResponseCompteDTo responseCompteDTo = compteService.Update_Compte(id, requestCompteDto);
        return ResponseEntity.ok(responseCompteDTo);
    }

    // delete retoune Rien !!!
    @Operation(
            summary = " supprimer compte par Id",
            parameters = @Parameter(name = "id", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "bien supprimer"),
                    @ApiResponse(responseCode = "4xx",description = "erreur client"),
                    @ApiResponse(responseCode = "5xx",description = "erreur serveur"),
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        compteService.DeleteCompteByID(id);
        return ResponseEntity.ok().build();
    }
// methode prendre deux paramatre
    @Operation(
            summary = " cette methode pour créditer un compte",
            parameters = {
                    @Parameter(name = "id",required = true),
                    @Parameter(name = "m", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = " Solde bien modifier",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseCompteDTo.class )
                            )
                    ),
                    @ApiResponse(responseCode = "4xx",description = "erreur client"),
                    @ApiResponse(responseCode = "5xx",description = "erreur serveur"),
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PatchMapping("/crediter/{id}/{m}")
    ResponseEntity<ResponseCompteDTo> crediter(@PathVariable Integer id, @PathVariable Double m){
        ResponseCompteDTo responseCompteDTo = compteService.Crediter(id, m);
        return ResponseEntity.ok(responseCompteDTo);
    }
    // methode prendre deux paramatre
    @Operation(
            summary = " cette methode pour debiter un compte",
            parameters = {
                    @Parameter(name = "id",required = true),
                    @Parameter(name = "m", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = " Solde bien modifier",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseCompteDTo.class )
                            )
                    ),
                    @ApiResponse(responseCode = "4xx",description = "erreur client"),
                    @ApiResponse(responseCode = "5xx",description = "erreur serveur"),
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PatchMapping("/debiter/{id}/{m}")
    ResponseEntity<ResponseCompteDTo> debiter(@PathVariable Integer id, @PathVariable Double m){
        ResponseCompteDTo responseCompteDTo = compteService.Debiter(id, m);
        return ResponseEntity.ok(responseCompteDTo);
    }





}
