package com.bzu.softwareconstruction.segments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bzu.softwareconstruction.services.multipart.FilePart;
import com.bzu.softwareconstruction.services.multipart.FilePartUrl;

public class SegmentPart implements FilePart{
	private List<FilePartUrl> itemsUrls = new ArrayList<>();
	public SegmentPart() {
	}
	public SegmentPart(File file) {
		addSegmentItem(new SegmentPartUrl(file));
	}

	public void addSegmentItem(FilePartUrl segmentItem) {
		if(segmentItem == null){
			throw new NullPointerException();
		}
		this.itemsUrls.add(segmentItem);
	}
	
	public boolean isEmpty(){
		return itemsUrls.isEmpty();
	}

	@Override
	public List<FilePartUrl> getAlternatives() {
		return itemsUrls;
	}
}