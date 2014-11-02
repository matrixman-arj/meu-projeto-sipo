package br.mil.eb.decex.ati.enumerado;

/**
 * Identificação situação do exercício orçamentário.<p/>
 * <ul>
 * 	<li>PENDENTE</li>
 * 	<li>ENCERRADO</li>
 * 	<li>ANDAMENTO</li>
 * </ul>
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 */
public enum SituacaoExercicio {
	PENDENTE("Pendente"),
	ENCERRADO("Encerrado"),
	ANDAMENTO("Andamento");
	
	@SuppressWarnings("unused")	
	private String value;
	
	private SituacaoExercicio(String value) {
		this.value = value;
	}
	
}