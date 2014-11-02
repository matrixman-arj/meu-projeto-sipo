package br.mil.eb.decex.ati.modelo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Configuração do Exercício orçamentário.
 * 
 * @author William <b>Moreira</b> de Pinho - 1° TEN QCO
 * @version 1.0
 */
@Entity
@Table(name="configuracaoexercicio", uniqueConstraints={
		@UniqueConstraint(columnNames={"exercicio_id", "estabelecimentoensino_id", "superior_id"})})
public class ConfiguracaoExercicio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONEXE_ID_GENERATOR", sequenceName="CONEXE_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONEXE_ID_GENERATOR")
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="exercicio_id")
	private ExercicioOrcamentario exercicio;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="superior_id")	
	private OrganizacaoMilitar superior;	
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="estabelecimentoensino_id")
	private OrganizacaoMilitar estabelecimentoEnsino;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private AcaoOrcamentaria acaoOrcamentaria;
	
	@OneToMany(mappedBy="configuracaoExercicio", cascade=CascadeType.ALL, orphanRemoval=true ,fetch=FetchType.EAGER)
	private List<ConfiguracaoPlano> planos;
	
	@Column
	private Double reservaGrupo3;
	
	@Column
	private Double reservaGrupo4;
	
	@Column
	private Double reservaDiariasPassagens;
		
	public ConfiguracaoExercicio(){
		planos = new ArrayList<ConfiguracaoPlano>();
	}

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da configuração do exercício
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
	 * Exercicio Orçamentário configurado
	 * @return exercício orçamentário
	 */
	public ExercicioOrcamentario getExercicio() {
		return exercicio;
	}

	/**
	 * Exercicio Orçamentário configurado
	 * @param exercicio exercício orçamentário
	 */	
	public void setExercicio(ExercicioOrcamentario exercicio) {
		this.exercicio = exercicio;
	}

	/**
	 * Diretoria ou Departamento do estabelecimento de ensino 
	 * que está sendo configurado
	 * @return organização militar
	 */
	public OrganizacaoMilitar getSuperior() {
		return superior;
	}

	/**
	 * Diretoria ou Departamento do estabelecimento de ensino 
	 * que está sendo configurado
	 * @param superior organização militar
	 */	
	public void setSuperior(OrganizacaoMilitar superior) {
		this.superior = superior;
	}

	/**
	 * Estabelecimento de Ensino que está sendo configurado 
	 * @return organização militar
	 */
	public OrganizacaoMilitar getEstabelecimentoEnsino() {
		return estabelecimentoEnsino;
	}

	/**
	 * Estabelecimento de Ensino que está sendo configurado 
	 * @param estabelecimentoEnsino organização militar
	 */	
	public void setEstabelecimentoEnsino(OrganizacaoMilitar estabelecimentoEnsino) {
		this.estabelecimentoEnsino = estabelecimentoEnsino;
	}

	/**
	 * Ação orçamentária da configuração
	 * @return ação orçamentaria
	 */
	public AcaoOrcamentaria getAcaoOrcamentaria() {
		return acaoOrcamentaria;
	}

	/**
	 * Ação orçamentária da configuração
	 * @param acaoOrcamentaria ação orçamentaria
	 */	
	public void setAcaoOrcamentaria(AcaoOrcamentaria acaoOrcamentaria) {
		this.acaoOrcamentaria = acaoOrcamentaria;
	}

	/**
	 * Reserva orçamentária para o grupo 3
	 * @return reserva orçamentária para o grupo 3
	 */
	public Double getReservaGrupo3() {
		return reservaGrupo3;
	}

	/**
	 * Reserva orçamentária para o grupo 3
	 * @param reservaGrupo3  reserva orçamentária para o grupo 3
	 */	
	public void setReservaGrupo3(Double reservaGrupo3) {
		this.reservaGrupo3 = reservaGrupo3;
	}

	/**
	 * Reserva orçamentária para o grupo 4
	 * @return reserva orçamentária para o grupo 4
	 */
	public Double getReservaGrupo4() {
		return reservaGrupo4;
	}

	/**
	 * Reserva orçamentária para o grupo 4
	 * @param reservaGrupo4  reserva orçamentária para o grupo 4
	 */	
	public void setReservaGrupo4(Double reservaGrupo4) {
		this.reservaGrupo4 = reservaGrupo4;
	}

	/**
	 * Reserva orçamentária para diárias e passagens
	 * @return Reserva orçamentária para diárias e passagens
	 */	
	public Double getReservaDiariasPassagens() {
		return reservaDiariasPassagens;
	}

	/**
	 * Reserva orçamentária para diárias e passagens
	 * @return reservaDiariasPassagens Reserva orçamentária para diárias e passagens
	 */	
	public void setReservaDiariasPassagens(Double reservaDiariasPassagens) {
		this.reservaDiariasPassagens = reservaDiariasPassagens;
	}
	
	/**
	 * Exibe teto orçamentário do grupo 3 em formato de moeda
	 * @return grupo 3 formatado como moeda
	 */
	public String getReservaGrupo3Formatado(){
		if(reservaGrupo3 != null)
			return NumberFormat.getCurrencyInstance().format(reservaGrupo3);
		
		return null;
	}
	
	/**
	 * Exibe teto orçamentário do grupo 4 em formato de moeda
	 * @return grupo 4 formatado como moeda
	 */	
	public String getReservaGrupo4Formatado(){
		if(reservaGrupo4 != null)
			return NumberFormat.getCurrencyInstance().format(reservaGrupo4);
		
		return null;
	}	
	
	/**
	 * Exibe teto orçamentário de diárias e passagens em formato de moeda
	 * @return diárias e passagens formatado como moeda
	 */	
	public String getReservaDiariasPassagensFormatado(){
		if(reservaDiariasPassagens != null)
			return NumberFormat.getCurrencyInstance().format(reservaDiariasPassagens);
		
		return null;
	}		
	
	/**
	 * Lista de relacionamento entre planos de detalhamento 
	 * e Configurações de Exercícios
	 * @return lista de relacionamentos plano x configuração
	 */
	public List<ConfiguracaoPlano> getPlanos() {
		return planos;
	}

	/**
	 * Lista de relacionamento entre planos de detalhamento 
	 * e Configurações de Exercícios
	 * @param planos lista de relacionamentos plano x configuração
	 */
	public void setPlanos(List<ConfiguracaoPlano> planos) {
		this.planos = planos;
	}

	/**
	 * Adiciona os planos de detalhamento relativos ao exercício orçamentário. 
	 * A configuração do teto orçamentário é feita em método próprio, uma vez 
	 * que para configuração dos tetos é necessário que as as configurações 
	 * do exercício estejam salvas préviamente.
	 * 
	 * @param lista lista de planos de detalhamento
	 */
	public void setPlanosDetalhamento(List<PlanoDetalhamento> lista) {
		
		if(lista == null || lista.size() == 0)
			return;
		
		ConfiguracaoPlano cp = null;
		
		planos.clear();
		
		/*
		 * Para cada plano de detalhamento será 
		 * criado uma instância do relacionamento 
		 * ConfiguracaoExercicio x PlanoDetalhamento
		 */
		for (PlanoDetalhamento pd : lista) {
			
			cp = new ConfiguracaoPlano();
			
			cp.setConfiguracaoExercicio(this);
			cp.setPlanoDetalhamento(pd);
			
			planos.add(cp);
		}
	}
	
	/**
	 * Retorna os planos de detalhamento para esta 
	 * configuração de exercício 
	 * @return planos de detalhamento
	 */
	public List<PlanoDetalhamento> getPlanosDetalhamento() {
		
		if(planos == null || planos.size() == 0)
			return null;
			
		List<PlanoDetalhamento> lista = 
				new ArrayList<PlanoDetalhamento>();
			
		for (ConfiguracaoPlano cp : planos) {
			PlanoDetalhamento p = (PlanoDetalhamento)PlanoDetalhamento.cloneSerializable(cp.getPlanoDetalhamento());
			lista.add(p);
		}
			
		return lista;
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
		result = prime
				* result
				+ ((estabelecimentoEnsino == null) ? 0 : estabelecimentoEnsino
						.hashCode());
		result = prime * result
				+ ((exercicio == null) ? 0 : exercicio.hashCode());
		result = prime * result
				+ ((superior == null) ? 0 : superior.hashCode());
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
		ConfiguracaoExercicio other = (ConfiguracaoExercicio) obj;
		if (estabelecimentoEnsino == null) {
			if (other.estabelecimentoEnsino != null)
				return false;
		} else if (!estabelecimentoEnsino.equals(other.estabelecimentoEnsino))
			return false;
		if (exercicio == null) {
			if (other.exercicio != null)
				return false;
		} else if (!exercicio.equals(other.exercicio))
			return false;
		if (superior == null) {
			if (other.superior != null)
				return false;
		} else if (!superior.equals(other.superior))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanejamentoExercicio [exercicio=" + exercicio + ", superior="
				+ superior + ", estabelecimentoEnsino=" + estabelecimentoEnsino
				+ "]";
	}	
}