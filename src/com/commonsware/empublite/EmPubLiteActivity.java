package com.commonsware.empublite;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EmPubLiteActivity extends Activity {

	EditText ed;
	Spinner sp;
	Button btn1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ed = (EditText)findViewById(R.id.editText1);
		sp = (Spinner)findViewById(R.id.spinner1);
		btn1= (Button)findViewById(R.id.button1);
		
		sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		btn1 .setOnClickListener(
		        new View.OnClickListener()
		        {
		            public void onClick(View view)
		            {
		                String title = ed.getText().toString();
		                String type = sp.getSelectedItem().toString().toLowerCase();
		                String uri = "http://cs-server.usc.edu:15010/examples/servlet/handlesearch?title="+title+"&type="+type;
		                uri=uri.replace(" ", "%20");

		                try {
			                HttpClient httpclient = new DefaultHttpClient();
			                HttpResponse response = httpclient.execute(new HttpGet(uri));
			                StatusLine statusLine = response.getStatusLine();
			                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			                    ByteArrayOutputStream out = new ByteArrayOutputStream();
			                    response.getEntity().writeTo(out);
			                    out.close();
			                    String responseString = out.toString();
			                    
			                    Intent intent = new Intent(EmPubLiteActivity.this, ResultViewActivity.class);
			                    Bundle b = new Bundle();
			                    b.putString("type", type);
			                    b.putString("result", responseString);
			                    intent.putExtras(b);
			                    startActivity(intent);
			                    //finish();
			                    
			                } else{
			                    //Closes the connection.
			                    response.getEntity().getContent().close();
			                    throw new IOException(statusLine.getReasonPhrase());
			                }
						} catch (Exception e) {
							// TODO: handle exception
						}

		            }
		        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.em_pub_lite, menu);
		return true;
	}

}
