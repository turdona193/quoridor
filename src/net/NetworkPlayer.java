package net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.annotation.Resource;

/**
 *  Network Player Contains Input and Output streams that are used to communicate between GameClient and MoveServer.
 *
 */
public class NetworkPlayer {

	/**
	 * Socket that is created to communicate between client and server.
	 */
	private Socket socket;
	/**
	 * reader is a BufferedReader to listen on socket for a communication.
	 */
	private BufferedReader reader;
	/**
	 * writer is a DataOutputStream to write to socket for communication.
	 */
	private DataOutputStream writer;
	/**
	 * 
	 * playerNumber, which contains the player number for the current game it is in.
	 */
	private int playerNumber;

	/**
	 * Class constructor. 
	 * Creates Network Player that opens a socket that connects to host at port.
	 * 
	 * @param host the host name.
	 * @param port the port number.
	 * @throws Exception Throws I/O exception if error when creating socket.
	 */
	public NetworkPlayer(String host, int port) throws Exception{
		try{
			socket = new Socket(host, port);
			writer = new DataOutputStream(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(Exception e){System.out.println("Network Player Failed to Connect: " + e);}
	}

	/**
	 * Class constructor.
	 * Creates a Network Player using a pre-existing socket.
	 * 
	 * @param connect Socket to construct the Network Player on.
	 * @throws Exception Throws I/O exception if error when creating socket.
	 */
	public NetworkPlayer(Socket connect) throws Exception{
		try{
			socket = connect;
			writer = new DataOutputStream(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(Exception e){System.out.println("Network Player Failed to Connect: " + e);}
	}

	/**
	 * Allows the user to send a String from the Socket.
	 * 
	 * @param write Writes string to the socket.
	 * @throws Exception Throws I/O exception if error when created socket.
	 */
	public void write(String write) throws Exception{
		try{
			writer.writeBytes(write + "\n");
			writer.flush();
		}catch(Exception e){System.err.println("Player couldn't write: " + e);}
	}

	/**
	 * Reads in a String from the Socket.
	 * 
	 * @return String that was received.
	 * @throws Exception Throws I/O exception if error when created socket.
	 */
	public String read() throws Exception{
		try{
			return reader.readLine();
		}catch(Exception e){System.err.println("Player couldn't read: " + e);}
		return "";
	}

	/**
	 * Closes Socket.
	 * 
	 * @throws Exception Throws I/O exception if error when created socket.
	 */

	public void close() throws Exception{
		try{
			socket.close();
		}catch(Exception e){}
	}

	/**
	 * Sets private int value 'playerNumber', which contains the player number for the current game it is in.
	 * 
	 * @param pn int that playerNumber will be set to.
	 */
	public void setPlayerNumber(int pn){
		playerNumber = pn;
	}

	/**
	 * gets private int value 'playerNumber', which contains the player number for the current game it is in.
	 * 
	 * @return playerNumber 
	 */
	public int getPlayerNumber(){
		return playerNumber;
	}

	public static void main(String[] args) {
		try{
			NetworkPlayer player = new NetworkPlayer("localhost", 6969);
			player.write("Hello");
		}catch(Exception e){System.err.println("Network Player failed at creating new: " + e);}
	}
}