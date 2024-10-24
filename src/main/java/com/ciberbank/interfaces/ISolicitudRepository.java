package com.ciberbank.interfaces;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ciberbank.model.Solicitud;

@Repository
public interface ISolicitudRepository extends JpaRepository<Solicitud, Integer>{
	List<Solicitud> findByFchregistro(Date fchregistro);
}
