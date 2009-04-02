package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.UsuarioDto;

public interface SoftmartService extends RemoteService
{
	public String login(String userName, String passwordHash);

	public List<String> getPaises();

	public List<String> getCiudades(String pais);
	
	public String registrar(UsuarioDto usuario);
	
	public String publicar(ProyectoDto proyecto);
	
	public List<String> getNiveles();
	public List<String> getPresupuestos();
	public List<String> getDificultades();
	public List<String> getTamanios();
}
