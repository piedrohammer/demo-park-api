package com.hammer.demoparkapi.web.dtos.mappers;

import com.hammer.demoparkapi.web.dtos.UsuarioCreateDTO;
import com.hammer.demoparkapi.web.dtos.UsuarioResponseDTO;
import com.hammer.demoparkapi.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toUsuario (UsuarioCreateDTO createDTO){
        return new ModelMapper().map(createDTO, Usuario.class);
    }
    /*
    eu vou ter que ter acesso à variável que está presente no objeto usuário e remover dessa variável
    a instrução Role underline para que eu insira no objeto dto apenas admin ou apenas cliente.
    Para fazer isso, vamos primeiro começar criando uma variável chamada role.
    Então vai ser uma variável do tipo string.
    E a gente vai acessar role a partir do objeto usuário.
    Porém o método getRole nos retorna um enum.
    O que eu quero é ter acesso a constante do enum no formato string, então eu posso utilizar esse método
    que tem em todos os enums, que é o método Name.
    Como o método name é uma string, então a gente pode utilizar o método substring.
    Para remover role underline, como eu faço a partir do método substring.
    Essa remoção eu indico índice a partir daquilo que eu quero que seja retornado.
    Então esse índice vai ser o valor da posição inicial, onde eu quero ter o valor retornado para a variável role.
    Eu posso fazer isso utilizando a string role underline.
    Eu pego o tamanho da string, então o valor do tamanho dessa string passa a ser o índice.
    E a partir daqui o substring faz o trabalho dele retornando.
    Se for, role underline admin será apenas admin.
    Se for, role underline cliente será apenas cliente.
    Só que eu preciso agora pegar o valor de role e inserir eu mesmo na variável role que a gente tem
    em responseDTO
    Para isso a gente vai utilizar uma operação com uma classe anônima da Model Mapper a partir de um objeto
    chamado PropertyMap.
    Nesse objeto a gente tem que passar um genérico referente a classe Fonte e a classe de destino
    a classe fonte é usuário e a classe de destido é UsuarioResponseDTO.
    Vou nomear a variável como props e criar a classe anônima.
    Aqui na classe anônima nós temos o método Configure.
    Nesse método é onde a gente vai colocar a operação.
    A gente acessa um método chamado map.
    E a partir do map a gente tem acesso aos campos presentes em o UsuarioResponseDTO.
    Vamos usar set role.
    Como setRole recebe um objeto do tipo String.
    A gente passa a string role que nós criamos na linha acima.

    *  */
    public static UsuarioResponseDTO toDTO (Usuario usuario){
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDTO> props = new PropertyMap<Usuario, UsuarioResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };

        /*
        criar uma variável do tipo Model Mapper.
        nomear como Mapper.
        E criar uma instância para Model Mapper agora a partir de Mapper eu acesso addMappings,
        Esse método do addMappings recebe um parâmetro do tipo PropertyMap, ou seja, aquela operação
        que nós criamos lá em Props vai ser adicionada na instância de Model Map.
        Então vamos passar aqui a variável propícia.
        Assim, quando o Model Mapper fizer a conversão de usuário para usuárioResponse, ele vai saber que
        quando ele chegar lá na parte referente a role, ele vai ter que usar o valor de role que nós setamos
        em Property Map.
        adicionamos o retorno mapper.map passando o usuário e UsuarioResponseDTO.class.
        * */
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    public static List<UsuarioResponseDTO> toListDTO(List<Usuario> usuarios){
        return usuarios.stream().map(user -> toDTO(user)).collect(Collectors.toList());
    }
}
