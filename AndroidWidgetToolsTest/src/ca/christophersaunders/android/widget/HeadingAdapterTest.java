/*******************************************************************************
 * Copyright (c) 2010 - Christopher Saunders
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package ca.christophersaunders.android.widget;

import ca.christophersaunders.android.R;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HeadingAdapterTest extends AndroidTestCase {
	
	// How the adapter should work if we look at it like an array:
	// [ heading1, 1, 2, 3, 4, heading2, 6, 7, 8, heading3, 10 ]
	String[] headings = { "heading1", "heading2", "heading3" };
	
	String[][] headingData = {
			{"1", "2", "3", "4"},
			{"6", "7", "8"},
			{"10"}
	};
	
	HeadingAdapter testAdapter;
	
	@Override
	public void setUp() {
		testAdapter = new HeadingAdapter(getContext());
		for(int i = 0; i < headings.length; i++) {
			ArrayAdapter<String> adapterForHeading = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, headingData[i]);
			testAdapter.addHeading(headings[i], adapterForHeading);
		}
	}
	
	public void testAdapterSize() {
		assertEquals(11, testAdapter.getCount());
	}
	
	public void testGetHeadingViewFromAdapter() {
		View v = testAdapter.getView(0, null, null);
		assertTrue(v instanceof TextView);
		TextView heading = (TextView) v;
		String message = heading.getText().toString();
		assertEquals("heading1", message);
	}
	
	public void testGetItemViewsFromAdapter() {
		// test the edges as well as an item that is somewhere in the middle
		View leftEdge = testAdapter.getView(1, null, null);
		View middleItem = testAdapter.getView(7, null, null);
		View rightEdge = testAdapter.getView(4, null, null);
		
		assertTrue(leftEdge instanceof TextView);
		assertTrue(middleItem instanceof TextView);
		assertTrue(rightEdge instanceof TextView);
		
		TextView leftEdgeTV = (TextView) leftEdge;
		TextView middleItemTV = (TextView) middleItem;
		TextView rightEdgeTV = (TextView) rightEdge;
		
		assertEquals("1", leftEdgeTV.getText().toString());
		assertEquals("7", middleItemTV.getText().toString());
		assertEquals("4", rightEdgeTV.getText().toString());
	}
	
	public void testGetItemsFromAdapter() {
		// We should not be able to get items for headings since users
		// shouldn't really be interacting with them
		assertNull(testAdapter.getItem(0));
		assertNull(testAdapter.getItem(5));
		assertNull(testAdapter.getItem(9));
		
		String itemValue1 = (String) testAdapter.getItem(1);
		String itemValue2 = (String) testAdapter.getItem(2);
		assertEquals("1", itemValue1);
		assertEquals("2", itemValue2);
	}
	
	public void testGetItemIdsFromAdapter() {
		int heading1pos = 0;
		int heading2pos = 5;
		int heading3pos = 9;
		assertEquals(-1, testAdapter.getItemId(heading1pos));
		assertEquals(-1, testAdapter.getItemId(heading2pos));
		assertEquals(-1, testAdapter.getItemId(heading3pos));
		
		assertEquals(0, testAdapter.getItemId(heading1pos+1));
		assertEquals(0, testAdapter.getItemId(heading2pos+1));
		assertEquals(0, testAdapter.getItemId(heading3pos+1));
		
		assertEquals(1, testAdapter.getItemId(heading1pos+2));
		assertEquals(1, testAdapter.getItemId(heading2pos+2));
		
		assertEquals(2, testAdapter.getItemId(heading1pos+3));
		assertEquals(2, testAdapter.getItemId(heading2pos+3));
		
		assertEquals(3, testAdapter.getItemId(heading1pos+4));
	}

}
