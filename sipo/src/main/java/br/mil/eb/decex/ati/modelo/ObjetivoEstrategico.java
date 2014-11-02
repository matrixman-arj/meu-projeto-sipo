package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Classe que representa os objetivos estratégicos definidos 
 * pelo DECEx. Cada estabelecimento de ensino deverá informar 
 * um objetivo estratégico para o recurso que está sendo 
 * utilizado.
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Entity
public class ObjetivoEstrategico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OBJESTRATEGICO_ID_GENERATOR", sequenceName="OBJESTRATEGICO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OBJESTRATEGICO_ID_GENERATOR")		
	private Long id;
	
	@Column
	private String descricao;

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária do objetivo estratégico
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Identificador de tabela. Código sequencial atribuído pela 
	 * sequence do Banco de Dados
	 * @param id Código sequencial
	 */	
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Descrição do objetivo estratégico
	 * @return descrição do objetivo estratégico
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Descrição do objetivo estratégico
	 * @param descricao descrição do objetivo estratégico
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ObjetivoEstrategico)) {
			return false;
		}
		ObjetivoEstrategico other = (ObjetivoEstrategico) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Objetivo Estrategico [descricao=" + descricao + "]";
	}
		
}