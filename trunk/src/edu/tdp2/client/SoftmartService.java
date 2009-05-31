package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.tdp2.client.dto.CalificacionDto;
import edu.tdp2.client.dto.ContratoDto;
import edu.tdp2.client.dto.FiltroDto;
import edu.tdp2.client.dto.MyAccountDto;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.SearchDto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.Usuario;

public interface SoftmartService extends RemoteService
{
	public List<Moneda> buscarMonedas();

	public String calificar(CalificacionDto calif);

	public String cancelarProyecto(Long projectId);

	public String cancelarProyectoXAdmin(Long projectId);

	public String chooseOffer(long offerId);

	public SearchDto filterProject(FiltroDto filtro);

	public List<Proyecto> getActiveProjects();

	public List<ContratoDto> getCalificacionesHechas(String user);

	public List<ContratoDto> getCalificacionesRecibidas(String user);

	public List<String> getDificultades();

	public MyAccountDto getMyAccountData(String usuario);

	public List<String> getNiveles();

	public Oferta getOfertaDeUsuario(Proyecto proyecto, String usuario);

	public Oferta getOfertaGanadora(Long id);

	public Oferta getOfertaGanadora(Proyecto project);

	public List<Oferta> getOffers(Proyecto project);

	public List<Proyecto> getOwnOpenProjects(String user);

	public List<String> getPaises();

	public List<String> getPresupuestos(Float conversion);

	public List<Proyecto> getProyectosDestacados();

	public List<Proyecto> getQualifiableProjects(String user);

	public List<String> getTamanios();

	public List<Proyecto> getUnassignedProjects(String usuario);

	public List<Usuario> getUsers();

	public String getUsuario(String login);

	public Boolean isUsuarioBloqueado(String name);

	public String login(String userName, String passwordHash);

	public String ofertar(OfertaDto oferta);

	public String publicar(ProyectoDto proyecto);

	public String registrar(UsuarioDto usuario);

	public String setProyectoDestacado(Long projectId, Boolean value);

	public String setProyectoRevisado(Long projectId, Boolean value);

	public String setUsuarioBloqueado(Long id, Boolean value);

	public String update(UsuarioDto dto, String usuarioAnterior);

	public String adminLogin(String userName, String passwordHash);
}
