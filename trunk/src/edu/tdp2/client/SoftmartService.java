package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.tdp2.client.dto.CalificacionDto;
import edu.tdp2.client.dto.ContratoDto;
import edu.tdp2.client.dto.FiltroDto;
import edu.tdp2.client.dto.MyAccountDto;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.Usuario;

public interface SoftmartService extends RemoteService
{
	public String login(String userName, String passwordHash);

	public List<String> getPaises();

	public String registrar(UsuarioDto usuario);

	public String publicar(ProyectoDto proyecto);

	public String ofertar(OfertaDto oferta);

	public String calificar(CalificacionDto calif);

	public List<String> getNiveles();

	public List<String> getPresupuestos(Float conversion);

	public List<String> getDificultades();

	public List<String> getTamanios();

	public List<Proyecto> getUnassignedProjects(String usuario);

	public List<Proyecto> getQualifiableProjects(String user);

	public List<Proyecto> getOwnOpenProjects(String user);

	public List<Oferta> getOffers(Proyecto project);

	public Oferta getOfertaGanadora(Proyecto project);

	public Oferta getOfertaDeUsuario(Proyecto proyecto, String usuario);

	public String chooseOffer(long offerId);

	public List<ContratoDto> getCalificacionesRecibidas(String user);

	public List<ContratoDto> getCalificacionesHechas(String user);

	public String cancelarProyecto(Long projectId);

	public String cancelarProyectoXAdmin(Long projectId);

	public MyAccountDto getMyAccountData(String usuario);

	public List<Proyecto> filterProject(FiltroDto filtro);

	public List<Moneda> buscarMonedas();

	public String update(UsuarioDto dto, String usuarioAnterior);

	public String setProyectoRevisado(Long projectId, Boolean value);

	public List<Proyecto> getActiveProjects();

	public List<Usuario> getUsers();

	public String setUsuarioBloqueado(Long id, Boolean value);

	public Boolean isUsuarioBloqueado(String name);
}
