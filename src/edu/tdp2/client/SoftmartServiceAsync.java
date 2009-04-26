package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

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

public interface SoftmartServiceAsync
{
	public void login(String userName, String passwordHash, AsyncCallback<String> callback);

	public void getPaises(AsyncCallback<List<String>> callback);

	public void getCiudades(String pais, AsyncCallback<List<String>> callback);

	public void registrar(UsuarioDto usuario, AsyncCallback<String> callback);

	public void publicar(ProyectoDto proyecto, AsyncCallback<String> callback);

	public void getNiveles(AsyncCallback<List<String>> callback);

	public void getPresupuestos(Float conversion, AsyncCallback<List<String>> callback);

	public void getDificultades(AsyncCallback<List<String>> callback);

	public void getTamanios(AsyncCallback<List<String>> callback);

	public void ofertar(OfertaDto oferta, AsyncCallback<String> asyncCallback);

	public void calificar(CalificacionDto calif, AsyncCallback<String> asyncCallback);

	public void getUnassignedProjects(String usuario, AsyncCallback<List<Proyecto>> callback);

	public void getQualifiableProjects(String user, AsyncCallback<List<Proyecto>> callback);

	public void getOwnOpenProjects(String user, AsyncCallback<List<Proyecto>> callback);

	public void getOffers(Proyecto project, AsyncCallback<List<Oferta>> callback);

	public void chooseOffer(long offerId, AsyncCallback<String> callback);

	public void getCalificacionesRecibidas(String user, AsyncCallback<List<ContratoDto>> callback);

	public void getCalificacionesHechas(String user, AsyncCallback<List<ContratoDto>> callback);

	public void cancelarProyecto(Long projectId, AsyncCallback<String> callback);

	public void getMyAccountData(String usuario, AsyncCallback<MyAccountDto> callback);

	public void filterProject(FiltroDto filtro, AsyncCallback<List<Proyecto>> callback);
	
	public void buscarMonedas(AsyncCallback<List<Moneda>> callback);
}
