package com.hammer.demoparkapi.web.controllers;

import com.hammer.demoparkapi.web.dtos.UsuarioCreateDTO;
import com.hammer.demoparkapi.web.dtos.UsuarioResponseDTO;
import com.hammer.demoparkapi.web.dtos.UsuarioSenhaDTO;
import com.hammer.demoparkapi.web.dtos.mappers.UsuarioMapper;
import com.hammer.demoparkapi.entities.Usuario;
import com.hammer.demoparkapi.web.exception.ErrorMessage;
import com.hammer.demoparkapi.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas ao recurso para cadastro, edição e leitura de um usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;



    // ResponseEntity Encapsula o objeto e transforma em json
    @Operation(summary = "Criar um novo usuario", description = "Recurso para cria um novo usuário.",
                responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "E-mail já cadastrado no sistema!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados inseridos inválidos!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
                }
    )
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create (@Valid @RequestBody UsuarioCreateDTO createDTO){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }

    @Operation(summary = "Buscar um usuário pelo id", description = "Recurso para fazer uma busca de um usuário pelo id. Requisição exige um Bearer Token. Acesso restrito para ADMIN|CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso realizado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "O Usuario não tem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENTE') AND #id == authentication.principal.id)")
    public ResponseEntity<UsuarioResponseDTO> getById (@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }

    @Operation(summary = "Atualizar senha", description = "Requisição exige um Bearer Token. Acesso restrito para ADMIN|CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "O Usuario não tem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos invalidos ou mal formatados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDTO dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar todos os usuários", description = "Recurso para fazer a busca de todos os usuários cadastrados. Requisição exige um Bearer Token. Acesso restrito para ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrado!",
                            content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))),
                    @ApiResponse(responseCode = "403", description = "O Usuario não tem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> getAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDTO(users));
    }


}
