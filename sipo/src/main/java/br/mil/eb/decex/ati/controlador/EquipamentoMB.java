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
 * Controlador para view de Usuários. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@ViewScoped
@ManagedBean
public class EquipamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EquipamentoService service;	

	private Equipamento equipamento;	
	
	@SuppressWarnings("unused")
	private List<MarcaEquipamento> marca;
	@SuppressWarnings("unused")
	private List<TipoEquipamento> tipo;
	
	private List<Equipamento> equipamentos;	
	private Equipamento equipamentoSelected;
	
	
	/**
	 * Inicializa listas de usuários e 
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
	 * @return Usuário
	 */
	public Equipamento getEquipamento() {
		return equipamento;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param equipamento Usuário
	 */
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}	
	
	/**
	 * Lista de postos e graduações dos militares
	 * @return lista de postos e graduações
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
	 * @return lista de usuários
	 */
	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}
		
	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Usuários
	 * @return Equipamento
	 */
	public Equipamento getEquipamentoSelected() {
		return equipamentoSelected;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de Usuários
	 * @param equipamentoSelected Equipamento
	 */
	public void setEquipamentoSelected(Equipamento equipamentoSelected) {
		this.equipamentoSelected = equipamentoSelected;
	}
	
	
	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar detalhamentos nos usuários
	 * @param slc evento associado a componente lista do primefaces
	 */
	public void onRowSelect(SelectEvent slc) {
		equipamento = (Equipamento) slc.getObject();		
	}	
	
	/**
	 * Persiste um usuário no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */
	public String salvar() throws SQLException {
		
		String message = (equipamento.getId() == null ? 
			"Registro incluído com sucesso." : "Registro alterado com sucesso.");	
		
		service.saveOrUpdate(equipamento);
			
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
		
		String result = service.remove(equipamento);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;
	}	
}