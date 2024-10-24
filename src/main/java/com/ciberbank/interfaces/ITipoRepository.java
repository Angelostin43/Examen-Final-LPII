package com.ciberbank.interfaces;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ciberbank.model.Tipo;


@Repository
public interface ITipoRepository extends JpaRepository<Tipo, Integer> {

}