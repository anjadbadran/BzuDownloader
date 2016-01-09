package com.bzu.softwareconstruction.services.multipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bzu.softwareconstruction.segments.SegmentPart;
import com.bzu.softwareconstruction.services.multipart.exceptions.InvalidSegmentPart;
import com.bzu.softwareconstruction.utils.DownloadUtils;

public class MultiPartDownloaderImpl implements MultiPartDownloader {
	private static final Logger log = Logger.getLogger(MultiPartDownloaderImpl.class.getName());
	
	@Override
	public InputStream download(FilePartUrl multipart) throws FileNotFoundException {
		log.info(String.format(">>Downloading segment:%s", multipart.getItemName()));
		List<InputStream> filesInputStream = new ArrayList<>();
		
		ConcurrentLinkedQueue<FilePart> downloadables = new ConcurrentLinkedQueue<>();
		InputStream inputStream = multipart.openStream();
		if(multipart.isSegment()){
			List<FilePart> readSegment = DownloadUtils.readSegment(inputStream);
			downloadables.addAll(readSegment);
		}else{
			downloadables.add(new SegmentPart(multipart));
		}
		//TODO : we might get use of threads here! - use the queue above
		FilePart alternatives;
		int count = 0;
		for(;(alternatives=downloadables.poll())!=null;){
			InputstreamAndType downloadedFile = download(alternatives);
			if(downloadedFile.isSegment){
				downloadables.addAll(DownloadUtils.readSegment(downloadedFile.is));
			}else{
				filesInputStream.add(downloadedFile.is);
			}
			log.info(String.format("Downloaded part #%d - still we have %d more parts",count++,downloadables.size()));
		}
		log.info("Finshied downloading file!");
		return new MultipartInputStream(filesInputStream);
	}


	private InputstreamAndType download(FilePart alternatives){
		
		for(int i=0;i< alternatives.getAlternatives().size();i++){
			FilePartUrl part = alternatives.getAlternatives().get(i);
			try{
				return new InputstreamAndType(part.openStream(), part.isSegment());
			}catch(Exception e){
				log.log(Level.INFO, String.format("File part #%d failed",i), e);
				//Do nothing, just skip to the next
			}
		}
		//No valid part found here!
		throw new InvalidSegmentPart();
	}
	
	private class InputstreamAndType{
		InputStream is;
		boolean isSegment;
		InputstreamAndType(InputStream is,boolean isSegment){
			this.is = is;
			this.isSegment = isSegment;
		}
	}
}