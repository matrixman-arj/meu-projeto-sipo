package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import br.mil.eb.decex.ati.enumerado.Posto;
import br.mil.eb.decex.ati.enumerado.SituacaoUsuario;
import br.mil.eb.decex.ati.validador.Cpf;

/**
 * Usuário do sistema SIPO. Realiza suas tarefas de acordo 
 * com o perfil atribuído. 
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_ID_GENERATOR", sequenceName="USUARIO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_ID_GENERATOR")
	private Long id;
	
	@Cpf
	@Column
	private String cpf;
	
	@Column
	private String nome;
	
	@Column
	private String email;
	
	@Column
	private String identidade;
	
	@Column
	private String nomeGuerra;
	
	@Enumerated(EnumType.STRING)
	private Posto postoGraduacao;
	
	@Column
	private String senha;
	
	@Enumerated(EnumType.STRING)
	private SituacaoUsuario situacao;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private OrganizacaoMilitar organizacaoMilitar;

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária do militar 
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
	 * CPF do usuário. Só pode existir um cpf ativo por usuário
	 * @return CPF do usuário
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * CPF do usuário. Só pode existir um cpf ativo por usuário
	 * @param cpf CPF do usuário
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * Nome completo do usuário
	 * @return nome completo do usuário
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Nome completo do usuário
	 * @param nome nome completo do usuário
	 */	
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * E-mail do usuário
	 * @return e-mail do usuário
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * E-mail do usuário
	 * @param email e-mail do usuário
	 */	
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Identidade do usuário
	 * @return identidade do usuário
	 */
	public String getIdentidade() {
		return identidade;
	}

	/**
	 * Identidade do usuário
	 * @param identidade identidade do usuário
	 */	
	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	/**
	 * Nome de guerra do usuário
	 * @return nome de guerra do usuário
	 */
	public String getNomeGuerra() {
		return nomeGuerra;
	}

	/**
	 * Nome de guerra do usuário
	 * @param nomeGuerra nome de guerra do usuário
	 */	
	public void setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
	}

	/**
	 * Posto ou graduação do usuário
	 * @return posto ou graduação
	 */
	public Posto getPostoGraduacao() {
		return postoGraduacao;
	}

	/**
	 * Posto ou graduação do usuário
	 * @param postoGraduacao posto ou graduação
	 */	
	public void setPostoGraduacao(Posto postoGraduacao) {
		this.postoGraduacao = postoGraduacao;
	}

	/**
	 * Senha do usuário
	 * @return senha do usuário
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Senha do usuário
	 * @param senha senha do usuário
	 */	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Organização Militar que usuário serve
	 * @return organização militar
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	/**
	 * Organização Militar que usuário serve
	 * @param organizacaoMilitar organização militar
	 */	
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	/**
	 * Situação do usuário <br/>
	 * @return situação do usuário
	 * @see br.mil.eb.decex.ati.enumerado.SituacaoExercicio
	 */
	public SituacaoUsuario getSituacao() {
		return situacao;
	}

	/**
	 * Situação do usuário <br/>
	 * @param situacao situação do usuário
	 * @see br.mil.eb.decex.ati.enumerado.SituacaoExercicio
	 */	
	public void setSituacao(SituacaoUsuario situacao) {
		this.situacao = situacao;
	}

	/**
	 * Criptografa senha do usuário com hash MD5
	 * @throws NoSuchAlgorithmException falha na criptografia da senha
	 */
	public void convertSenhaToMD5() throws NoSuchAlgorithmException {
		if(senha != null && !senha.isEmpty()) 
			senha = converterSenha(senha);
	}
	
	public boolean equalsSenha(String value) throws NoSuchAlgorithmException {
		
		if(converterSenha(value).equals(senha))
			return true;
		
		return false;
	}
	
	private String converterSenha(String value) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
		value = String.format("%32x", hash);		
		
		return value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime
				* result
				+ ((organizacaoMilitar == null) ? 0 : organizacaoMilitar
						.hashCode());
		result = prime * result
				+ ((situacao == null) ? 0 : situacao.hashCode());
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
		Usuario other = (Usuario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (organizacaoMilitar == null) {
			if (other.organizacaoMilitar != null)
				return false;
		} else if (!organizacaoMilitar.equals(other.organizacaoMilitar))
			return false;
		if (situacao != other.situacao)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [cpf=" + cpf + ", situacao=" + situacao
				+ ", organizacaoMilitar=" + organizacaoMilitar + "]";
	}

}