package com.chat.application.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.chat.application.handlers.ClientHandler;

public class ChatServer {

	private static List<ClientHandler> clients = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(5000);
		System.out.println("Server started. Waiting for clients....");

		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("client connected." + clientSocket);
			ClientHandler clientThread = new ClientHandler(clientSocket, clients);
			clients.add(clientThread);
			new Thread(clientThread).start();
		}

	}
}
