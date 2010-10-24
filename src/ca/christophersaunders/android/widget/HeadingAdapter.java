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

import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import ca.christophersaunders.android.R;

public class HeadingAdapter extends BaseAdapter {
	
	/**
	 * Used to inform the listener when a heading has disappeared
	 * so that it can be displayed in static manner on screen.
	 * 
	 * Currently this doesn't function, since the details of getting
	 * it to work properly still haven't been ironed out perfectly.
	 * @author csaunders
	 *
	 */
	public static interface OnHeadingChangeListener {
		public void headingChanged(HeadingAdapter adapter);
	}
	
	private static final int HEADING_TYPE = 0;
	
	private HashMap<String, Adapter> headingsMapping = new HashMap<String, Adapter>();
	private ArrayAdapter<String> headings;
	private String currentHeading = null;
	private OnHeadingChangeListener onHeadingChangeListener = null;
	
	public HeadingAdapter(Context context) {
		super();
		headings = new ArrayAdapter<String>(context, R.layout.heading_adapter_heading);
	}
	
	public HeadingAdapter(Context context, int headingViewResource) {
		super();
		headings = new ArrayAdapter<String>(context, headingViewResource);
	}
	

	public int getCount() {
		int count = 0;
		for(int i = 0; i < headings.getCount(); i++) {
			String heading = headings.getItem(i);
			count += headingsMapping.get(heading).getCount() + 1;
		}
		return count;
	}
	
	@Override
	public int getViewTypeCount() {
		// We need to assume that at least the headers will be there
		int count = 1;
		for(int i = 0; i < headings.getCount(); i++) {
			String heading = headings.getItem(i);
			count += headingsMapping.get(heading).getViewTypeCount();
		}
		return count;
	}
	
	@Override
	public int getItemViewType(int position) {
		int type = 1;
		for(int i = 0; i < headings.getCount(); i++) {
			if(position == 0) {
				return HEADING_TYPE;
			} else {
				position--;
			}
			
			String heading = headings.getItem(i);
			Adapter adapter = headingsMapping.get(heading);
			
			int adapterLength = adapter.getCount(); 
			if(position < adapterLength) {
				return type + adapter.getItemViewType(position);
			}
			
			position -= adapterLength;
		}
		
		return -1;
	}

	public Object getItem(int position) {
		for(int headingPosition = 0; headingPosition < headings.getCount(); headingPosition++) {
			if(position == 0) {
				return null;
			} else {
				position--;
			}
			
			String heading = headings.getItem(headingPosition);
			Adapter adapter = headingsMapping.get(heading);
			int adapterLength = adapter.getCount();
			
			if(position < adapterLength) {
				return adapter.getItem(position); 
			}
			
			// We need to ensure that we decrement our position by the
			// number of items in the adapter as well as for our header
			position -= adapterLength;
		}
		return null;
	}

	public long getItemId(int position) {
		for(int i = 0; i < headings.getCount(); i++) {
			if(position == 0) {
				return -1;
			} else {
				position--;
			}
			
			String heading = headings.getItem(i);
			Adapter adapter = headingsMapping.get(heading);
			
			int adapterLength = adapter.getCount();
			if(position < adapterLength) {
				return adapter.getItemId(position);
			}
			
			// We need to ensure that we decrement our position by the
			// number of items in the adapter as well as for our header			
			position -= adapterLength;
		}
		return -1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		for(int i = 0; i < headings.getCount(); i++) {
			String heading = headings.getItem(i);
			if(position == 0) {
				return headings.getView(i, convertView, parent);
			} else {
				position--;
			}
			
			Adapter adapter = headingsMapping.get(heading);
			
			int adapterLength = adapter.getCount();
			if(position < adapterLength) {
				return adapter.getView(position, convertView, parent);
			}
			
			// We need to ensure that we decrement our position by the
			// number of items in the adapter as well as for our header
			position -= (adapterLength);
		}
		return null;
	}
	
	public String getCurrentHeading() {
		return currentHeading;
	}
	
	public void setOnHeadingChangeListener(OnHeadingChangeListener listener) {
		this.onHeadingChangeListener = listener;
	}
	
	public OnHeadingChangeListener getOnHeadingChangeListener() {
		return onHeadingChangeListener;
	}
	
	/**
	 * Places the heading and adapter at the end of the list.
	 * @param heading The Title of the Heading
	 * @param adapterForHeading The adapter for this heading to pull data from
	 */
	public void addHeading(String heading, Adapter adapterForHeading) {
		headings.add(heading);
		headingsMapping.put(heading, adapterForHeading);
	}

}
