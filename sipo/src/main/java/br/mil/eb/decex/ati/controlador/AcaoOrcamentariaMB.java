package br.mil.eb.decex.ati.controlador;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import br.mil.eb.decex.ati.modelo.AcaoOrcamentaria;
import br.mil.eb.decex.ati.modelo.PlanoDetalhamento;
import br.mil.eb.decex.ati.servico.AcaoOrcamentariaService;
import br.mil.eb.decex.ati.servico.PlanoDetalhamentoService;

/**
 * Controlador para view de Ações Orçamentarias. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@ViewScoped
@ManagedBean
public class AcaoOrcamentariaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AcaoOrcamentariaService service;
	
	@Inject
	private PlanoDetalhamentoService planoService;
		
	private AcaoOrcamentaria acaoOrcamentaria;
	private List<PlanoDetalhamento> planos;
	private List<PlanoDetalhamento> selectedPlanos;	
	private List<AcaoOrcamentaria> acoes;	
	private AcaoOrcamentaria acaoSelected;

	/**
	 * Inicializa listas de ações orçamentarias e 
	 * instancia objetos necessários, além de 
	 * realizar a limpeza de outros
	 */		
	@PostConstruct
	public void init() {
		
		//Instancia o model da página		
		acaoOrcamentaria = new AcaoOrcamentaria();

		//Carrega as listas de apoio da página		
		planos = planoService.findAll();
		acoes = service.findAll();

		//Limpa seleção anterior de planos de detalhamento
		selectedPlanos = null;
		
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Ação Orçamentária
	 */
	public AcaoOrcamentaria getAcaoOrcamentaria() {
		return acaoOrcamentaria;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param acaoOrcamentaria Ação Orçamentária
	 */
	public void setAcaoOrcamentaria(AcaoOrcamentaria acaoOrcamentaria) {
		this.acaoOrcamentaria = acaoOrcamentaria;
	}

	/**
	 * Lista de planos de detalhamento
	 * @return lista de planos de detalhamento
	 */
	public List<PlanoDetalhamento> getPlanos() {
		return planos;
	}	
	
	/**
	 * Lista com planos de detalhamento selecionados para 
	 * cadastramento das ações orçamentárias.
	 * @return lista de planos de detalhamento
	 */
	public List<PlanoDetalhamento> getSelectedPlanos() {
		return selectedPlanos;
	}

	/**
	 * Lista com planos de detalhamento selecionados para 
	 * cadastramento das ações orçamentárias.
	 * @param selectedPlanos lista de planos de detalhamento
	 */
	public void setSelectedPlanos(List<PlanoDetalhamento> selectedPlanos) {
		this.selectedPlanos = selectedPlanos;
	}

	/**
	 * Lista de ações orçamentárias cadastrados no  
	 * banco de dados
	 * @return lista de ações orçamentárias
	 */
	public List<AcaoOrcamentaria> getAcoes() {
		return acoes;
	}
	
	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Ações 
	 * Orçamentárias
	 * @return Ação Orçamentária
	 */
	public AcaoOrcamentaria getAcaoSelected() {
		return acaoSelected;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Ações 
	 * Orçamentárias
	 * @param acaoSelected Ação Orçamentária
	 */
	public void setAcaoSelected(AcaoOrcamentaria acaoSelected) {
		this.acaoSelected = acaoSelected;
	}

	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar detalhamentos nas ações orçamentárias
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		acaoOrcamentaria = (AcaoOrcamentaria) slc.getObject();
		selectedPlanos = acaoOrcamentaria.getPlanosList();
	}	
	
	/**
	 * Persiste uma ação orçamentária no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */
	public String salvar() {
		
		String message = (acaoOrcamentaria.getId() == null ? 
				"Registro incluído com sucesso." : "Registro alterado com sucesso.");
			
		try {
			
			Set<PlanoDetalhamento> pd = new HashSet<PlanoDetalhamento>(selectedPlanos);
			
			acaoOrcamentaria.setPlanos(pd);
			
			service.saveOrUpdate(acaoOrcamentaria);
			
		} catch(Exception e) {		
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
		
		String result = service.remove(acaoOrcamentaria);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;
	}	
}