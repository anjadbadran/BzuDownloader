/**
 * 
 */
package com.bzu.softwareconstruction.services.multipart;
import com.bzu.softwareconstruction.services.multipart.exceptions.InvalidItemURL;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import com.bzu.softwareconstruction.segments.SegmentPartUrl;
import com.bzu.softwareconstruction.services.multipart.exceptions.InvalidItemURL;

/**
 * @author hamados
 *
 */
public class MultiPartDownloaderImplTest {

	@Test(expected = InvalidItemURL.class)
	public void downloadBadOutPutFolderTest() throws FileNotFoundException {
		MultiPartDownloaderImpl download = new MultiPartDownloaderImpl();
		download.download(new SegmentPartUrl("http://151.80.134.50/data/text_test.segments"));
	}
	
	@Test(expected = InvalidItemURL.class)
	public void downloadBadUrlTest() throws FileNotFoundException {
	    MultiPartDownloaderImpl download = new MultiPartDownloaderImpl();
		download.download(new SegmentPartUrl("http://151.80.134.50/data/text.txt.badurl"));
	}
}
