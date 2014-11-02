package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Ação Orçamentária. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Entity
@Table(name="acaoorcamentaria", uniqueConstraints={@UniqueConstraint(columnNames={"codigo"})})
public class AcaoOrcamentaria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACAO_ID_GENERATOR", sequenceName="ACAO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACAO_ID_GENERATOR")
	private Long id;
	
	@Column
	private String codigo;
	
	@Column
	private String nome;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="ACAO_PLANO", joinColumns=
			@JoinColumn(name="acao_id", referencedColumnName="id"), inverseJoinColumns=
			@JoinColumn(name="acao_plano_id", referencedColumnName="id"), 
			uniqueConstraints={@UniqueConstraint(columnNames={"acao_id", "acao_plano_id"})})	
	private Set<PlanoDetalhamento> planos;
	
	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da ação orçamentária 
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
	 * Código de negócio que identifica a ação orçamentária
	 * @return código de negócio
	 */	
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Código de negócio que identifica a ação orçamentária
	 * @param codigo código de negócio
	 */	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Nome da ação orçamentária
	 * @return nome da ação orçamentária
	 */	
	public String getNome() {
		return nome;
	}

	/**
	 * Nome da ação orçamentária
	 * @param nome nome da ação orçamentária
	 */	
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Conjunto de planos de detalhamento associados à 
	 * ação orçamentária
	 * @return conjunto de planos de detalhamento
	 */	
	public Set<PlanoDetalhamento> getPlanos() {
		return planos;
	}

	/**
	 * Conjunto de planos de detalhamento associados à 
	 * ação orçamentária
	 * @param planos conjunto de planos de detalhamento
	 */		
	public void setPlanos(Set<PlanoDetalhamento> planos) {
		this.planos = planos;
	}
	
	/**
	 * Retorna os planos de detalhamento em formato de lista
	 * @return lista de planos de detalhamento
	 */	
	public List<PlanoDetalhamento> getPlanosList() {
		return new ArrayList<PlanoDetalhamento>(planos);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcaoOrcamentaria other = (AcaoOrcamentaria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AcaoOrcamentaria [codigo=" + codigo + "]";
	}
}