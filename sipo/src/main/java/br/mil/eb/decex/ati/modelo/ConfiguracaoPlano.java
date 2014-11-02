package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe de relacionamento entre configuração do exercício e os 
 * planos de detalhamento
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Entity
@IdClass(ConfiguracaoPlanoId.class)
public class ConfiguracaoPlano implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="configuracaoExercicio_id")
	private ConfiguracaoExercicio configuracaoExercicio;
	
	@Id
	@ManyToOne
	@JoinColumn(name="planoDetalhamento_id")	
	private PlanoDetalhamento planoDetalhamento;
	
	@Column
	private Double grupo3;
	
	@Column	
	private Double grupo4;
	
	@Column	
	private Double diariaPassagem;
	
	/**
	 * Configuração do exercício
	 * @see br.mil.eb.decex.ati.modelo.ConfiguracaoExercicio
	 * @return configuração do exercício
	 */	
	public ConfiguracaoExercicio getConfiguracaoExercicio() {
		return configuracaoExercicio;
	}
	
	/**
	 * Configuração do exercício
	 * @see br.mil.eb.decex.ati.modelo.ConfiguracaoExercicio
	 * @param configuracaoExercicio configuração do exercício
	 */		
	public void setConfiguracaoExercicio(ConfiguracaoExercicio configuracaoExercicio) {
		this.configuracaoExercicio = configuracaoExercicio;
	}
	
	/**
	 * Plano de detalhamento
	 * @see br.mil.eb.decex.ati.modelo.PlanoDetalhamento
	 * @return plano de detalhamento
	 */		
	public PlanoDetalhamento getPlanoDetalhamento() {
		return planoDetalhamento;
	}
	
	/**
	 * Plano de detalhamento
	 * @see br.mil.eb.decex.ati.modelo.PlanoDetalhamento
	 * @param planoDetalhamento plano de detalhamento
	 */		
	public void setPlanoDetalhamento(PlanoDetalhamento planoDetalhamento) {
		this.planoDetalhamento = planoDetalhamento;
	}
	
	/**
	 * Teto orçamentário para o grupo 3
	 * @return teto para o grupo 3
	 */	
	public Double getGrupo3() {
		return grupo3;
	}
	
	/**
	 * Teto orçamentário para o grupo 3
	 * @param grupo3 teto para o grupo 3
	 */	
	public void setGrupo3(Double grupo3) {
		this.grupo3 = grupo3;
	}
	
	/**
	 * Teto orçamentário para o grupo 4
	 * @return teto para o grupo 4
	 */	
	public Double getGrupo4() {
		return grupo4;
	}
	
	/**
	 * Teto orçamentário para o grupo 4
	 * @param grupo4 teto para o grupo 4
	 */	
	public void setGrupo4(Double grupo4) {
		this.grupo4 = grupo4;
	}
	
	/**
	 * Teto orçamentário para diárias e passagens
	 * @return teto para diárias e passagens
	 */	
	public Double getDiariaPassagem() {
		return diariaPassagem;
	}
	
	/**
	 * Teto orçamentário para diárias e passagens
	 * @param diariaPassagem teto para diárias e passagens
	 */		
	public void setDiariaPassagem(Double diariaPassagem) {
		this.diariaPassagem = diariaPassagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((configuracaoExercicio == null) ? 0 : configuracaoExercicio
						.hashCode());
		result = prime
				* result
				+ ((planoDetalhamento == null) ? 0 : planoDetalhamento
						.hashCode());
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
		ConfiguracaoPlano other = (ConfiguracaoPlano) obj;
		if (configuracaoExercicio == null) {
			if (other.configuracaoExercicio != null)
				return false;
		} else if (!configuracaoExercicio.equals(other.configuracaoExercicio))
			return false;
		if (planoDetalhamento == null) {
			if (other.planoDetalhamento != null)
				return false;
		} else if (!planoDetalhamento.equals(other.planoDetalhamento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConfiguracaoPlano [configuracaoExercicio="
				+ configuracaoExercicio + ", planoDetalhamento="
				+ planoDetalhamento + "]";
	}
	
}