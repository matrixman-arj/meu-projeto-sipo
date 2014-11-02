package br.mil.eb.decex.ati.enumerado;

/**
 * Identificação do grupo a que pertence as natureza de despesas.<p/>
 * <ul>
 * 	<li>3 - OUTRAS DESPESAS CORRENTES</li>
 * 	<li>4 - INVESTIMENTOS</li>
 * </ul>
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 */
public enum GrupoNatureza {
	
	OUTRAS_DESPESAS_CORRENTES("3 - Outras Despesas Correntes"),
	INVESTIMENTOS("4 - Investimentos");
	
	@SuppressWarnings("unused")	
	private String value;
	
	private GrupoNatureza(String value) {
		this.value = value;
	}
	
}