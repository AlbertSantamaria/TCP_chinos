package edu.upc.eetac.dsa.asantamaria.TCP_chinos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class clienteChinos implements Runnable {

	ServerSocket servidor = null;

	static String nick;

	public clienteChinos() {

		Thread hilo = new Thread(this);
		hilo.start();
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("+++    Bienvenido al Juego de los Chinos!    +++");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.printf("Escriba su nick para poder empezar -->");
		nick = in.next();

		new clienteChinos();

	}

	public void Salir() {

		try {
			servidor.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void enviarJugada(PartidaChip partida) {
		try {
			Socket cli = new Socket("192.168.1.204", 9001);

			System.out.println("[]-->Enviando: " + partida.getMensaje()
					+ " al servidor.");
			// System.out.println(bean.getMensaje());

			ObjectOutputStream flujo_objetos = new ObjectOutputStream(
					cli.getOutputStream());
			flujo_objetos.writeObject(partida);

			cli.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			// Apertura Socket
			servidor = new ServerSocket(9003);
			Socket serv;
			PartidaChip objeto_rx;

			PartidaChip objeto_tx_play = new PartidaChip("PLAY", nick, 0, 0);
			enviarJugada(objeto_tx_play);

			while (true) {

				// Acepta cx entrantes
				serv = servidor.accept();

				// Lee Objeto
				ObjectInputStream flujo_entrada = new ObjectInputStream(
						serv.getInputStream());
				objeto_rx = new PartidaChip();

				// Hay que parsearlo para recibirlo en la instancia local
				objeto_rx = (PartidaChip) flujo_entrada.readObject();

				if (objeto_rx.getMensaje().equalsIgnoreCase("VERSUS")) {
					System.out.println("Jugara la partida contra "
							+ objeto_rx.getNick());

				}
				if (objeto_rx.getMensaje().equalsIgnoreCase("YOUR BET")) {

					Scanner in = new Scanner(System.in);
					System.out.printf("Escriba su apuesta ->");
					int apuesta = in.nextInt();
					System.out.printf("Escriba sus monedas ->");
					int monedas = in.nextInt();

					PartidaChip objeto_tx_mybet = new PartidaChip("MY BET",
							nick, monedas, apuesta);

					enviarJugada(objeto_tx_mybet);

				}
				if (objeto_rx.getMensaje().equalsIgnoreCase("WAIT")) {
					System.out.println("[]<--Recibido: "
							+ objeto_rx.getMensaje()
							+ " Esperando contrincante...");

				}
				if (objeto_rx.getMensaje().equalsIgnoreCase("WAIT BET")) {
					System.out.println("[]<--Recibido: "
							+ objeto_rx.getMensaje()
							+ " Esperando apuesta del contrincante...");

				}
				if (objeto_rx.getMensaje().equalsIgnoreCase("BET OF")) {
					System.out.println("El jugador " + objeto_rx.getNick()
							+ " ha apostado " + objeto_rx.getApuesta());
				}
				if (objeto_rx.getMensaje().equalsIgnoreCase("WINNER")) {
					System.out.println("[]<--Recibido: "+objeto_rx.getNick() + " ha ganado!!");
					servidor.close();
					break;
				}

				// pone mensaje en el formulario
				// System.out.println("[]<--Recibido:" +
				// objeto_rx.getMensaje());

				// cierra
				serv.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
