package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.UsuarioDto;

public interface SoftmartServiceAsync
{
	public void login(String userName, String passwordHash, AsyncCallback<String> callback);

	public void getPaises(AsyncCallback<List<String>> callback);

	public void getCiudades(String pais, AsyncCallback<List<String>> callback);
	
	public void registrar(UsuarioDto usuario, AsyncCallback<String> callback);
	
	public void publicar(ProyectoDto proyecto, AsyncCallback<String> callback);
	
	public void getNiveles(AsyncCallback<List<String>> callback);
	public void getPresupuestos(AsyncCallback<List<String>> callback);
	public void getDificultades(AsyncCallback<List<String>> callback);
	public void getTamanios(AsyncCallback<List<String>> callback);
	public void ofertar(OfertaDto oferta, AsyncCallback<String> asyncCallback);
}