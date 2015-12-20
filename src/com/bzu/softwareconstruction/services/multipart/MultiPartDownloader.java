package com.bzu.softwareconstruction.services.multipart;

import java.io.File;

public interface MultiPartDownloader {
	public void download(FilePartUrl partUrl,File outputFolder);
}
