package edu.upc.eetac.dsa.asantamaria.TCP_chinos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class servidorChinos implements Runnable {

	ServerSocket servidor = null;

	Jugador jugador0;
	Jugador jugador1;

	boolean partida;

	public servidorChinos() {

		Thread hilo = new Thread(this);
		// .start llama al run() creado
		hilo.start();
	}

	public static void main(String[] args) {

		new servidorChinos();
	}

	public void run() {
		try {
			// Apertura Socket
			servidor = new ServerSocket(9001);
			Socket cli;

			PartidaChip objeto_rx;
			System.out
					.println("++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out
					.println("+++     Servidor del Juego de los Chinos!    +++");
			System.out
					.println("++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
			System.out.println("[XX]--> Esperando jugadores...");
			System.out.println("");

			while (true) {
				// Acepta cx entrantes
				cli = servidor.accept();

				// Lee Objeto
				ObjectInputStream flujo_entrada = new ObjectInputStream(
						cli.getInputStream());

				objeto_rx = new PartidaChip();

				// Hay que parsearlo para recibirlo en la instancia local
				objeto_rx = (PartidaChip) flujo_entrada.readObject();

				if (objeto_rx.getMensaje().equalsIgnoreCase("PLAY")) {

					if (!partida) {

						// Jugador(objeto_rx.getNick(),cli.getInetAddress(),cli.getPort());

						jugador0 = new Jugador(objeto_rx.getNick(),
								cli.getInetAddress(), 9003, false);
						partida = true;
						System.out.println("[VX]<-- Recibido "
								+ objeto_rx.getMensaje());
						System.out
								.println("Partida iniciada: Registrado el jugador "
										+ jugador0.getNombre()
										+ " "
										+ jugador0.getIp()
										+ " puerto "
										+ jugador0.getPuerto());

						PartidaChip objeto_tx_wait = new PartidaChip("WAIT",
								"", 0, 0);

						enviarJugada(objeto_tx_wait, jugador0.getIp(),
								jugador0.getPuerto());

					} else {

						// Jugador(objeto_rx.getNick(),cli.getInetAddress(),cli.getPort());
						jugador1 = new Jugador(objeto_rx.getNick(),
								cli.getInetAddress(), 9003, false);

						System.out.println("[VV]--> Recibido "
								+ objeto_rx.getMensaje());

						System.out.println("Registrado al jugador 2 "
								+ jugador1.getNombre() + " " + jugador1.getIp()
								+ " puerto " + jugador1.getPuerto());

						PartidaChip objeto_tx_versus0 = new PartidaChip(
								"VERSUS", jugador1.getNombre(), 0, 0);
						enviarJugada(objeto_tx_versus0, jugador0.getIp(),
								jugador0.getPuerto());

						PartidaChip objeto_tx_versus1 = new PartidaChip(
								"VERSUS", jugador0.getNombre(), 0, 0);
						enviarJugada(objeto_tx_versus1, jugador1.getIp(),
								jugador1.getPuerto());

						PartidaChip objeto_tx_yourbet = new PartidaChip(
								"YOUR BET", "", 0, 0);
						enviarJugada(objeto_tx_yourbet, jugador0.getIp(),
								jugador0.getPuerto());

						PartidaChip objeto_tx_waitbet = new PartidaChip(
								"WAIT BET", "", 0, 0);
						enviarJugada(objeto_tx_waitbet, jugador1.getIp(),
								jugador1.getPuerto());
					}

				}

				if (objeto_rx.getMensaje().equalsIgnoreCase("MY BET")) {

					// Respuesta Bet of a ambos

					PartidaChip objeto_tx_betof = new PartidaChip("BET OF",
							objeto_rx.getNick(), objeto_rx.getMonedas(),
							objeto_rx.getApuesta());
					enviarJugada(objeto_tx_betof, jugador0.getIp(),
							jugador0.getPuerto());
					enviarJugada(objeto_tx_betof, jugador1.getIp(),
							jugador1.getPuerto());

					if (objeto_rx.getNick().equalsIgnoreCase(
							jugador0.getNombre())
							&& !jugador0.apostado) {

						// Se registra la apuesta

						jugador0.setMonedas(objeto_rx.getMonedas());
						jugador0.setApuesta(objeto_rx.getApuesta());
						jugador0.setApostado(true);

						// Se envia un wait bet al 0 y un yourbet al 1

						PartidaChip objeto_tx_waitbet = new PartidaChip(
								"WAIT BET", "", 0, 0);
						enviarJugada(objeto_tx_waitbet, jugador0.getIp(),
								jugador0.getPuerto());

						PartidaChip objeto_tx_yourbet = new PartidaChip(
								"YOUR BET", "", 0, 0);
						enviarJugada(objeto_tx_yourbet, jugador1.getIp(),
								jugador1.getPuerto());

					}
					if (objeto_rx.getNick().equalsIgnoreCase(
							jugador1.getNombre())
							&& !jugador1.apostado) {

						jugador1.setMonedas(objeto_rx.getMonedas());
						jugador1.setApuesta(objeto_rx.getApuesta());
						jugador1.setApostado(true);

					}
				}

				if (partida) {
					if (jugador0.getApostado() && jugador1.getApostado()) {

						int monedas = jugador0.getMonedas()
								+ jugador1.getMonedas();
						
		
						if (monedas == jugador0.getApuesta()) {

							PartidaChip objeto_tx_win = new PartidaChip(
									"WINNER", jugador0.getNombre(), 0, 0);
							enviarJugada(objeto_tx_win, jugador0.getIp(),
									jugador0.getPuerto());
							enviarJugada(objeto_tx_win, jugador1.getIp(),
									jugador1.getPuerto());
							System.out.println("[]--> " + jugador0.getNombre()
									+ " ha ganado!");
							servidor.close();
						}
						if (monedas==jugador1.getApuesta()) {

							PartidaChip objeto_tx_win = new PartidaChip(
									"WINNER", jugador1.getNombre(), 0, 0);
							enviarJugada(objeto_tx_win, jugador0.getIp(),
									jugador0.getPuerto());
							enviarJugada(objeto_tx_win, jugador1.getIp(),
									jugador1.getPuerto());
							System.out.println("[]--> " + jugador1.getNombre()
									+ " ha ganado!");
							servidor.close();
						
						} else {
							PartidaChip objeto_tx_win = new PartidaChip(
									"WINNER", "Nadie", 0, 0);
							enviarJugada(objeto_tx_win, jugador0.getIp(),
									jugador0.getPuerto());
							enviarJugada(objeto_tx_win, jugador1.getIp(),
									jugador1.getPuerto());
							System.out.println("[]--> Nadie ha ganado!");
							servidor.close();
						}
					}
				}
				// pone mensaje en el formulario

				if (objeto_rx.getMensaje().equalsIgnoreCase("FIN")) {
					servidor.close();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void enviarJugada(PartidaChip partida, InetAddress ip, int puerto) {
		try {
			Socket cli = new Socket(ip, puerto);

			System.out.println("[]-->Enviando: " + partida.getMensaje() + " a "
					+ ip);

			ObjectOutputStream flujo_objetos = new ObjectOutputStream(
					cli.getOutputStream());
			flujo_objetos.writeObject(partida);

			cli.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
