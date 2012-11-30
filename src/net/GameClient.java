package net;

import java.io.*;
import java.util.Scanner;
import net.NetworkPlayer;
import java.net.*;

public class GameClient{

	public ServerSocket serv;
	public NetworkPlayer players[];
	public int numberOfPlayers;
	public int round;

	public GameClient(String[] args)throws Exception{
		try{
			if(args.length > 4){
				players = new NetworkPlayer[4];
				numberOfPlayers = 4;
				TCPClientConstructor(args[0], Integer.parseInt(args[1]), args[2],Integer.parseInt(args[3]),
						args[4], Integer.parseInt(args[5]), args[6], Integer.parseInt(args[7]));
			}else if(args.length > 0){
				players = new NetworkPlayer[2];
				numberOfPlayers = 2;
				TCPClientConstructor(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
			}else{
				players = new NetworkPlayer[1];
				TCPClientConstructor();
			}
		}catch(Exception e){}
	}

	public void TCPClientConstructor(String host1, int port1, String host2, int port2,
			String host3, int port3, String host4, int port4) throws Exception{
		String[] hosts = {host1, host2, host3, host4};
		int[] ports = {port1,port2,port3,port4};
		for(int i = 0; i <4;i++)
			try{
				players[i] = new NetworkPlayer(hosts[i], ports[i]);
			}catch(Exception e){System.out.println("Failed to Connect to player " + i+" : " + e);}
	}

	public void TCPClientConstructor(String host1, int port1, String host2, int port2 ) throws Exception{
		String[] hosts = {host1, host2};
		int[] ports = {port1,port2};
		for(int i = 0; i <2;i++)
			try{
				players[i] = new NetworkPlayer(hosts[i], ports[i]);
			}catch(Exception e){System.out.println("Failed to Connect to player " + i+" : " + e);}
	}

	public void TCPClientConstructor() throws Exception{
		try{
			players[0] = new NetworkPlayer("localhost", 6969);
		}catch(Exception e){System.out.println("Failed to Connect: " + e);}
	}

	public void run() throws Exception{
		String move = "";
		String msg = "";
		boolean stillPlaying = true;

		try{
			for(int i = 0; i<numberOfPlayers;i++){
				players[i].write("QUORIDOR " + numberOfPlayers + " "+i);
				msg = players[i].read();
				System.out.println(msg);
				players[i].setPlayerNumber(i);
			}
		}catch(Exception e){System.out.println("Failed"); stillPlaying =false;}
		round = 0;
		move = "";
		Scanner sc;
		String whatever ="";
		
		while(stillPlaying){
			players[round%numberOfPlayers].write("MOVE?");
			if((move = players[round%numberOfPlayers].read()) != null){
				sc = new Scanner(move);
				whatever = sc.next();
				if(whatever.contains("MOVE")){
					msg = "MOVED " + players[round%numberOfPlayers].getPlayerNumber() + "";
					while(sc.hasNext()){
						msg = msg + " " + sc.next();
					}

					for(int i =0; i<numberOfPlayers;i++){
						players[i].write(msg);
					}
				}
				
				round++;
			}
		}

	}

	public static void main(String [] args) throws Exception{
		GameClient client;
		try{
			client = new GameClient(args);
			client.run();
		}catch(Exception e){System.err.println("Failed to connect to move Server " + e);}

	}
}
