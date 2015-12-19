package com.bzu.softwareconstruction.segments;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bzu.softwareconstruction.services.multipart.FilePartUrl;
import com.bzu.softwareconstruction.services.multipart.exceptions.InvalidItemURL;

public class SegmentPartUrl implements FilePartUrl{
	private static final Logger log = Logger.getLogger(SegmentPartUrl.class.getName());

	private String url;
	private String contentType;
	
	public SegmentPartUrl(String url){
		if(url == null || url.isEmpty()){
			throw new IllegalArgumentException("url not must have a value");
		}
		this.url = url;
	}

	public SegmentPartUrl(File file) {
		url = file.toURI().toString();
	}

	@Override
	public InputStream openStream() {
		try {
			URL url = new URL(this.url);
			log.log(Level.INFO, this.url);
			
			URLConnection connection = url.openConnection();
			connection.connect();

			//contentType = connection.getContentType();
			String contentType = connection.getContentType();

			log.log(Level.INFO, connection.toString());
			log.log(Level.INFO, contentType);


			InputStream is = connection.getInputStream();
			is.getClass().getName();
			return is;
		} catch (IOException e) {
			throw new InvalidItemURL(e);
		}
	}

	@Override
	public String getItemName() {
		//TODO : is there systems with \ instead?
		String[] urlPath = url.split("/");//Assumed / is the separator 
		return urlPath[urlPath.length-1];//Last part is the name
	}
	
	private void insureStreamOpened(){
		if(contentType == null){
		//	throw new IllegalStateException("You must openStream() before requesting contentType!!");
		}
	}
	@Override
	public String contentType() {
		insureStreamOpened();
		return contentType;
	}
	
	@Override
	public boolean isSegment() {
		insureStreamOpened();
		if ( this.contentType != null ) {
			if ( this.contentType.toLowerCase().equals("text/segments-manifest")) {
				return  true;
			}
		} else if (this.url.toLowerCase().endsWith("segments")) {
			return true ;
		}
		return false;
				
	}
}
