package edu.tdp2.client.model;

import java.util.ArrayList;
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

	public static long getSerialVersionUID()
	{
		return serialVersionUID;
	}

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

	@OneToMany(mappedBy = "proyecto", fetch = FetchType.EAGER)
	private List<Oferta> ofertas;

	@OneToOne(mappedBy = "proyecto", fetch = FetchType.LAZY)
	private Contrato contrato;

	@Column(name = "Cancelado", nullable = false)
	private boolean cancelado;

	@Column(name = "CanceladoAdmin", nullable = false)
	private boolean canceladoXAdmin;

	@Column(name = "Revisado", nullable = false)
	private boolean revisado;

	@Column(name = "Destacado", nullable = false)
	private boolean destacado;

	public Proyecto()
	{
	}

	public Proyecto(ProyectoDto dto, Usuario usuario, Moneda moneda)
	{
		setNombre(dto.getNombre());
		setNivel(dto.getNivel());
		setTamanio(dto.getTamanio());
		setDificultad(dto.getDificultad());
		setDescripcion(dto.getDescripcion());
		setPathArchivo(dto.getArchivo());
		setMinPresupuesto(Presupuesto.desarmarRango(dto.getPresupuesto()).get(0));
		setMaxPresupuesto(Presupuesto.desarmarRango(dto.getPresupuesto()).get(1));
		setUsuario(usuario);
		setFecha(dto.getFecha());
		setMoneda(moneda);
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

	public Contrato getContrato()
	{
		return contrato;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public String getDificultad()
	{
		return dificultad;
	}

	public Date getFecha()
	{
		return fecha;
	}

	public int getMaxPresupuesto()
	{
		return maxPresupuesto;
	}

	public int getMinPresupuesto()
	{
		return minPresupuesto;
	}

	public Moneda getMoneda()
	{
		return moneda;
	}

	public String getNivel()
	{
		return nivel;
	}

	public String getNombre()
	{
		return nombre;
	}

	public List<Oferta> getOfertas()
	{
		return ofertas;
	}

	public String getPathArchivo()
	{
		return pathArchivo;
	}

	public String getTamanio()
	{
		return tamanio;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public boolean isCancelado()
	{
		return cancelado;
	}

	public boolean isCanceladoXAdmin()
	{
		return canceladoXAdmin;
	}

	public boolean isDestacado()
	{
		return destacado;
	}

	public boolean isRevisado()
	{
		return revisado;
	}

	@Override
	public void prune()
	{
		contrato = null;
		ofertas = new ArrayList<Oferta>(ofertas);
		for (Oferta oferta : ofertas)
			oferta.prune();
		usuario.prune();

	}

	public void pruneNotIncludingOffers()
	{
		contrato = null;
		ofertas = null;
		usuario.prune();
	}

	public void setCancelado(boolean cancelado)
	{
		this.cancelado = cancelado;
	}

	public void setCanceladoXAdmin(boolean canceladoXAdmin)
	{
		this.canceladoXAdmin = canceladoXAdmin;
	}

	public void setContrato(Contrato contrato)
	{
		this.contrato = contrato;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setDestacado(boolean destacado)
	{
		this.destacado = destacado;
	}

	public void setDificultad(String dificultad)
	{
		boolean existe = false;
		for (DificultadProyecto d : DificultadProyecto.values())
			if (d.name().compareTo(dificultad) == 0)
				existe = true;
		if (existe)
			this.dificultad = dificultad;
	}

	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}

	public void setMaxPresupuesto(int maxPresupuesto)
	{
		this.maxPresupuesto = maxPresupuesto;
	}

	public void setMinPresupuesto(int minPresupuesto)
	{
		this.minPresupuesto = minPresupuesto;
	}

	public void setMoneda(Moneda moneda)
	{
		this.moneda = moneda;
	}

	public void setNivel(String nivel)
	{
		boolean existe = false;
		for (NivelReputacion n : NivelReputacion.values())
			if (n.name().compareTo(nivel) == 0)
				existe = true;
		if (existe)
			this.nivel = nivel;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public void setOfertas(List<Oferta> ofertas)
	{
		this.ofertas = ofertas;
	}

	public void setPathArchivo(String pathArchivo)
	{
		this.pathArchivo = pathArchivo;
	}

	public void setRevisado(boolean revisado)
	{
		this.revisado = revisado;
	}

	public void setTamanio(String tamanio)
	{
		boolean existe = false;
		for (TamanioProyecto t : TamanioProyecto.values())
			if (t.name().compareTo(tamanio) == 0)
				existe = true;
		if (existe)
			this.tamanio = tamanio;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
}
