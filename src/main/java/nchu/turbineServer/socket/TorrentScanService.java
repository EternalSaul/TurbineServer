package nchu.turbineServer.socket;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.filechooser.FileSystemView;

import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.common.Torrent;

import nchu.turbineServer.filter.TorrentFilter;

public class TorrentScanService extends Thread{
	static File diretory;
	static TorrentFilter torrentfilter=new TorrentFilter();
	
	public static File getDiretory() {
		return diretory;
	}

	public static void setDiretory(File diretory) {
		TorrentScanService.diretory = diretory;
	}

	@Override
	public void run() {
		diretory=new File(FileSystemView.getFileSystemView().getDefaultDirectory()+"/TurbineServer");
		while(true){
			if(!diretory.exists()) diretory.mkdir();
			try {
				scanTorrent();
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void scanTorrent() throws NoSuchAlgorithmException, IOException{
		File torrents[]=diretory.listFiles(torrentfilter);
		for(File t:torrents){
			Torrent torrent=Torrent.load(t);
			String th=torrent.getHexInfoHash();
			if(!t.getName().equals(th+".torrent")){
				File rename=new File(diretory+"/"+th+".torrent");
				if(!rename.exists())
				t.renameTo(rename);
				else
				t.delete();
			}
			System.out.println(t.getName());
		}
	}
}
