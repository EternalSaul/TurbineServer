package nchu.turbineServer.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MagnetServerSocket {
//	ServerSocket serverSocket;
	ExecutorService tp=Executors.newFixedThreadPool(10);
	public void start() {
		new TorrentScanService().start();
		try(ServerSocket serverSocket=new ServerSocket(6868)) {
			while(true){
				Socket socket=serverSocket.accept();
				tp.submit(new MagnetService(socket));	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
