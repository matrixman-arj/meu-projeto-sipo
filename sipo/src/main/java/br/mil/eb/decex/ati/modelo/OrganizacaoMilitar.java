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
import br.mil.eb.decex.ati.enumerado.TipoOM;

/**
 * Organização Militar. Pode ser uma Diretoria, Departamento ou 
 * Estabelecimento de Ensino. Pode representar um Estabelecimentos de Ensino 
 * que faz seu planejamento or&ccedil;ament&aacute;rio.
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Entity
@Table(name="organizacaomilitar", uniqueConstraints={@UniqueConstraint(columnNames={"codigo"})})
public class OrganizacaoMilitar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OM_ID_GENERATOR", sequenceName="OM_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OM_ID_GENERATOR")
	private Long id;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="SUPERIORES_SUBORDINADOS", joinColumns=
			@JoinColumn(name="superior_id", referencedColumnName="id"), inverseJoinColumns=
			@JoinColumn(name="superior_subordinado_id", referencedColumnName="id"), 
			uniqueConstraints={@UniqueConstraint(columnNames={"superior_id", "superior_subordinado_id"})})
	private List<OrganizacaoMilitar> listaSuperiores;
	
	@Column
	private String codigo;
	
	@Column
	private String sigla;

	@Column
	private String nome;

	@Enumerated(EnumType.STRING)
	private TipoOM tipo;
	
	public OrganizacaoMilitar(){
		
	}

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da Organização Militar
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
	 * Código de unidade gestora da organização militar
	 * @param codigo codigo de unidade gestora da organização militar
	 */
	public void setCodigo(String codigo){
		this.codigo = codigo;
	}
	
	/**
	 * Código de unidade gestora da organização militar
	 * @return codigo de unidade gestora da organização militar
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
	 * Sigla da organização militar
	 * @return sigla da organização militar
	 */
	public String getSigla(){
		return sigla;
	}
	
	/**
	 * Nome da organização militar
	 * @return nome da organização militar
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Nome da organização militar
	 * @param nome nome da organização militar
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * Identifica o tipo de organização militar. 
	 * Departamento, Diretoria ou Estabelecimento de Ensino
	 * @return tipo da organização militar
	 */
	public TipoOM getTipo() {
		return tipo;
	}

	/**
	 * Identifica o tipo de organização militar. 
	 * Departamento, Diretoria ou Estabelecimento de Ensino	 
	 * @param tipo tipo da organização militar
	 */
	public void setTipo(TipoOM tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Lista de organizaçõe militares superiores associadas a 
	 * esta organização militar
	 * @return lista de organizações militares
	 */
	public List<OrganizacaoMilitar> getListaSuperiores() {
		return listaSuperiores;
	}

	/**
	 * Lista de organizaçõe militares superiores associadas a 
	 * esta organização militar
	 * @param listaSuperiores lista de organizações militares
	 */
	public void setListaSuperiores(List<OrganizacaoMilitar> listaSuperiores) {
		this.listaSuperiores = listaSuperiores;
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
		if (!(obj instanceof OrganizacaoMilitar)) {
			return false;
		}
		OrganizacaoMilitar other = (OrganizacaoMilitar) obj;
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
		return "OrganizacaoMilitar [sigla=" + sigla + "]";
	}
	
}