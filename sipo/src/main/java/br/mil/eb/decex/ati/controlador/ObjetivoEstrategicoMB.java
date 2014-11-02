package br.mil.eb.decex.ati.controlador;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import br.mil.eb.decex.ati.modelo.ObjetivoEstrategico;
import br.mil.eb.decex.ati.servico.ObjetivoEstrategicoService;

/**
 * Controlador para view de Objetivos Estratégicos. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@ViewScoped
@ManagedBean
public class ObjetivoEstrategicoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ObjetivoEstrategicoService service;
	
	private ObjetivoEstrategico objetivoEstrategico;
	private List<ObjetivoEstrategico> objetivos;	
	private ObjetivoEstrategico objetivoSelected;

	/**
	 * Inicializa lista de Objetivo Estratégico e 
	 * instancia objetos necessários 
	 */	
	@PostConstruct
	public void init(){
		
		//Instancia o model da página		
		objetivoEstrategico = new ObjetivoEstrategico();
		
		//Carrega a lista de apoio da página		
		objetivos = service.findAll();
	}
	
	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Objetivo Estratégico
	 */
	public ObjetivoEstrategico getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param objetivoEstrategico Objetivo Estratégico
	 */
	public void setObjetivoEstrategico(ObjetivoEstrategico objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}
	
	/**
	 * Lista dos objetivos estratégicos cadastrados no banco de dados
	 * @return lista de objetivos Estratégicos
	 */
	public List<ObjetivoEstrategico> getObjetivos() {
		return objetivos;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de objetivos estratégicos
	 * @return Objetivo Estratégico
	 */
	public ObjetivoEstrategico getObjetivoSelected() {
		return objetivoSelected;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de objetivos estratégicos
	 * @param objetivoSelected objetivo estratégico selecionado
	 */
	public void setObjetivoSelected(ObjetivoEstrategico objetivoSelected) {
		this.objetivoSelected = objetivoSelected;
	}

	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar detalhamentos no objetivo estratégico
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		objetivoEstrategico = (ObjetivoEstrategico) slc.getObject();
	}	
	
	/**
	 * Persiste um objetivo estratégico no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */	
	public String salvar() {	
		
		String message = (objetivoEstrategico.getId() == null ? 
				"Registro incluído com sucesso." : "Registro alterado com sucesso.");		
		
		try {						
			service.saveOrUpdate(objetivoEstrategico);
		}catch(Exception e){
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
	 * Exclui um objetivo estratégico do banco de dados
	 * @return sucesso ou falha
	 */
	public String excluir() {
		
		String result = service.remove(objetivoEstrategico);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;		
	}	
}