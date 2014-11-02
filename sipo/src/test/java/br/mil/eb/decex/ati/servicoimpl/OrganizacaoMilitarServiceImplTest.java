package br.mil.eb.decex.ati.servicoimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.mil.eb.decex.ati.enumerado.TipoOM;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.servico.OrganizacaoMilitarService;
import br.mil.eb.decex.ati.test.BaseTest;

@RunWith(Arquillian.class)
public class OrganizacaoMilitarServiceImplTest extends BaseTest {

	@Inject
	private OrganizacaoMilitarService service;
	
	//Insere duas novas organizacoes militares
	@Test
	@InSequence(1)
	public void insereOrganizacoesMilitaresTest() {
		
		//Inserindo DECEx
		OrganizacaoMilitar decex = new OrganizacaoMilitar();
		
		decex.setCodigo("01700");
		decex.setNome("Departamento de Educação e Cultura do Exército");
		decex.setSigla("DECEx");
		decex.setTipo(TipoOM.DEPARTAMENTO);
		
		service.persist(decex);
		
		//Inserindo DESMil
		OrganizacaoMilitar desmil = new OrganizacaoMilitar();
		desmil.setCodigo("01980");
		desmil.setNome("Diretoria de Ensino Superior Militar");
		desmil.setSigla("DESMil");
		desmil.setTipo(TipoOM.DIRETORIA);
		
		service.persist(desmil);
		
		//Consulta decex
		OrganizacaoMilitar consultaDECEx = service.findBySigla("DECEx");
		assertNotNull(consultaDECEx);
		assertEquals("01700", consultaDECEx.getCodigo());
		
		//Consulta desmil
		OrganizacaoMilitar consultaDESMil = service.findBySigla("DESMil");
		assertNotNull(consultaDESMil);
		assertEquals("01980", consultaDESMil.getCodigo());		
		
		//Consulta lista
		List<OrganizacaoMilitar> organizacoes = service.findAll();
		assertNotNull(organizacoes);
		assertEquals(2, organizacoes.size());		
		
	}
	
	//Altera uma organizacao militar
	
	//Exclui uma organizacao militar
	
}