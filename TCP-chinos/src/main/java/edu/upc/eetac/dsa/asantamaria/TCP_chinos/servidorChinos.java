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

			PartidaStat objeto_rx;
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++     Servidor del Juego de los Chinos!    +++");
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
			System.out.println("[XX]--> Esperando jugadores...");
			System.out.println("");
			while (true) {
				// Acepta cx entrantes
				cli = servidor.accept();

				// Lee Objeto
				ObjectInputStream flujo_entrada = new ObjectInputStream(
						cli.getInputStream());

				objeto_rx = new PartidaStat();

				// Hay que parsearlo para recibirlo en la instancia local
				objeto_rx = (PartidaStat) flujo_entrada.readObject();

			
				if (objeto_rx.getMensaje().equalsIgnoreCase("PLAY")) {

					if (!partida) {
						
					//	jugador0=new Jugador(objeto_rx.getNick(),cli.getInetAddress(),cli.getPort());
						
						jugador0=new Jugador(objeto_rx.getNick(),cli.getInetAddress(),9003);
						partida = true;
						System.out.println("[VX]--> Recibido "+objeto_rx.getMensaje());
						System.out.println("Partida iniciada: Registrado el jugador "
								+ jugador0.getNombre() + " "
								+ jugador0.getIp() + " puerto "
								+ jugador0.getPuerto());

						PartidaStat objeto_tx_wait = new PartidaStat("WAIT",
								"", 0, 0);

						enviarJugada(objeto_tx_wait, jugador0.getIp(),
								jugador0.getPuerto());

					} else {
	//					jugador1=new Jugador(objeto_rx.getNick(),cli.getInetAddress(),cli.getPort());
						jugador1=new Jugador(objeto_rx.getNick(),cli.getInetAddress(),9003);
						
						System.out.println("[VV]--> Recibido "+objeto_rx.getMensaje());
						
						System.out.println("Registrado al jugador 2 "
								+ jugador1.getNombre() + " "
								+ jugador1.getIp() + " puerto "
								+ jugador1.getPuerto());

						PartidaStat objeto_tx_versus0 = new PartidaStat(
								"VERSUS", "jugador1.getNombre()", 0, 0);
						enviarJugada(objeto_tx_versus0, jugador0.getIp(),
								jugador0.getPuerto());

						PartidaStat objeto_tx_versus1 = new PartidaStat(
								"VERSUS", jugador0.getNombre(), 0, 0);
						enviarJugada(objeto_tx_versus1, jugador1.getIp(),
								jugador1.getPuerto());
						
						PartidaStat objeto_tx_yourbet = new PartidaStat(
								"YOUR BET", "", 0, 0);
						enviarJugada(objeto_tx_yourbet, jugador0.getIp(),
								jugador0.getPuerto());

						PartidaStat objeto_tx_waitbet = new PartidaStat(
								"WAIT BET", "", 0, 0);
						enviarJugada(objeto_tx_waitbet, jugador1.getIp(),
								jugador1.getPuerto());
					}

				}
				
				if (objeto_rx.getMensaje().equalsIgnoreCase("MY BET")){
					
					
					
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

	void enviarJugada(PartidaStat partida, InetAddress ip, int puerto) {
		try {
			Socket cli = new Socket(ip, puerto);

			System.out.println("[]-->Enviando: " + partida.getMensaje()+" a "+ip);

			ObjectOutputStream flujo_objetos = new ObjectOutputStream(
					cli.getOutputStream());
			flujo_objetos.writeObject(partida);

			cli.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public void actionPerformed(ActionEvent arg0) {
	//
	// if(arg0.getSource()== btnsalir){
	//
	// try {
	// servidor.close();
	// dispose();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	// }

}
