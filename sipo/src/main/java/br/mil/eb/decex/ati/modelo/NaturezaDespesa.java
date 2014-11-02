package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.text.ParseException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.swing.text.MaskFormatter;

/**
 * Natureza de Despesa. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Entity
@Table(name="naturezadespesa", uniqueConstraints=@UniqueConstraint(columnNames={"codigo"}))
public class NaturezaDespesa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NATUREZA_ID_GENERATOR", sequenceName="NATUREZA_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NATUREZA_ID_GENERATOR")	
	private Long id;
	
	@Column
	private Integer grupo;
	
	@Column
	private Integer codigo;
	
	@Column
	private String descricao;

	public NaturezaDespesa(){
		
	}

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da natureza de despesa
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
	 * Grupo de despesa ou investimento da natureza de despesa
	 * @return grupo de despesa
	 */
	public Integer getGrupo() {
		return grupo;
	}

	/**
	 * Grupo de despesa ou investimento da natureza de despesa
	 * @param grupo grupo de despesa
	 */
	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	/**
	 * Código de negócio que identifica a natureza de despesa
	 * @return código de negócio
	 */
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * Código de negócio que identifica a natureza de despesa
	 * @param codigo código de negócio
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Descrição da natureza de despesa
	 * @return descrição da natureza de despesa
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Descrição da natureza de despesa
	 * @param descricao descrição da natureza de despesa
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Formatador do código de negócio, inclui a máscara ##.##.##.##
	 * @return código de negócio devidamente formatado
	 */
	public String getCodigoFormatado() {
				
		if(codigo != null && codigo.toString().length() == 8){
			
			MaskFormatter mask = null;
			
			try {
				mask = new MaskFormatter("##.##.##.##");
				mask.setValueContainsLiteralCharacters(false);
				return mask.valueToString(codigo);
			} catch (ParseException e) {
				e.printStackTrace();
			}		
			
		}
			
		return null;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NaturezaDespesa)) {
			return false;
		}
		NaturezaDespesa other = (NaturezaDespesa) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "NaturezaDespesa [codigo=" + codigo + ", descricao=" + descricao
				+ "]";
	}	
}