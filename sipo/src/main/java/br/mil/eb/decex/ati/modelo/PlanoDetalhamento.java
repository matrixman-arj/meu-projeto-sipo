package br.mil.eb.decex.ati.modelo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Plano de detalhamento
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Entity
@Table(name="planodetalhamento", uniqueConstraints={@UniqueConstraint(columnNames={"codigo"})})
public class PlanoDetalhamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLANO_ID_GENERATOR", sequenceName="PLANO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLANO_ID_GENERATOR")	
	private Long id;

	@Column
	private String codigo;
	
	@Column
	private String nome;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="PLANO_NATUREZA", joinColumns=
			@JoinColumn(name="plano_id", referencedColumnName="id"), inverseJoinColumns=
			@JoinColumn(name="plano_natureza_id", referencedColumnName="id"), 
			uniqueConstraints={@UniqueConstraint(columnNames={"plano_id", "plano_natureza_id"})})	
	private Set<NaturezaDespesa> naturezas;
	
	@OneToMany(mappedBy="planoDetalhamento")
	private Set<ConfiguracaoPlano> configuracoes;
	
	public PlanoDetalhamento(){
		
	}

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária do plano de detalhamento
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
	 * Código de negócio que identifica o plano de detalhamento
	 * @return código de negócio
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Código de negócio que identifica o plano de detalhamento
	 * @param codigo código de negócio
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Nome do plano de detalhamento
	 * @return nome do plano de detalhamento
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Nome do plano de detalhamento
	 * @param nome do plano de detalhamento
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Conjunto de naturezas de despesa associadas ao 
	 * plano de detalhamento
	 * @return conjunto de naturezas de despesa
	 */
	public Set<NaturezaDespesa> getNaturezas() {
		return naturezas;
	}

	/**
	 * Conjunto de naturezas de despesa associadas ao 
	 * plano de detalhamento
	 * @param naturezas conjunto de naturezas de despesa
	 */
	public void setNaturezas(Set<NaturezaDespesa> naturezas) {
		this.naturezas = naturezas;
	}

	/**
	 * Retorna as naturezas de despesa em formato de lista
	 * @return lista de naturezas de despesa
	 */
	public List<NaturezaDespesa> getNaturezasList(){
		return new ArrayList<NaturezaDespesa>(naturezas);
	}
		
	
	/**
	 * Realiza a clonagem do objeto
	 * @param obj objeto a ser clonado
	 * @return nova instancia do objeto clonado
	 */
	public static Object cloneSerializable(Serializable obj) {
		
		ObjectOutputStream out = null;  
		ObjectInputStream in = null;  
    
		try {  
			ByteArrayOutputStream bout = new ByteArrayOutputStream();  
			out = new ObjectOutputStream(bout);  
                
			out.writeObject(obj);  
			out.close();  
                
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());  
			in = new ObjectInputStream(bin);              
			Object copy = in.readObject();  
    
			in.close();  
                
			return copy;  
		} catch (Exception ex) {  
			ex.printStackTrace();  
		} finally {  
			try {  
				if(out != null) {  
					out.close();  
				}  
                    
				if(in != null) {  
					in.close();  
				}  
			} catch (IOException ignore) {}  
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
		if (!(obj instanceof PlanoDetalhamento)) {
			return false;
		}
		PlanoDetalhamento other = (PlanoDetalhamento) obj;
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
		return "PlanoDetalhamento [codigo=" + codigo + "]";
	}	
}