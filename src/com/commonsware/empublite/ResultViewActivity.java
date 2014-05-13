package com.commonsware.empublite;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.StaticLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.facebook.*;
import com.facebook.model.*;
import android.widget.TextView;
import android.content.Intent;

public class ResultViewActivity extends Activity {
	ArrayList<String> covers = new ArrayList<String>();
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> genres = new ArrayList<String>();
	ArrayList<String> years = new ArrayList<String>();
	ArrayList<String> titles = new ArrayList<String>();
	ArrayList<String> artists = new ArrayList<String>();
	ArrayList<String> samples = new ArrayList<String>();
	ArrayList<String> performers = new ArrayList<String>();
	ArrayList<String> composers = new ArrayList<String>();
	ArrayList<String> details = new ArrayList<String>();
    
    Bundle bundle = new Bundle();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_view);
		Bundle b = getIntent().getExtras();
		String type = b.getString("type");
		String results = b.getString("result");
		int noResultFlag = 0;// 1 for no result
		
		JSONObject jObject=null;
		JSONArray jArray=null;
		String whitespace = "     ";



	    
		try {
			jObject = new JSONObject(results);
			
			//delete tag "results"
			results =jObject.getString("results");
			
			if (results.equals("")) {
				noResultFlag=1;
			}
			
			else {
				jObject = new JSONObject(results);
				
				jArray=jObject.getJSONArray("result");
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject oneObject = jArray.getJSONObject(i);
					
					if(type.equals("artists")){
						covers.add(oneObject.getString("cover"));
						names.add(oneObject.getString("name"));
						genres.add(oneObject.getString("genre"));
						years.add(oneObject.getString("year"));
						details.add(oneObject.getString("detail"));
					}
					
					else if(type.equals("albums")){
						covers.add(oneObject.getString("cover"));
						titles.add(oneObject.getString("title"));
						artists.add(oneObject.getString("artist"));
						years.add(oneObject.getString("genre"));
						genres.add(oneObject.getString("year"));
						details.add(oneObject.getString("detail"));
					}
					
					else if(type.equals("songs")){
						samples.add(oneObject.getString("sample"));
						titles.add(oneObject.getString("title"));
						performers.add(oneObject.getString("performer"));
						composers.add(oneObject.getString("composer"));
						details.add(oneObject.getString("detail"));
					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ScrollView sv = new ScrollView(this);
        TableLayout ll=new TableLayout(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        if (type.equals("artists")) {
        	if (noResultFlag==0) {
    	        for(int i=0;i<jArray.length();i++) {
    	            TableRow tbrow=new TableRow(this);
    	            tbrow.setId(i);
    	            bundle.putString("type", "artists");
    	            
    	            String cover = covers.get(i);
    	            

    	            //show cover
    	        	ImageView coverImageView = new ImageView(this);
    	        	URL url = null;
    	            if ((!(cover.equals("N/A ")))&&(!(cover.equals("N/A")))) {
    	            	try {
    	            		url = new URL(cover);
    	                	Bitmap coverImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
    	                	bundle.putString("cover"+i, cover);
    	                	coverImageView.setImageBitmap(coverImage);	
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    				}
    	            else{
    	            	try {
    						url = new URL("http://images.hemmings.com/search_ad/ni.jpg");
    			        	Bitmap coverImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
    			        	bundle.putString("cover"+i, "http://images.hemmings.com/search_ad/ni.jpg");
    			        	coverImageView.setImageBitmap(coverImage);	
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    	            }

    	        	//out & inside layout should be same
    	        	TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(150, 100);
    	        	coverImageView.setLayoutParams(layoutParams);
    	        	
    	            tbrow.addView(coverImageView);

    	            //show name
    	            TextView name= new TextView(this);
    	            name.setText(whitespace+"Name:\n"+whitespace+names.get(i));
    	            bundle.putString("name"+i, names.get(i));
    	            tbrow.addView(name);
    	            
    	            //show genre
    	            TextView genre= new TextView(this);
    	            genre.setText(whitespace+"Genre:\n"+whitespace+genres.get(i));
    	            bundle.putString("genre"+i, genres.get(i));
    	            tbrow.addView(genre);
    	            
    	            //show year
    	            TextView year= new TextView(this);
    	            year.setText(whitespace+"Year:\n"+whitespace+years.get(i));
    	            bundle.putString("year"+i, years.get(i));
    	            tbrow.addView(year);
    	            
    	            //detail
    	            bundle.putString("detail"+i,details.get(i));
    	            
    	            tbrow.setClickable(true);
    	            tbrow.setOnClickListener(new View.OnClickListener() {
    	            
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							int id = v.getId();
		                    Intent intent = new Intent(ResultViewActivity.this, FaceBookActivity.class);
		                    bundle.putString("id", v.getId()+"");
		                    intent.putExtras(bundle);
		                    /*
		        	        covers.clear();
		        	        titles.clear();
		        	        performers.clear();
		        	        composers.clear();
		        	        details.clear();
		        	        */
		                    startActivity(intent);
						}
					});
    	            
    	            ll.addView(tbrow);

    	        }
    	        /*
    	        covers.clear();
    	        names.clear();
    	        genres.clear();
    	        years.clear();
    	        */

    		}
    		else {
                TableRow tbrow=new TableRow(this);
                TextView noResult= new TextView(this);
                noResult.setText("no result");
                tbrow.addView(noResult);
                ll.addView(tbrow);
    		}

		}
		
        else if (type.equals("albums")) {
        	if (noResultFlag==0) {
    	        for(int i=0;i<jArray.length();i++) {
    	            TableRow tbrow=new TableRow(this);
    	            tbrow.setId(i);
    	            bundle.putString("type", "albums");
    	            
    	            String cover = covers.get(i);

    	            //show cover
    	        	ImageView coverImageView = new ImageView(this);
    	        	URL url = null;
    	            if ((!(cover.equals("N/A ")))&&(!(cover.equals("N/A")))) {
    	            	try {
    	            		url = new URL(cover);
    	                	Bitmap coverImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
    	                	coverImageView.setImageBitmap(coverImage);	
    	    	            bundle.putString("cover"+i, cover);
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    				}
    	            else{
    	            	try {
    						url = new URL("http://images.hemmings.com/search_ad/ni.jpg");
    			        	Bitmap coverImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
    			        	coverImageView.setImageBitmap(coverImage);	
    	    	            bundle.putString("cover"+i, "http://images.hemmings.com/search_ad/ni.jpg");
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    	            }

    	        	//out & inside layout should be same
    	        	TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(150, 100);
    	        	coverImageView.setLayoutParams(layoutParams);
    	        	
    	            tbrow.addView(coverImageView);

    	            //show title
    	            TextView title= new TextView(this);
    	            title.setText(whitespace+"Title:\n"+whitespace+titles.get(i));
    	            bundle.putString("title"+i, titles.get(i));
    	            tbrow.addView(title);
    	            
    	            //show artists
    	            TextView artist= new TextView(this);
    	            artist.setText(whitespace+"Artist:\n"+whitespace+artists.get(i));
    	            bundle.putString("artist"+i, artists.get(i));
    	            tbrow.addView(artist);
    	            
    	            //show genre
    	            TextView genre= new TextView(this);
    	            genre.setText(whitespace+"Genre:\n"+whitespace+genres.get(i));
    	            bundle.putString("genre"+i, genres.get(i));
    	            tbrow.addView(genre);
    	            
    	            //show year
    	            TextView year= new TextView(this);
    	            year.setText(whitespace+"Year:\n"+whitespace+years.get(i));
    	            bundle.putString("year"+i, years.get(i));
    	            tbrow.addView(year);
    	            
    	            //detail
    	            bundle.putString("detail"+i,details.get(i));
    	            
    	            tbrow.setClickable(true);
    	            tbrow.setOnClickListener(new View.OnClickListener() {
    	            
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							int id = v.getId();
		                    Intent intent = new Intent(ResultViewActivity.this, FaceBookActivity.class);
		                    bundle.putString("id", v.getId()+"");
		                    intent.putExtras(bundle);
		                    /*
		        	        covers.clear();
		        	        titles.clear();
		        	        performers.clear();
		        	        composers.clear();
		        	        details.clear();
		        	        */
		                    startActivity(intent);
						}
					});
    	            
    	            ll.addView(tbrow);

    	        }
    	        /*
    	        covers.clear();
    	        titles.clear();
    	        artists.clear();
    	        genres.clear();
    	        years.clear();
    	        */

    		}
    		else {
                TableRow tbrow=new TableRow(this);
                TextView noResult= new TextView(this);
                noResult.setText("no result");
                tbrow.addView(noResult);
                ll.addView(tbrow);
    		}

		}
        
        else if(type.equals("songs")){
        	if (noResultFlag==0) {
    	        for(int i=0;i<jArray.length();i++) {
    	            TableRow tbrow=new TableRow(this);
    	            
    	            tbrow.setId(i);
    	            bundle.putString("type", "songs");
    	            
    	            //show cover
    	        	ImageView coverImageView = new ImageView(this);
    	        	URL url = null;
                	try {
						url = new URL("http://images.hemmings.com/search_ad/ni.jpg");
			        	Bitmap coverImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			        	coverImageView.setImageBitmap(coverImage);	
			        	bundle.putString("cover", "http://images.hemmings.com/search_ad/ni.jpg");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

    	        	//out & inside layout should be same
    	        	TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(150, 100);
    	        	coverImageView.setLayoutParams(layoutParams);
    	        	
    	            tbrow.addView(coverImageView);

    	            //show title
    	            TextView title= new TextView(this);
    	            title.setText(whitespace+"Title:\n"+whitespace+titles.get(i));
    	            bundle.putString("title"+i, titles.get(i));
    	            tbrow.addView(title);
    	            
    	            //show performer
    	            TextView performer= new TextView(this);
    	            performer.setText(whitespace+"Performer:\n"+whitespace+performers.get(i));
    	            bundle.putString("performer"+i, performers.get(i));
    	            tbrow.addView(performer);
    	            
    	            //show composer
    	            TextView composer= new TextView(this);
    	            composer.setText(whitespace+"Composer:\n"+whitespace+composers.get(i));
    	            bundle.putString("composer"+i, composers.get(i));
    	            tbrow.addView(composer);
    	            
    	            //detail
    	            bundle.putString("detail"+i,details.get(i));
    	            
    	            tbrow.setClickable(true);
    	            tbrow.setOnClickListener(new View.OnClickListener() {
    	            
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							int id = v.getId();
		                    Intent intent = new Intent(ResultViewActivity.this, FaceBookActivity.class);
		                    bundle.putString("id", v.getId()+"");
		                    intent.putExtras(bundle);
		                    /*
		        	        covers.clear();
		        	        titles.clear();
		        	        performers.clear();
		        	        composers.clear();
		        	        details.clear();
		        	        */
		                    startActivity(intent);
						}
					});
    	            ll.addView(tbrow);

    	        }


    		}
    		else {
                TableRow tbrow=new TableRow(this);
                TextView noResult= new TextView(this);
                noResult.setText("no result");
                tbrow.addView(noResult);
                ll.addView(tbrow);
    		}
        }
        hsv.addView(ll);
        sv.addView(hsv);
        setContentView(sv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_view, menu);
		return true;
	}

}
