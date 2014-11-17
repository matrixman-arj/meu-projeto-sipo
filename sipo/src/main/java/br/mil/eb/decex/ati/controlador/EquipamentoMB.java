package br.mil.eb.decex.ati.controlador;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import br.mil.eb.decex.ati.enumerado.MarcaEquipamento;
import br.mil.eb.decex.ati.enumerado.TipoEquipamento;
import br.mil.eb.decex.ati.modelo.Equipamento;
import br.mil.eb.decex.ati.servico.EquipamentoService;

/**
 * Controlador para view de Equipamentos. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author <b>Vanilton</b> Gomes dos Santos - 3º Sgt QE
 * @version 1.0
 */
@ViewScoped
@ManagedBean
public class EquipamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EquipamentoService service;	

	private Equipamento equipamento;	
	
	private List<Equipamento> equipamentos;	
	private Equipamento equipamentoSelected;
	
	
	/**
	 * Inicializa listas de equipamentos e 
	 * instancia objetos necessários, além de 
	 * realizar a limpeza de outros
	 */			
	@PostConstruct
	public void init() {

		//Instancia o model da página		
		equipamento = new Equipamento();
		
		//Carrega as listas de apoio da página			
		equipamentos = service.findAll();		
		
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Equipamento
	 */
	public Equipamento getEquipamento() {
		return equipamento;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param equipamento Equipamento
	 */
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}	
	
	/**
	 * Lista da marcas de equipamentos
	 * @return lista de equipamentos
	 */
	public List<MarcaEquipamento> getMarcaEquipamentos() {
		return Arrays.asList(MarcaEquipamento.values());
	}
	
	public List<TipoEquipamento> getTipoEquipamentos() {
		return Arrays.asList(TipoEquipamento.values());
	}
		
	/**
	 * Lista de ações equipamentos cadastrados no  
	 * banco de dados
	 * @return lista de equipamentos
	 */
	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}
		
	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Equipamentos
	 * @return Equipamento
	 */
	public Equipamento getEquipamentoSelected() {
		return equipamentoSelected;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Equipamentos
	 * @param equipamentoSelected Equipamento
	 */
	public void setEquipamentoSelected(Equipamento equipamentoSelected) {
		this.equipamentoSelected = equipamentoSelected;
	}
	
	
	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * equipamento realizar detalhamentos nos equipamentos
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		equipamento = (Equipamento) slc.getObject();		
	}	
	
	/**
	 * Persiste um equipamento no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */
	public String salvar() throws SQLException {
		
		String message = (equipamento.getId() == null ? 
			"Registro incluído com sucesso." : "Registro alterado com sucesso.");	
		try {
			service.saveOrUpdate(equipamento);
			
		} catch (Exception e) {
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
	 * Exclui um equipamento do banco de dados
	 * @return sucesso ou falha
	 */			
	public String excluir() {
		
		String result = service.remove(equipamento);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;
	}	
}