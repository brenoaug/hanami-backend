package com.recode.hanami.repository;

import com.recode.hanami.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, String> {
    
    @Query("SELECT v FROM Venda v LEFT JOIN FETCH v.produto LEFT JOIN FETCH v.cliente LEFT JOIN FETCH v.vendedor")
    List<Venda> findAllWithRelations();
}