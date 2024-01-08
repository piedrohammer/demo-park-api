package com.hammer.demoparkapi.web.controllers;

import com.hammer.demoparkapi.entities.Vaga;
import com.hammer.demoparkapi.services.VagaService;
import com.hammer.demoparkapi.web.dtos.VagaCreateDTO;
import com.hammer.demoparkapi.web.dtos.VagaResponseDTO;
import com.hammer.demoparkapi.web.dtos.mappers.VagaMapper;
import com.hammer.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@Tag(name = "Vagas", description = "Contém todas as opereções relativas ao recurso de uma vaga")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

    private final VagaService vagaService;

    @Operation(summary = "Criar uma nova vaga", description = "Recurso para criar uma nova vaga." +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado")),
                    @ApiResponse(responseCode = "409", description = "Vaga já cadastrada",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDTO dto) {
        Vaga vaga = VagaMapper.toVaga(dto);
        vagaService.salvar(vaga);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(vaga.getCodigo())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Localizar uma vaga", description = "Recurso para retornar uma vaga pelo seu código" +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = VagaResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Vaga não localizada",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDTO> getByCodigo(@PathVariable String codigo) {
        Vaga vaga = vagaService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}

/*
Agora nós vamos retornar o cabeçalho, por isso o corpo vai ser vazio
o nosso método Create  tem que pegar o DTO e transformar em um objeto do tipo vaga, então declaro uma variável do tipo vaga.
e a partir de VagaMapper eu acesso o método toVaga, passando o dto como parâmetro
Depois o próximo passo é salvar a vaga.
Então vamos acessar vagaService e passando o objeto vaga como parâmetro. -> vagaService.salvar(vaga)
se não for lançada aquela exceção, é porque a vaga foi salva no banco de dados com sucesso.
Agora, então gerar a estrutura que vai criar a URI, que vai ser incluída no cabeçalho Location.
Para isso.
Vamos utilizar uma variável do tipo o URI, que é do pacote Java Net.
Eu vou nomear como location.
É a partir de uma classe do Spring chamada ServletUriComponentsBuilder.
Nós vamos gerar essa URI que vai fazer parte do cabeçalho.
Então nessa classe tem vários métodos e nós vamos utilizar o .fromCurrentRequestUri()
Esse método aqui faz o quê?
Ele recupera a URI que foi usada para acessar o recurso que você está trabalhando.
Então, o recurso Create utiliza a URL api/v1/vagas.
Então esse método vai recuperar essa URI e nós vamos concatenar a essa URL o código de acesso a essa vaga.
Para isso a gente acessa o método path e um novo caminho vai ser incluído nessa URL que vai ser o caminho /{código}.
Essa parte chaves código é um parâmetro e agora vai dizer qual o valor deve ser incluído nesseparâmetro.
Para isso.
Usamos o método buildAndExpand
e a partir de vaga acessamos o método getCodigo
Assim o código da vaga vai ser incluído na URI.
Para finalizar, temos que transformar essa operação em um objeto do tipo URI.
Para isso usamos toUri
Ok E agora na instrução de retorno do método.
Vamos usar o ResponseEntity.created(location).build();
Veja que o método Created espera por um objeto do tipo URI.
Esse objeto vai ser na verdade o nosso location.
Dessa forma, o Spring adiciona esse location como cabeçalho da resposta dessa requisição e esse cabeçalho
vai conter a URL de acesso à vaga que foi criada.
Para acessar essa vaga, nós vamos usar um novo método.
Que vai ser do tipo get.
Que vai ter o caminho barra código.
Ele também terá permissão de acesso exclusiva para o administrador.
Eu vou nomear o método como getbyCodigo.
Dessa vez o nosso retorno não vai ser Void e sim o VagaResponseDTO
E nós vamos ter a anotação @PathVariable
Como o argumento do método String codigo
Agora no corpo do método, vamos adicionar uma variável do tipo vaga.
Que vai receber um objeto vaga.
Vaga vaga = vagaService.buscarPorCodigo(codigo);
Vamos passar o código como parâmetro.
E agora, em ResponseEntity
Vamos utilizar o método ok
E vamos transformar a vaga em um ResponseDTO
ResponseEntity.ok(VagaMapper.toDto(vaga));
Passando a vaga como parâmetro.
* */