package com.ciberbank.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "solicitud")
public class Solicitud {
	@Id
	private int codigo;
	private String nombre;
	private String apellido;
	private Double monto;
	private int cuotas;
	private Date fchregistro;
	private int idtipo;
	
	@ManyToOne
	@JoinColumn(name = "idtipo", insertable = false, updatable = false)
	private Tipo Objtipo;
}
