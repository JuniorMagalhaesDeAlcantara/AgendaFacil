package br.com.agenda_facil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agenda_facil.model.Contato;

// @Repository identifica esta interface como responsável
// pelo acesso aos dados.
@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    // O JpaRepository já fornece os métodos necessários para o CRUD:
    //
    // save() -> cadastrar ou atualizar
    // findAll() -> listar todos
    // findById() -> buscar pelo ID
    // deleteById() -> excluir pelo ID
    // count() -> contar registros
}
