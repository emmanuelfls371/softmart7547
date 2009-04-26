package edu.tdp2.client.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import edu.tdp2.client.dto.ProyectoDto;

@Entity
@Table(name = "Proyecto")
public class Proyecto extends AbstractDomainObject
{
	private static final long serialVersionUID = -4981789655579048475L;

	@Column(name = "Nombre", length = 50, nullable = false)
	private String nombre;

	@Column(name = "PresupuestoMax", nullable = false)
	private int maxPresupuesto;

	@Column(name = "PresupuestoMin", nullable = false)
	private int minPresupuesto;

	@Column(name = "Fecha", length = 10, nullable = false)
	private Date fecha;

	@Column(name = "Nivel", nullable = false)
	private String nivel;

	@Column(name = "Dificultad", nullable = false)
	private String dificultad;

	@Column(name = "Tamanio", nullable = false)
	private String tamanio;

	@Column(name = "Descripcion")
	private String descripcion;

	@Column(name = "PathArchivo", length = 255)
	private String pathArchivo;

	@ManyToOne()
	@JoinColumn(name = "Moneda", nullable = false)
	private Moneda moneda;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Usuario", nullable = false)
	private Usuario usuario;

	@OneToMany(mappedBy = "proyecto", fetch = FetchType.LAZY)
	private List<Oferta> ofertas;

	@OneToOne(mappedBy = "proyecto", fetch = FetchType.LAZY)
	private Contrato contrato;

	@Column(name = "Cancelado", nullable = false)
	private boolean cancelado;

	public List<Oferta> getOfertas()
	{
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas)
	{
		this.ofertas = ofertas;
	}

	public boolean isCancelado()
	{
		return cancelado;
	}

	public void setCancelado(boolean cancelado)
	{
		this.cancelado = cancelado;
	}

	public Moneda getMoneda()
	{
		return moneda;
	}

	private void setMoneda(Moneda moneda)
	{
		this.moneda = moneda;
	}

	public Contrato getContrato()
	{
		return contrato;
	}

	public void setContrato(Contrato contrato)
	{
		this.contrato = contrato;
	}

	public boolean existe(Oferta of)
	{
		if (of != null)
		{
			for (Oferta oferta : ofertas)
				if (oferta.equals(of))
					return true;
			return false;
		}
		else
			return false;
	}

	public boolean addOferta(Oferta of)
	{
		if (of != null && !existe(of))
		{
			ofertas.add(of);
			return true;
		}
		else
			return false;
	}

	public Proyecto()
	{
	}

	public Proyecto(ProyectoDto dto, Usuario usuario, Moneda moneda)
	{
		this.setNombre(dto.getNombre());
		this.setNivel(dto.getNivel());
		this.setTamanio(dto.getTamanio());
		this.setDificultad(dto.getDificultad());
		this.setDescripcion(dto.getDescripcion());
		this.setPathArchivo(dto.getArchivo());
		this.setMinPresupuesto(Presupuesto.desarmarRango(dto.getPresupuesto()).get(0));
		this.setMaxPresupuesto(Presupuesto.desarmarRango(dto.getPresupuesto()).get(1));
		this.setUsuario(usuario);
		this.setFecha(dto.getFecha());
		this.setMoneda(moneda);
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	private void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	private void setMaxPresupuesto(int maxPresupuesto)
	{
		this.maxPresupuesto = maxPresupuesto;
	}

	private void setMinPresupuesto(int minPresupuesto)
	{
		this.minPresupuesto = minPresupuesto;
	}

	private void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}

	private void setNivel(String nivel)
	{
		boolean existe = false;
		for (NivelReputacion n : NivelReputacion.values())
			if (n.name().compareTo(nivel) == 0)
				existe = true;
		if (existe)
			this.nivel = nivel;
	}

	private void setDificultad(String dificultad)
	{
		boolean existe = false;
		for (DificultadProyecto d : DificultadProyecto.values())
			if (d.name().compareTo(dificultad) == 0)
				existe = true;
		if (existe)
			this.dificultad = dificultad;
	}

	private void setTamanio(String tamanio)
	{
		boolean existe = false;
		for (TamanioProyecto t : TamanioProyecto.values())
			if (t.name().compareTo(tamanio) == 0)
				existe = true;
		if (existe)
			this.tamanio = tamanio;
	}

	private void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	private void setPathArchivo(String pathArchivo)
	{
		this.pathArchivo = pathArchivo;
	}

	public static long getSerialVersionUID()
	{
		return serialVersionUID;
	}

	public int getMaxPresupuesto()
	{
		return maxPresupuesto;
	}

	public int getMinPresupuesto()
	{
		return minPresupuesto;
	}

	public Date getFecha()
	{
		return fecha;
	}

	public String getNivel()
	{
		return nivel;
	}

	public String getDificultad()
	{
		return dificultad;
	}

	public String getTamanio()
	{
		return tamanio;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public String getPathArchivo()
	{
		return pathArchivo;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	@Override
	public void prune()
	{
		contrato = null;
		ofertas = null;
		usuario.prune();
	}
}
