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
import br.mil.eb.decex.ati.modelo.NaturezaDespesa;
import br.mil.eb.decex.ati.servico.NaturezaDespesaService;

/**
 * Controlador para view de Natureza de Despesas. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 */
@ViewScoped
@ManagedBean
public class NaturezaDespesaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private NaturezaDespesaService service;
	
	private NaturezaDespesa naturezaDespesa;
	private List<NaturezaDespesa> naturezas;	
	private NaturezaDespesa naturezaSelected;

	/**
	 * Inicializa lista de Natureza de Despesas e 
	 * instancia objetos necessários 
	 */	
	@PostConstruct
	public void init(){
		
		//Instancia o model da página		
		naturezaDespesa = new NaturezaDespesa();
		
		//Carrega a lista de apoio da página		
		naturezas = service.findAll();
	}
	
	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Natureza de Despesa
	 */
	public NaturezaDespesa getNaturezaDespesa() {
		return naturezaDespesa;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param naturezaDespesa Natureza de Despesa
	 */
	public void setNaturezaDespesa(NaturezaDespesa naturezaDespesa) {
		this.naturezaDespesa = naturezaDespesa;
	}
	
	/**
	 * Lista das naturezas de despesas cadastradas no banco de dados
	 * @return lista de naturezas de despesas
	 */
	public List<NaturezaDespesa> getNaturezas() {
		return naturezas;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Naturezas de Despesas
	 * @return Natureza de Despesa
	 */
	public NaturezaDespesa getNaturezaSelected() {
		return naturezaSelected;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Naturezas de Despesas
	 * @param naturezaSelected natureza de despesas selecionada
	 */
	public void setNaturezaSelected(NaturezaDespesa naturezaSelected) {
		this.naturezaSelected = naturezaSelected;
	}

	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar detalhamentos na natureza da despesa
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		naturezaDespesa = (NaturezaDespesa) slc.getObject();
	}	
	
	/**
	 * Persiste uma natureza de despesa no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */	
	public String salvar() {	
		
		String message = (naturezaDespesa.getId() == null ? 
				"Registro incluído com sucesso." : "Registro alterado com sucesso.");		
		
		try {						
			service.saveOrUpdate(naturezaDespesa);
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
	 * Exclui uma natureza de despesa do banco de dados
	 * @return sucesso ou falha
	 */
	public String excluir() {
		
		String result = service.remove(naturezaDespesa);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;		
	}	
}