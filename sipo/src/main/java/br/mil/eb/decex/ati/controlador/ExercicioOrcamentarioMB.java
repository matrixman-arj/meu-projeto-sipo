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
import br.mil.eb.decex.ati.enumerado.SituacaoExercicio;
import br.mil.eb.decex.ati.modelo.ExercicioOrcamentario;
import br.mil.eb.decex.ati.servico.ExercicioOrcamentarioService;

/**
 * Controlador para view de Exercícios Orçamentários. <br/>
 * Implementado como formulário mestre-detalhe.
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 */
@ViewScoped
@ManagedBean
public class ExercicioOrcamentarioMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ExercicioOrcamentarioService service;
	
	private ExercicioOrcamentario exercicioOrcamentario;
	private List<ExercicioOrcamentario> exercicios;
	private ExercicioOrcamentario exercicioSelected;
	
	
	/**
	 * Inicializa lista de exercícios orçamentários e 
	 * instancia objetos necessários 
	 */		
	@PostConstruct
	public void init(){
		
		//Instancia o model da página				
		exercicioOrcamentario = new ExercicioOrcamentario();
		
		//Carrega a lista de apoio da página				
		exercicios = service.findAll();

	}
		
	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @return Exercício Orçamentário
	 */
	public ExercicioOrcamentario getExercicioOrcamentario() {
		return exercicioOrcamentario;
	}

	/**
	 * Objeto da página que sofrerá atualizações no mecânismo 
	 * de persistencia
	 * @param exercicioOrcamentario Exercício Orçamentário
	 */
	public void setExercicioOrcamentario(ExercicioOrcamentario exercicioOrcamentario) {
		this.exercicioOrcamentario = exercicioOrcamentario;
	}
	
	/**
	 * Lista dos exercícios orçamentários cadastrados no banco de dados
	 * @return lista de exercícios orçamentários
	 */
	public List<ExercicioOrcamentario> getExercicios() {
		return exercicios;
	}

	/**
	 * Situação do exercício orçamentário
	 * @return lista com as possíveis situações de um exercício orçamentário
	 * @see br.mil.eb.decex.ati.enumerado.SituacaoExercicio
	 */
	public List<SituacaoExercicio> getSituacaoExercicio() {
		return Arrays.asList(SituacaoExercicio.values());
	}	
	
	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de exercícios 
	 * orçamentários
	 * @return Exercício orçamentário
	 */
	public ExercicioOrcamentario getExercicioSelected() {
		return exercicioSelected;
	}

	/**
	 * Objeto utilizado para suporte às funcionalidades 
	 * de seleção na dataTable e detalhamento de exercícios 
	 * orçamentários
	 * @param exercicioSelected Exercício orçamentário
	 */
	public void setExercicioSelected(ExercicioOrcamentario exercicioSelected) {
		this.exercicioSelected = exercicioSelected;
	}
	
	/**
	 * Registro selecionado na dataTable da tela e disponibilizado para o 
	 * usuário realizar detalhamento no exercício orçamentário
	 * @param slc evento associado a componente lista do primefaces
	 */	
	public void onRowSelect(SelectEvent slc) {
		exercicioOrcamentario = (ExercicioOrcamentario) slc.getObject();
	}		
	
	/**
	 * Referência direta para página de configuração do exercício
	 * @return
	 */
	public String configuracaoExercicioOrcamentario(){
		return "/administracao/configuracaoexercicio.xhtml";
	}
		
	/**
	 * Persiste um exercício orçamentário no banco de dados
	 * @return sucesso caso não ocorra uma exception
	 */		
	public String salvar() {
				
		String message = (exercicioOrcamentario.getId() == null ? 
				"Registro incluído com sucesso." : "Registro alterado com sucesso.");		
		
		try {						
			service.saveOrUpdate(exercicioOrcamentario);
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
	public String excluir(){
		
		String result = service.remove(exercicioOrcamentario);
		
		String message = (result.equals("success") ? "Registro excluído com sucesso." : 
			"Falha na exclusão do registro.");
				
		FacesContext.getCurrentInstance().addMessage(":frmcad:mensagem", 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Confirmação"));
		
		init();
		
		return result;		
	}
}