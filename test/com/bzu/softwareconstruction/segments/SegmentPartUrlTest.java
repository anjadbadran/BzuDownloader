package com.bzu.softwareconstruction.segments;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bzu.softwareconstruction.services.multipart.exceptions.InvalidItemURL;

public class SegmentPartUrlTest {

	@Test
	public void testIsSegmentTrue() {

		SegmentPartUrl segment = new SegmentPartUrl("http://151.80.134.50/data/text_test.segments");
		assertEquals(true, segment.isSegment());
	}

	@Test
	public void testIsSegmentFalse() {

		SegmentPartUrl segment = new SegmentPartUrl("http://151.80.134.50/data/text.txt-segment1");
		assertEquals(false, segment.isSegment());
	}

	@Test(expected = InvalidItemURL.class)
	public void openStreamBadUrl() {
		SegmentPartUrl segment = new SegmentPartUrl("http://151.80.134.50/data/text.txt-badrurl");
		segment.openStream();
	}

	@Test 
	public void openStreamGoodUrl() {
		SegmentPartUrl segment = new SegmentPartUrl("http://151.80.134.50/data/text.txt-segment1");
		assertEquals("sun.net.www.protocol.http.HttpURLConnection$HttpInputStream", segment.openStream().getClass().getName());
	}
}
