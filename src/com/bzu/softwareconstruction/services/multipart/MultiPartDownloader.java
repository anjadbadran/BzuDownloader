package com.bzu.softwareconstruction.services.multipart;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface MultiPartDownloader {
	public InputStream download(FilePartUrl partUrl) throws FileNotFoundException;
}
