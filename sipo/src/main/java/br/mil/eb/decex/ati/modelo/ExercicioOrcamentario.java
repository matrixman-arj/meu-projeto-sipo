package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import br.mil.eb.decex.ati.enumerado.SituacaoExercicio;

@Entity
@Table(name="exercicioorcamentario", uniqueConstraints={@UniqueConstraint(columnNames={"ano"})})
public class ExercicioOrcamentario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EXERCICIO_ID_GENERATOR", sequenceName="EXERCICIO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EXERCICIO_ID_GENERATOR")	
	private Long id;
	
	@Column
	private Integer ano;
	
	@Enumerated(EnumType.STRING)
	private SituacaoExercicio situacao;
	
	public ExercicioOrcamentario(){
		situacao = SituacaoExercicio.PENDENTE;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the ano
	 */
	public Integer getAno() {
		return ano;
	}

	/**
	 * @param ano the ano to set
	 */
	public void setAno(Integer ano) {
		this.ano = ano;
	}

	/**
	 * @return the situacao
	 */
	public SituacaoExercicio getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(SituacaoExercicio situacao) {
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
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
		if (!(obj instanceof ExercicioOrcamentario)) {
			return false;
		}
		ExercicioOrcamentario other = (ExercicioOrcamentario) obj;
		if (ano == null) {
			if (other.ano != null) {
				return false;
			}
		} else if (!ano.equals(other.ano)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Exercicio Orcamentario [ano=" + ano + "]";
	}
		
}