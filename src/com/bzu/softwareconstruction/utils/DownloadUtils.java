package com.bzu.softwareconstruction.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.bzu.softwareconstruction.logic.Config;
import com.bzu.softwareconstruction.segments.SegmentPart;
import com.bzu.softwareconstruction.segments.SegmentPartUrl;
import com.bzu.softwareconstruction.services.multipart.FilePart;

public class DownloadUtils {
	private static final Logger log = Logger.getLogger(DownloadUtils.class.getName());
	
	/**
	 * Read segment content and build multiple {@link SegmentPart}s
	 * @param segmentFile
	 * @return
	 */
	public static List<FilePart> readSegment(InputStream segmentFile) {
		Scanner scanner = new Scanner(segmentFile);
		List<FilePart> segmentItemGroup = new ArrayList<>();
		SegmentPart group = new SegmentPart();
		int lineIndex = 0;
		while(scanner.hasNext()){
			String line = scanner.nextLine();
			lineIndex++;
			// This is separator for the segments e.g. "**"
			if (Config.SEGMENT_ITEM_SEPERATOR.equals(line)) {
				if (group.isEmpty()) {
					log.warning("Found segment group without urls! - at row # " + lineIndex);
					log.warning("Skipping this group!");
				} else {
					segmentItemGroup.add((FilePart) group);
					group = new SegmentPart();
				}
			} else {
				group.addSegmentItem(new SegmentPartUrl(line));
			}
		}
		if(lineIndex!=0){
			segmentItemGroup.add(group);
		}
		scanner.close();
		assert !segmentItemGroup.isEmpty();
		log.info(String.format("reading %d line - found %d parts", lineIndex,segmentItemGroup.size()));
		return segmentItemGroup;
	}
	
	public static File downloadFile(InputStream is, File folder,String fileName,AtomicInteger readBytes) throws IOException{
		folder.mkdirs();
		File newFile = new File(folder, fileName);
		newFile.createNewFile();
		OutputStream outputStream = new FileOutputStream(newFile);
		copyStream(is, outputStream, readBytes);
		return newFile;
	}

	public static void copyStream(InputStream is, OutputStream outputStream, AtomicInteger readBytes)
			throws IOException {
		byte[] buffer = new byte[1024];//1 kb at a time
		int bytes = 0;
		while((bytes = is.read(buffer))!=-1){
			outputStream.write(buffer, 0, bytes);
			readBytes.addAndGet(bytes);
		}
		outputStream.close();
	}

}
