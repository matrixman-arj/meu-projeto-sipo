package br.mil.eb.decex.ati.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public abstract class BaseTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
			.addPackages(true, "br.mil.eb.decex.ati")	
			.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
			.addAsManifestResource("test-persistence.xml", "persistence.xml")
			.addAsManifestResource("postgresqltest-ds.xml");
		
	}		
	
}