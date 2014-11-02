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
import br.mil.eb.decex.ati.modelo.NaturezaDespesa;
import br.mil.eb.decex.ati.modelo.PlanoDetalhamento;
import br.mil.eb.decex.ati.servico.NaturezaDespesaService;
import br.mil.eb.decex.ati.servico.PlanoDetalhamentoService;

/**
 * Controlador para view de Planos de Detalhamento. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@ViewScoped
@ManagedBean
public class PlanoDetalhamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PlanoDetalhamentoService service;
	
	@Inject
	private NaturezaDespesaService naturezaService;
	
	private PlanoDetalhamento planoDetalhamento;
	private List<NaturezaDespesa> naturezas;
	private List<NaturezaDespesa> selectedNaturezas;	
	private List<PlanoDetalhamento> planos;
	private PlanoDetalhamento planoSelected;
	
	/**
	 * Inicializa listas de planos de detalhamento e 
	 * instancia objetos necessários, além de 
	 * realizar a limpeza de outros
	 */	
	@PostConstruct
	public void init() {
		
		//Instancia o model da página
		planoDetalhamento = new PlanoDetalhamento();
		
		//Carrega as listas de apoio da página
		naturezas = naturezaService.findAll();
		planos = service.findAll();

		//Limpa seleção anterior de naturezas de despesa
		selectedNaturezas = null;
		
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Plano de detalhamento
	 */
	public PlanoDetalhamento getPlanoDetalhamento() {
		return planoDetalhamento;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param planoDetalhamento Plano de Detalhamento
	 */
	public void setPlanoDetalhamento(PlanoDetalhamento planoDetalhamento) {
		this.planoDetalhamento = planoDetalhamento;
	}

	/**
	 * Lista de naturezas de despesa associadas ao plano de detalhamento
	 * @return lista de naturezas de despesa
	 */
	public List<NaturezaDespesa> getNaturezas() {
		return naturezas;
	}
		
	/**
	 * Lista com naturezas de despesas selecionadas para 
	 * cadastramento dos planos de detalhamento.
	 * @return lista de naturezas de despesa
	 */
	public List<NaturezaDespesa> getSelectedNaturezas() {
		return selectedNaturezas;
	}

	/**
	 * Lista com naturezas de despesas selecionadas para 
	 * cadastramento dos planos de detalhamento.
	 * @param selectedNaturezas lista de naturezas de despesa
	 */
	public void setSelectedNaturezas(List<NaturezaDespesa> selectedNaturezas) {
		this.selectedNaturezas = selectedNaturezas;
	}
	
	/**
	 * Lista das planos de detalhamento cadastrados no  
	 * banco de dados
	 * @return lista de planos de detalhamento
	 */
	public List<PlanoDetalhamento> getPlanos() {
		return planos;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Planos 
	 * de detalhamento
	 * @return Plano de detalhamento
	 */
	public PlanoDetalhamento getPlanoSelected() {
		return planoSelected;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Planos 
	 * de detalhamento
	 * @param planoSelected Plano de detalhamento
	 */
	public void setPlanoSelected(PlanoDetalhamento planoSelected) {
		this.planoSelected = planoSelected;
	}

	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar detalhamentos no plano de detalhamento
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		planoDetalhamento = (PlanoDetalhamento) slc.getObject();
		selectedNaturezas = planoDetalhamento.getNaturezasList();
	}	
	
	/**
	 * Persiste um plano de detalhamento no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */
	public String salvar() {
		
		String message = (planoDetalhamento.getId() == null ? 
			"Registro incluído com sucesso." : "Registro alterado com sucesso.");
		
		try {
			
			Set<NaturezaDespesa> nd = new HashSet<NaturezaDespesa>(selectedNaturezas);
			
			planoDetalhamento.setNaturezas(nd);
			
			service.saveOrUpdate(planoDetalhamento);
			
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
	 * Exclui um plano de detalhamento do banco de dados
	 * @return sucesso ou falha
	 */	
	public String excluir() {
		
		String result = service.remove(planoDetalhamento);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;
	}	
}