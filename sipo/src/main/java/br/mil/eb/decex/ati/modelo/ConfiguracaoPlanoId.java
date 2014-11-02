package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Classe de identificação do relacionamento entre 
 * configuração do exercício e os planos de detalhamento
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Embeddable
public class ConfiguracaoPlanoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch=FetchType.LAZY)
	private ConfiguracaoExercicio configuracaoExercicio;
	
	@ManyToOne(fetch=FetchType.LAZY)	
	private PlanoDetalhamento planoDetalhamento;
	
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
		ConfiguracaoPlanoId other = (ConfiguracaoPlanoId) obj;
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
		return "ConfiguracaoPlanoId [configuracaoExercicio="
				+ configuracaoExercicio + ", planoDetalhamento="
				+ planoDetalhamento + "]";
	}

}