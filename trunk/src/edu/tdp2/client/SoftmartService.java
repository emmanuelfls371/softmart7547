package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.tdp2.client.dto.CalificacionDto;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;

public interface SoftmartService extends RemoteService
{
	public String login(String userName, String passwordHash);

	public List<String> getPaises();

	public List<String> getCiudades(String pais);

	public String registrar(UsuarioDto usuario);

	public String publicar(ProyectoDto proyecto);

	public String ofertar(OfertaDto oferta);

	public String calificar(CalificacionDto calif);

	public List<String> getNiveles();

	public List<String> getPresupuestos();

	public List<String> getDificultades();

	public List<String> getTamanios();

	public List<Proyecto> getUnassignedProjects();

	public List<Proyecto> getQualifiableProjects(String user);
	
	public List<Proyecto> getOwnOpenProjects(String user);

	public List<Oferta> getOffers(Proyecto project);
}
