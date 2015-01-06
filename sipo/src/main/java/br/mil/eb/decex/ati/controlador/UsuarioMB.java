package br.mil.eb.decex.ati.controlador;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import br.mil.eb.decex.ati.enumerado.Posto;
import br.mil.eb.decex.ati.excecao.CPFDuplicadoException;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.SecaoAssessoria;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.servico.OrganizacaoMilitarService;
import br.mil.eb.decex.ati.servico.SecaoAssessoriaService;
import br.mil.eb.decex.ati.servico.UsuarioService;

/**
 * Controlador para view de Usuários. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@ViewScoped
@ManagedBean
public class UsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService service;
	
	@Inject
	private OrganizacaoMilitarService omService;
	
	@Inject
	private SecaoAssessoriaService secService;

	private Usuario usuario;
	private List<OrganizacaoMilitar> organizacoes;
	
	private List<SecaoAssessoria> secoes;
	
	@SuppressWarnings("unused")
	private List<Posto> postos;	
	
	private List<Usuario> usuarios;	
	private Usuario usuarioSelected;
	private OrganizacaoMilitar organizacaoSelected;
	private SecaoAssessoria secaoSelected;
	/**
	 * Inicializa listas de usuários e 
	 * instancia objetos necessários, além de 
	 * realizar a limpeza de outros
	 */			
	@PostConstruct
	public void init() {

		//Instancia o model da página		
		usuario = new Usuario();
		
		//Carrega as listas de apoio da página				
		organizacoes = omService.findAll();		
		usuarios = service.findAll();
		secoes = secService.findAll();
	

		//Limpa seleção anterior de organização militar		
		organizacaoSelected = null;
		
		//Limpa seleção anterior de seção assessoria		
		secaoSelected = null;
		
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Usuário
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param usuario Usuário
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Lista de planos de organizações militares
	 * @return lista de organizações militares
	 */
	public List<OrganizacaoMilitar> getOrganizacoes() {
		return organizacoes;
	}
	
	/**
	 * Lista de planos de seções assessorias
	 * @return lista de seções assessorias
	 */
	public List<SecaoAssessoria> getSecoes() {
		return secoes;
	}	
	
	/**
	 * Lista de postos e graduações dos militares
	 * @return lista de postos e graduações
	 */
	public List<Posto> getPostos() {
		return Arrays.asList(Posto.values());
	}
		
	/**
	 * Lista de ações usuarios cadastrados no  
	 * banco de dados
	 * @return lista de usuários
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
		
	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Usuários
	 * @return Usuario
	 */
	public Usuario getUsuarioSelected() {
		return usuarioSelected;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Usuários
	 * @param usuarioSelected Usuario
	 */
	public void setUsuarioSelected(Usuario usuarioSelected) {
		this.usuarioSelected = usuarioSelected;
	}
	
	/**
	 * Organização Militar em que o militar serve
	 * @return organização militar
	 */
	public OrganizacaoMilitar getOrganizacaoSelected() {
		return organizacaoSelected;
	}
	
	/**
	 * Seção Assessoria em que o militar trabalha
	 * @return seção assessoria
	 */
	public SecaoAssessoria getSecaoSelected() {
		return secaoSelected;
	}

	/**
	 * Organização Militar em que o militar serve
	 * @return organizacaoSelected organização militar
	 */
	public void setOrganizacaoSelected(OrganizacaoMilitar organizacaoSelected) {
		this.organizacaoSelected = organizacaoSelected;
	}
	
	/**
	 * Seção Assessoria em que o militar trabalha
	 * @return secaoSelected seção assessoria
	 */
	public void setSecaoSelected(SecaoAssessoria secaoSelected) {
		this.secaoSelected = secaoSelected;
	}

	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar detalhamentos nos usuários
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		usuario = (Usuario) slc.getObject();
		organizacaoSelected = usuario.getOrganizacaoMilitar();
		secaoSelected = usuario.getSecaoAssessoria();
	}	
	
	/**
	 * Persiste um usuário no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */
	public String salvar() {
		
		String message = (usuario.getId() == null ? 
			"Registro incluído com sucesso." : "Registro alterado com sucesso.");
		
		try {
			usuario.setOrganizacaoMilitar(organizacaoSelected);
			usuario.setSecaoAssessoria(secaoSelected);
			service.saveOrUpdate(usuario);
		} catch (CPFDuplicadoException e) {
			FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Confirmação"));
			return null;
		}
			
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
			new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return "success";
	}	
		
	/**
	 * Exclui uma ação orçamentária do banco de dados
	 * @return sucesso ou falha
	 */			
	public String excluir() {
		
		String result = service.remove(usuario);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;
	}	
}