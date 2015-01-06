package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import br.mil.eb.decex.ati.enumerado.TipoSecaoAssessoria;

/**
 * Seção Assessoria. Pode ser uma Assessoria, Seção ou 
 * Subseção. Pode representar uma subseção que faz pedido de 
 * de equipamento ou manutenção do mesmo.
 * 
 * @author <b>Vanilton</b> Gomes dos Santos - 3° Sgt QE
 * @version 1.0
 */
@Entity
@Table(name="secaoassessoria", uniqueConstraints={@UniqueConstraint(columnNames={"codigo"})})
public class SecaoAssessoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SECAO_ID_GENERATOR", sequenceName="SECAO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SECAO_ID_GENERATOR")
	private Long id;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="SECSUPERIORES_SUBORDINADOS", joinColumns=
			@JoinColumn(name="secsuperior_id", referencedColumnName="id"), inverseJoinColumns=
			@JoinColumn(name="secsuperior_subordinado_id", referencedColumnName="id"), 
			uniqueConstraints={@UniqueConstraint(columnNames={"secsuperior_id", "secsuperior_subordinado_id"})})
	private List<SecaoAssessoria> listaSecSuperiores;
	
	@Column
	private String codigo;
	
	@Column
	private String sigla;

	@Column
	private String nome;

	@Enumerated(EnumType.STRING)
	private TipoSecaoAssessoria tipo;
	
	public SecaoAssessoria(){
		
	}

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da Seção ou Assessoria
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
	 * Código da seção ou assessoria gestora da seção ou subseção
	 * @param codigo da seção ou assessoria gestora da seção ou subseção
	 */
	public void setCodigo(String codigo){
		this.codigo = codigo;
	}
	
	/**
	 * Código da seção ou assessoria gestora da seção ou subseção
	 * @return codigo da seção ou assessoria gestora da seção ou subseção
	 */
	public String getCodigo(){
		return codigo;
	}	
	
	/**
	 * Sigla da organização militar
	 * @param sigla sigla da organização militar
	 */
	public void setSigla(String sigla){
		this.sigla = sigla;
	}
	
	/**
	 * Sigla da seção ou assessoria
	 * @return sigla da seção ou assessoria
	 */
	public String getSigla(){
		return sigla;
	}
	
	/**
	 * Nome da seção ou assessoria 
	 * @return nome da seção ou assessoria
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Nome da seção ou assessoria 
	 * @return nome da seção ou assessoria
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Identifica o tipo de seção ou assessoria. 
	 * Assessoria, Seção ou SubSeção
	 * @return tipo da seção ou assessoria
	 */
	public TipoSecaoAssessoria getTipo() {
		return tipo;
	}

	/**
	 * Identifica o tipo de seção ou assessoria. 
	 * Assessoria, Seção ou SubSeção
	 * @return tipo da seção ou assessoria
	 */
	public void setTipo(TipoSecaoAssessoria tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Lista de seções e assessorias superiores associadas a 
	 * esta seção ou subseção
	 * @return lista de seções e assessorias
	 */
	public List<SecaoAssessoria> getListaSecSuperiores() {
		return listaSecSuperiores;
	}

	/**
	 * Lista de seções e assessorias superiores associadas a 
	 * esta seção ou subseção
	 * @param listaSecSuperiores lista de seções e assessorias
	 */
	public void setListaSecSuperiores(List<SecaoAssessoria> listaSecSuperiores) {
		this.listaSecSuperiores = listaSecSuperiores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		if (!(obj instanceof SecaoAssessoria)) {
			return false;
		}
		SecaoAssessoria other = (SecaoAssessoria) obj;
		if (sigla == null) {
			if (other.sigla != null) {
				return false;
			}
		} else if (!sigla.equals(other.sigla)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SecaoAssessoria [sigla=" + sigla + "]";
	}
	
}