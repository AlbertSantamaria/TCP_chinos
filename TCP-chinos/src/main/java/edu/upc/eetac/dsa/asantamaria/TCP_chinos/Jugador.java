package edu.upc.eetac.dsa.asantamaria.TCP_chinos;

import java.net.InetAddress;

public class Jugador {

	String nombre = "";
	InetAddress ip;
	int puerto;

	Jugador() {
	}

	Jugador(String nombre, InetAddress ip, int puerto) {

		setNombre(nombre);
		setIp(ip);
		setPuerto(puerto);

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

}
