package com.bzu.softwareconstruction.services.multipart;

import java.io.File;

import com.bzu.softwareconstruction.segments.SegmentPartUrl;

public class DownloadedFile {
	private File file;
	private boolean isSegment;
	
	public DownloadedFile(File file,boolean isSegment){
		this.file = file;
		this.isSegment = isSegment;
	}
	
	public FilePartUrl toFileParturl() {
		return new SegmentPartUrl(file);
	}

	public void delete() {
		file.delete();
	}

	public File getFile(){
		return this.file;
	}
	
	public boolean isSegment(){
		return this.isSegment;
	}
}
