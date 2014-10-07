package edu.upc.eetac.dsa.asantamaria.TCP_chinos;

import java.net.InetAddress;

public class Jugador {

	String nombre = "";
	InetAddress ip;
	int puerto;
	int apuesta;
	int monedas;
	boolean apostado=false;

	Jugador() {
	}

	Jugador(String nombre, InetAddress ip, int puerto,boolean ap) {

		setNombre(nombre);
		setIp(ip);
		setPuerto(puerto);
		setApostado(ap);
	}

	public boolean getApostado() {
		return apostado;
	}

	public void setApostado(boolean apostado) {
		this.apostado = apostado;
	}

	public int getApuesta() {
		return apuesta;
	}

	public void setApuesta(int apuesta) {
		this.apuesta = apuesta;
	}

	public int getMonedas() {
		return monedas;
	}

	public void setMonedas(int monedas) {
		this.monedas = monedas;
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
