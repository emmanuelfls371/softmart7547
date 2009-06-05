package edu.tdp2.client;

import java.util.ArrayList;
import java.util.Date;
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

	public String cancelarProyectoXAdmin(String adminUserName, Long projectId);

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

	public String setProyectoRevisado(String adminUserName, Long projectId, Boolean value);

	public String setUsuarioBloqueado(String adminUserName, Long id, Boolean value);

	public String update(UsuarioDto dto, String usuarioAnterior);

	public String adminLogin(String userName, String passwordHash);

	public String adminLogout(String userName);

	public String setTextoBienvenida(String locale, String text);

	public String getTextoBienvenida(String locale);
	
	public String filterLog(Date from, Date to, String admin);	

	public List<String> getAdmins();
}