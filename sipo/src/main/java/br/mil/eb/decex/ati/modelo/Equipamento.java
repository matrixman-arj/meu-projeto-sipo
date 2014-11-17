package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import br.mil.eb.decex.ati.enumerado.MarcaEquipamento;
import br.mil.eb.decex.ati.enumerado.TipoEquipamento;

@Entity
@Table(name="equipamento")
public class Equipamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="EQUIPAMENTO_ID_GENERATOR", sequenceName="EQUIPAMENTO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EQUIPAMENTO_ID_GENERATOR")
	private Long id;	
	
	@Column
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private MarcaEquipamento marca;
	
	@Enumerated(EnumType.STRING)
	private TipoEquipamento tipo;
	
	@Column
	private String modelo;	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public MarcaEquipamento getMarca() {
		return marca;
	}

	public void setMarca(MarcaEquipamento marca) {
		this.marca = marca;
	}

	public TipoEquipamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEquipamento tipo) {
		this.tipo = tipo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Equipamento [id=" + id + "]";
	}

}
