package com.ciberbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo")
public class Tipo {
	@Id
	private int idtipo;
	private String des_tipo;
}
