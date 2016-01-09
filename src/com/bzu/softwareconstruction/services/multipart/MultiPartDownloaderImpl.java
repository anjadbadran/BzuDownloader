package com.bzu.softwareconstruction.services.multipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bzu.softwareconstruction.segments.SegmentPart;
import com.bzu.softwareconstruction.segments.SegmentPartUrl;
import com.bzu.softwareconstruction.services.multipart.exceptions.InvalidSegmentPart;
import com.bzu.softwareconstruction.utils.DownloadUtils;

public class MultiPartDownloaderImpl implements MultiPartDownloader {
	private static final Logger log = Logger.getLogger(MultiPartDownloaderImpl.class.getName());
	
	@Override
	public InputStream download(FilePartUrl multipart, File outputFolder) throws FileNotFoundException {
		log.info(String.format(">>Downloading segment:%s", multipart.getItemName()));
		File finalFile = createResultFile(outputFolder, multipart.getItemName());
		InputStream inputStream = multipart.openStream();
		ConcurrentLinkedQueue<FilePart> downloadables = new ConcurrentLinkedQueue<>(DownloadUtils.readSegment(inputStream));
		
		//TODO : we might get use of threads here! - use the queue above
		FilePart alternatives;
		int count = 0;
		for(;(alternatives=downloadables.poll())!=null;){
			DownloadedFile downloadedFile = download(alternatives,outputFolder);
			if(downloadedFile.isSegment()){
				downloadables.addAll(DownloadUtils.readSegment(downloadedFile.toFileParturl().openStream()));
			}else{
				accomulateFiles(downloadedFile,count == 0,finalFile);
			}
			downloadedFile.delete();
			log.info(String.format("Downloaded part #%d - still we have %d more parts",count++,downloadables.size()));
		}
		log.info("Finshied downloading file!");
		return new FileInputStream(finalFile);
	}
	
	/**
	 * Create new file in the specified directory
	 * @param outputFolder
	 * @param itemName
	 * @return
	 */
	private File createResultFile(File outputFolder, String itemName) {
		int i = itemName.lastIndexOf('.');
		String resultFileName = itemName.substring(0, i);
		File resultFile = new File(outputFolder, resultFileName);
		int count=1;
		while (resultFile.exists()){
			resultFile = new File(outputFolder, String.format("%s_%s", count,resultFileName));
		}
		try {
			resultFile.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return resultFile;
	}


	private void accomulateFiles(DownloadedFile downloadedFile, boolean isFirstPart, File outputFile) {
		File file = outputFile;
		OutputStream os;
		try {
			os = new FileOutputStream(file,!isFirstPart);
		} catch (FileNotFoundException e) {
			//Should not happen
			throw new RuntimeException(e);
		}
		try {
			try{
				DownloadUtils.copyStream(new FileInputStream(downloadedFile.getFile()),os, new AtomicInteger(0));
			}finally{
				os.close();
			}
		} catch (Exception e) {
			//Should not happen
			throw new RuntimeException(e);
		}
	}


	private DownloadedFile download(FilePart alternatives,File outputFolder){
		
		for(int i=0;i< alternatives.getAlternatives().size();i++){
			FilePartUrl part = alternatives.getAlternatives().get(i);
			try{
				return doDownload(part,outputFolder);
			}catch(Exception e){
				log.log(Level.INFO, String.format("File part #%d failed",i), e);
				//Do nothing, just skip to the next
			}
		}
		//No valid part found here!
		throw new InvalidSegmentPart();
	}
	
	private DownloadedFile doDownload(FilePartUrl part,File outputFolder){
		InputStream inputstream = part.openStream();
		AtomicInteger  downloadedBytes = new AtomicInteger(0);
		File downloadedFile;
		try {
			downloadedFile = DownloadUtils.downloadFile(inputstream, outputFolder, part.getItemName(), downloadedBytes);
			try{
				return new DownloadedFile(downloadedFile, part.isSegment());
			}finally{
				inputstream.close();
			}
		} catch (IOException e) {
			throw new InvalidSegmentPart(e);
		}
	}
	
}