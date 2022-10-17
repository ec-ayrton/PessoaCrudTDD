package com.ayrton.pessoacrud.repository;

import com.ayrton.pessoacrud.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);
    List<Pessoa> findByIdadeBetween(Long limiteInferior, Long limitesuperior);

}
