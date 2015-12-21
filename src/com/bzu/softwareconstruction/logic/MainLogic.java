package com.bzu.softwareconstruction.logic;

import java.io.File;
import java.io.FileNotFoundException;

import com.bzu.softwareconstruction.segments.SegmentPartUrl;
import com.bzu.softwareconstruction.services.Services;


public class MainLogic {
	public static void main(String args[]) throws FileNotFoundException{
		//Segment segment = "/test_data/text_test.segments")));
		String baseUrl = "file:/Users/ghalebkhaled/Documents/workspace/Downloader/";
		Services.multipartDownloader.download(new SegmentPartUrl(baseUrl+"test_data/text_test.segments"), new File("./test_output/"));
		Services.multipartDownloader.download(new SegmentPartUrl(baseUrl+"test_data2/test2.segments"), new File("./test_output/"));

	}
}
