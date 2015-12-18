package com.bzu.softwareconstruction.services.multipart;

import java.io.InputStream;

public interface FilePartUrl {
	public InputStream openStream();
	public String getItemName();
	public String contentType();
	public boolean isSegment();
}
