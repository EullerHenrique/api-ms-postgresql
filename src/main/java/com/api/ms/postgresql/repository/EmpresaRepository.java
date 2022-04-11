package com.api.ms.postgresql.repository;

import com.api.ms.postgresql.domain.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository

//É uma extensão específica de JPA de Repository. Ele contém a API completa de CrudRepository e
//PagingAndSortingRepository. Portanto, ele contém API para operações CRUD básicas e também API para paginação
//e classificação.

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
