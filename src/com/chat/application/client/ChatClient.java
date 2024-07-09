package com.chat.application.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class ChatClient {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	protected Consumer<String> onMessageReceived;

	public ChatClient(String address, int port, Consumer<String> onMessageReceived) throws IOException {
		System.out.println("Connected to chat server.");
		this.socket = new Socket(address, port);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.onMessageReceived = onMessageReceived;
	}

	public void sendMessage(String message) {
		out.println(message);
	}

	public void startClient() {
		new Thread(() -> {
			try {
				String line;
				while ((line = in.readLine()) != null) {
					onMessageReceived.accept(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
