package br.mil.eb.decex.ati.controlador;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import br.mil.eb.decex.ati.modelo.AcaoOrcamentaria;
import br.mil.eb.decex.ati.modelo.ConfiguracaoExercicio;
import br.mil.eb.decex.ati.modelo.ConfiguracaoPlano;
import br.mil.eb.decex.ati.modelo.ExercicioOrcamentario;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.PlanoDetalhamento;
import br.mil.eb.decex.ati.servico.AcaoOrcamentariaService;
import br.mil.eb.decex.ati.servico.ExercicioOrcamentarioService;
import br.mil.eb.decex.ati.servico.OrganizacaoMilitarService;

/**
 * Controlador para view de Configurações de Exercícios. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@ViewScoped
@ManagedBean
public class ConfiguracaoExercicioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ExercicioOrcamentarioService service;

	@Inject
	private OrganizacaoMilitarService serviceOM;	

	@Inject
	private AcaoOrcamentariaService serviceAcao;
	
	private ConfiguracaoExercicio configuracaoExercicio;
	private ExercicioOrcamentario exercicioOrcamentario;
	private OrganizacaoMilitar superiorSelected;
	private AcaoOrcamentaria acaoSelected;
	private OrganizacaoMilitar subordinadoSelected;
	private ConfiguracaoExercicio configuracaoSelected;
	private List<PlanoDetalhamento> selectedPlanos;		
	private List<OrganizacaoMilitar> superiores;
	private List<AcaoOrcamentaria> acoes;
	private List<OrganizacaoMilitar> subordinados;
	private List<PlanoDetalhamento> planos;
	private List<ConfiguracaoExercicio> configuracoes;
	private boolean clone;
	private boolean habilitar;
	
	/**
	 * Inicializa listas de configurações de exercícios e 
	 * instancia objetos necessários, além de 
	 * realizar a limpeza de outros
	 */			
	@PostConstruct
	public void init() {
		
		//Instancia o model da página		
		configuracaoExercicio = new ConfiguracaoExercicio();
		
		//Carrega as listas de apoio da página		
		superiores = serviceOM.listBySuperiores();
		acoes = serviceAcao.findAll();
		
		if(exercicioOrcamentario == null)
			exercicioOrcamentario = getExercicio();
		
		configuracoes = service.listByExercicio(exercicioOrcamentario);		
			
		subordinadoSelected = null;
		subordinados = null;
		planos = null;
		
	}
	
	/*
	 * Recupera id do exercicio orçamentário passado via 
	 * request em query string da tela de exercício orçamentario 
	 * e cria o objeto de apoio de exercicio
	 */
	private ExercicioOrcamentario getExercicio() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext()
				.getRequestParameterMap();;
		
		String exercicioId = params.get("ano");
		
		ExercicioOrcamentario exercicio = service.findById(Long.valueOf(exercicioId));
		
		return exercicio;
	}
	
	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Configuração do Exercício
	 */	
	public ConfiguracaoExercicio getConfiguracaoExercicio() {
		return configuracaoExercicio;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param configuracaoExercicio Configuração do Exercício
	 */	
	public void setConfiguracaoExercicio(ConfiguracaoExercicio configuracaoExercicio) {
		this.configuracaoExercicio = configuracaoExercicio;
	}

	/**
	 * Exercício orçamentário que será configurado
	 * @return exercício orçamentário que será configurado
	 */
	public ExercicioOrcamentario getExercicioOrcamentario() {
		return exercicioOrcamentario;
	}
	
	/**
	 * Exercício orçamentário que será configurado
	 * @param exercicioOrcamentario exercício orçamentário que será configurado
	 */	
	public void setExercicioOrcamentario(ExercicioOrcamentario exercicioOrcamentario) {
		this.exercicioOrcamentario = exercicioOrcamentario;
	}

	/**
	 * Organização militar superior ao estabelecimento que está 
	 * sendo configurado.
	 * @return Organização Militar
	 */
	public OrganizacaoMilitar getSuperiorSelected() {
		return superiorSelected;
	}

	/**
	 * Organização militar superior ao estabelecimento que está 
	 * sendo configurado.
	 * @param superiorSelected Organização Militar
	 */	
	public void setSuperiorSelected(OrganizacaoMilitar superiorSelected) {
		this.superiorSelected = superiorSelected;
	}

	/**
	 * Ação orçamentária do estabelecimento que está 
	 * sendo configurado.
	 * @return Ação orçamentária
	 */	
	public AcaoOrcamentaria getAcaoSelected() {
		return acaoSelected;
	}

	/**
	 * Ação orçamentária do estabelecimento que está 
	 * sendo configurado.
	 * @param acaoSelected Ação orçamentária
	 */		
	public void setAcaoSelected(AcaoOrcamentaria acaoSelected) {
		this.acaoSelected = acaoSelected;
	}

	/**
	 * Organização militar que está sendo configurada.
	 * @return Organização Militar
	 */	
	public OrganizacaoMilitar getSubordinadoSelected() {
		return subordinadoSelected;
	}

	/**
	 * Organização militar que está sendo configurada.
	 * @param subordinadoSelected Organização Militar
	 */		
	public void setSubordinadoSelected(OrganizacaoMilitar subordinadoSelected) {
		this.subordinadoSelected = subordinadoSelected;
	}

	/**
	 * Objeto de apoio para detalhamento de configurações de exercícios 
	 * orçamentários
	 * @return configuração de exercício
	 */
	public ConfiguracaoExercicio getConfiguracaoSelected() {
		return configuracaoSelected;
	}

	/**
	 * Objeto de apoio para detalhamento de configurações de exercícios 
	 * orçamentários
	 * @param configuracaoSelected configuração de exercício
	 */	
	public void setConfiguracaoSelected(ConfiguracaoExercicio configuracaoSelected) {
		this.configuracaoSelected = configuracaoSelected;
	}

	/**
	 * Lista com planos de detalhamento selecionados para 
	 * configuração do exercício.
	 * @return lista de planos de detalhamento
	 */
	public List<PlanoDetalhamento> getSelectedPlanos() {
		return selectedPlanos;
	}

	/**
	 * Lista com planos de detalhamento selecionados para 
	 * configuração do exercício.
	 * @param selectedPlanos lista de planos de detalhamento
	 */
	public void setSelectedPlanos(List<PlanoDetalhamento> selectedPlanos) {
		this.selectedPlanos = selectedPlanos;
	}	
	
	/**
	 * Lista de organizações militares cadastradas no banco de dados,  
	 * enquadradas como Departamentos e/ou Diretorias 
	 * @return lista de organizações militares
	 */
	public List<OrganizacaoMilitar> getSuperiores() {
		return superiores;
	}

	/**
	 * Lista de ações orçamentárias cadastradas no banco de dados.
	 * @return lista de ações orçamentárias
	 */	
	public List<AcaoOrcamentaria> getAcoes() {
		return acoes;
	}
	
	/**
	 * Lista de organizações militares subordinadas 
	 * @return lista de organizações militares
	 */
	public List<OrganizacaoMilitar> getSubordinados() {
		return subordinados;
	}

	/**
	 * Lista de planos de detalhamento associados a uma 
	 * ação orçamentária
	 * @return lista de planos de detalhamento
	 */
	public List<PlanoDetalhamento> getPlanos() {
		return planos;
	}

	/**
	 * Lista de configurações de exercício orçamentário 
	 * cadastrado no banco de dados.
	 * @return
	 */
	public List<ConfiguracaoExercicio> getConfiguracoes() {
		return configuracoes;
	}

	/**
	 * Lista os subordinados do Departamento ou Diretoria  
	 * selecionado
	 * @param event evento ajax
	 */
	public void listSubordinados(AjaxBehaviorEvent event) {
		if(superiorSelected != null)
			subordinados = serviceOM.listBySubordinados(superiorSelected);
	}	
	
	/**
	 * Lista os planos de detalhamento associados a ação 
	 * orçamentária selecionada
	 * @param event evento ajax
	 */
	public void listPlanos(AjaxBehaviorEvent event) {
		if(acaoSelected != null)
			planos = acaoSelected.getPlanosList();
	}		
		
	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar detalhamentos nas configurações dos exercícios
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		
		if(clone){
			FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não é possível alterar registros com a função clone ativada", "Confirmação"));			
			return;
		}
		
		configuracaoExercicio = (ConfiguracaoExercicio) slc.getObject();
		superiorSelected = configuracaoExercicio.getSuperior();		
		subordinados = serviceOM.listBySubordinados(superiorSelected);
		acaoSelected = serviceAcao.findById(configuracaoExercicio.getAcaoOrcamentaria().getId());
		planos = acaoSelected.getPlanosList();
		selectedPlanos = configuracaoExercicio.getPlanosDetalhamento();
		subordinadoSelected = configuracaoExercicio.getEstabelecimentoEnsino();
	}		

	/**
	 * Realiza a persistência de tetos orçamentários
	 * @param event objeto selecionado na datatable
	 */
    public void onRowEdit(RowEditEvent event) {
        
        //Recupera o objeto alterado
        ConfiguracaoPlano cp = (ConfiguracaoPlano) event.getObject();
        service.addTetoOrcamentario(cp);
        
    }	
	
	/**
	 * Flag para controle de visibilidade do botão clonar 
	 * e da fieldset de seleção de planos de detalhamento
	 * @return flag booleana
	 */
	public boolean isClone() {
		return clone;
	}

	/**
	 * Flag para controle de visibilidade do botão clonar 
	 * e da fieldset de seleção de planos de detalhamento
	 * @param clone booleana
	 */
	public void setClone(boolean clone) {
		this.clone = clone;
	}
	
	/**
	 * Controle de flag
	 * @return controle de flag
	 */
	public boolean isHabilitar() {
		return habilitar;
	}

	/**
	 * Habilita ou não os campos necessários a funcionalidade 
	 * de clonagem. O clone apenas poderá ser realizado em um 
	 * exercício orçamentário que ainda não tenha sido configurado
	 * @param event evento ajax
	 */
	public void habilitaCampos(AjaxBehaviorEvent event) {
				
		habilitar = clone;
	
		if(habilitar){
			superiorSelected = null;
			init();
		}
	}			
	
	/**
	 * Realiza a clonagem do exercício anterior, facilitando o trabalho 
	 * de entrada de dados do usuário
	 * @return sucesso caso não ocorra uma exception
	 */
	public String clonar() {
		
		String message = "Configuração do Exercício clonada com sucesso";
		
		//Verifica se o exercício ainda não foi configurado
		if(configuracoes != null && configuracoes.size() > 0){
			FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Só é possível clonar exercícios que não possuam configurações", "Confirmação"));
			return null;							
		}
		
		try {
			
			service.clonarConfiguracaoAnterior(exercicioOrcamentario);
			init();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Confirmação"));
			return null;				
		}
			
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));		
		
		return "success";
	}
	
	/**
	 * Persiste uma configuração de exercício no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */
	public String salvar() {
		
		String message = (configuracaoExercicio.getId() == null ? 
				"Registro incluído com sucesso." : "Registro alterado com sucesso.");
			
		try {
						
			configuracaoExercicio.setPlanosDetalhamento(selectedPlanos);
			configuracaoExercicio.setAcaoOrcamentaria(acaoSelected);
			configuracaoExercicio.setEstabelecimentoEnsino(subordinadoSelected);
			configuracaoExercicio.setSuperior(superiorSelected);
			configuracaoExercicio.setExercicio(exercicioOrcamentario);
			
			service.addConfiguracao(configuracaoExercicio);
			
		} catch(Exception e) {		
			FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Confirmação"));
			return null;
		}
		
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));		
		
		//Atualiza estado da tela
		subordinadoSelected = null;
		selectedPlanos = null;
		configuracaoExercicio = new ConfiguracaoExercicio();
		configuracoes = service.listByExercicio(exercicioOrcamentario);
				
		return "success";
	}		
	
	/**
	 * Exclui uma configuração de exercício do banco de dados
	 * @return sucesso ou falha
	 */		
	public String excluir() {
		
		String result = service.remove(configuracaoExercicio);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;
	}		
	
}