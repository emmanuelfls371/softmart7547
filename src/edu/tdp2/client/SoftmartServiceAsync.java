package edu.tdp2.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

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

public interface SoftmartServiceAsync
{
	public void adminLogin(String userName, String passwordHash, AsyncCallback<String> callback);

	public void adminLogout(String userName, AsyncCallback<String> callback);

	public void buscarMonedas(AsyncCallback<List<Moneda>> callback);

	public void calificar(CalificacionDto calif, AsyncCallback<String> asyncCallback);

	public void cancelarProyecto(Long projectId, AsyncCallback<String> callback);

	public void cancelarProyectoXAdmin(String adminUserName, Long projectId, AsyncCallback<String> callback);

	public void chooseOffer(long offerId, AsyncCallback<String> callback);

	public void filterLog(Date from, Date to, String admin, AsyncCallback<String> callback);

	public void filterProject(FiltroDto filtro, AsyncCallback<SearchDto> callback);

	public void getActiveProjects(AsyncCallback<List<Proyecto>> callback);

	public void getAdmins(AsyncCallback<List<String>> callback);

	public void getCalificacionesHechas(String user, AsyncCallback<List<ContratoDto>> callback);

	public void getCalificacionesRecibidas(String user, AsyncCallback<List<ContratoDto>> callback);

	public void getDificultades(AsyncCallback<List<String>> callback);

	public void getMyAccountData(String usuario, AsyncCallback<MyAccountDto> callback);

	public void getNiveles(AsyncCallback<List<String>> callback);

	public void getOfertaDeUsuario(Proyecto proyecto, String usuario, AsyncCallback<Oferta> callback);

	public void getOfertaGanadora(Long id, AsyncCallback<Oferta> callback);

	public void getOfertaGanadora(Proyecto project, AsyncCallback<Oferta> callback);

	public void getOffers(Proyecto project, AsyncCallback<List<Oferta>> callback);

	public void getOwnOpenProjects(String user, AsyncCallback<List<Proyecto>> callback);

	public void getPaises(AsyncCallback<List<String>> callback);

	public void getPresupuestos(Float conversion, AsyncCallback<List<String>> callback);

	public void getProyectosDestacados(AsyncCallback<List<Proyecto>> callback);

	public void getQualifiableProjects(String user, AsyncCallback<List<Proyecto>> callback);

	public void getTamanios(AsyncCallback<List<String>> callback);

	public void getTextoBienvenida(String locale, AsyncCallback<String> callback);

	public void getUnassignedProjects(String usuario, AsyncCallback<List<Proyecto>> callback);

	public void getUsers(AsyncCallback<List<Usuario>> callback);

	public void getUsuario(String login, AsyncCallback<String> callback);

	public void isUsuarioBloqueado(String name, AsyncCallback<Boolean> callback);

	public void login(String userName, String passwordHash, AsyncCallback<String> callback);

	public void ofertar(OfertaDto oferta, AsyncCallback<String> asyncCallback);

	public void publicar(ProyectoDto proyecto, AsyncCallback<String> callback);

	public void registrar(UsuarioDto usuario, AsyncCallback<String> callback);

	public void setProyectoDestacado(Long projectId, Boolean value, AsyncCallback<String> callback);

	public void setProyectoRevisado(String adminUserName, Long projectId, Boolean value, AsyncCallback<String> callback);

	public void setTextoBienvenida(String locale, String text, AsyncCallback<String> callback);

	public void setUsuarioBloqueado(String adminUserName, Long id, Boolean value, AsyncCallback<String> callback);

	public void update(UsuarioDto dto, String usuarioAnterior, AsyncCallback<String> callback);
}
