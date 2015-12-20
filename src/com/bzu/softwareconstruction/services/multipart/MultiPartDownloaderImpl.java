package com.bzu.softwareconstruction.services.multipart;

import java.io.File;
import java.util.logging.Logger;

public class MultiPartDownloaderImpl implements MultiPartDownloader {
	private static final Logger log = Logger.getLogger(MultiPartDownloaderImpl.class.getName());
	
	@Override
	public void download(FilePartUrl multipart, File outputFolder) {
		throw new RuntimeException("TODO");
	}
}
