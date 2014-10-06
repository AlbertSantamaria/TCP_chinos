package edu.upc.eetac.dsa.asantamaria.TCP_chinos;

import java.io.Serializable;

public class PartidaStat implements Serializable {

	String mensaje;
	String nick;
	int apuesta;
	int monedas;
	boolean activa;

	PartidaStat() {
	}

	PartidaStat(String msg, String nick, int monedas, int apuesta) {

		setMensaje(msg);
		setNick(nick);
		setMonedas(monedas);
		setApuesta(apuesta);

	}
	
	PartidaStat(String msg, String nick, int monedas, int apuesta,boolean activo) {

		setMensaje(msg);
		setNick(nick);
		setMonedas(monedas);
		setApuesta(apuesta);
		setActiva(activo);
	}

	public boolean getActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public String getMensaje() {

		return mensaje;
	}

	public String getNick() {

		return nick;
	}

	public void setMensaje(String msg) {

		this.mensaje = msg;
	}

	public void setNick(String id) {

		this.nick = id;
	}

	public int getApuesta() {

		return apuesta;
	}

	public int getMonedas() {

		return monedas;
	}

	public void setMonedas(int monedas) {

		this.monedas = monedas;
	}

	public void setApuesta(int apuesta) {

		this.apuesta = apuesta;
	}
}
