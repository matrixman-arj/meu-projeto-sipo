package br.mil.eb.decex.ati.excecao;

/**
 * Exceção para tentativa de inclusão de CPF duplicado de 
 * usuário do sistema.
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 */
public class CPFDuplicadoException extends Exception {

	private static final long serialVersionUID = 1L;

	public CPFDuplicadoException(String message){
		super(message);
	}
	
}