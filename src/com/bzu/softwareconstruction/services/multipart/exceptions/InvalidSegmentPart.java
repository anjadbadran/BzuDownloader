package com.bzu.softwareconstruction.services.multipart.exceptions;

public class InvalidSegmentPart extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidSegmentPart(){
	}
	
	public InvalidSegmentPart(Throwable e){
		super(e);
	}
}
