package com.bzu.softwareconstruction.services;

import com.bzu.softwareconstruction.services.multipart.MultiPartDownloader;
import com.bzu.softwareconstruction.services.multipart.MultiPartDownloaderImpl;

public class Services {
	public static final MultiPartDownloader multipartDownloader = new MultiPartDownloaderImpl();
}
