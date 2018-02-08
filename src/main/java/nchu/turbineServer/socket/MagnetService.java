package nchu.turbineServer.socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;

public class MagnetService implements Callable<byte[]>{
	Socket socket;
	
	public MagnetService(Socket socket) {
		this.socket = socket;
	}

	@Override
	public byte[] call() throws Exception {
		//�鿴http����ͷ��
		InputStream is=socket.getInputStream();
		InputStreamReader reader=new InputStreamReader(is,"utf-8");
		BufferedReader br=new BufferedReader(new InputStreamReader(is));//ʹ�ø÷��������л���  new InputSteramReader()���ֽ���ת��Ϊ�ַ���
		String name=br.readLine();
		
		//��ȡTorrent SHA��
		String hash=name.split(" ")[1].replaceAll("/", "");
		File dir=TorrentScanService.getDiretory();
		File torrent=new File(dir+"/"+hash+".torrent");
		
		//��������ͷ��
        String header="HTTP/1.0 200 OK\r\n"  
                +"Server:MagnetFile 2.0\r\n"  
                +"Content-length:"+torrent.length()+"\r\n"  
                +"Content-type:application/x-bittorrent\r\n\r\n";  
        
		if(torrent.exists()){
			OutputStream os=socket.getOutputStream();
			FileInputStream fs=new FileInputStream(torrent);//��ȡ�ļ��е�����
			System.out.println(fs.available());
			byte b[]=new byte[4096];
			os.write(header.getBytes(Charset.forName("utf-8")));
			int len=0;
			int k=0;
			while((len=fs.read(b, 0, 4096))!=-1){
				k+=len;
				os.write(b,0, len);
				os.flush();
			}
//			os.flush();
			System.out.println(k+"��д");
			socket.close();
			return b;
		}else{
			String notFound="HTTP/1.0 404 Not Found\r\n\r\n";
			OutputStream os=socket.getOutputStream();
			os.write(notFound.getBytes(Charset.forName("utf-8")));
			os.flush();
			socket.close();
		}
		
	/*	name=(name.s)*/
		
		return null;
	}

}
