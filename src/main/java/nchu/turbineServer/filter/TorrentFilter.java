package nchu.turbineServer.filter;

import java.io.File;
import java.io.FilenameFilter;

public class TorrentFilter implements FilenameFilter{
	@Override
	public boolean accept(File dir, String name) {
		if(name.endsWith(".torrent")) return true;
		return false;
	}
}
