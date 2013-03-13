package com.example.intentservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.intentservice.MyResultReceiver.Receiver;

public class MainActivity extends Activity implements OnClickListener,Receiver {
    Button btLogin;  
    public MyResultReceiver mReceiver;

@Override
protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btLogin = (Button) findViewById(R.id.button1);
    mReceiver = new MyResultReceiver(new Handler());

    mReceiver.setReceiver(this);
    btLogin.setOnClickListener(this);
     
}

@Override
public void onClick(View arg0) {
    // TODO Auto-generated method stub
              
         if(arg0.getId()==R.id.button1){
              Intent i = new Intent(this, MyIntentService.class);
              i.putExtra("nameTag","vinoth" );
              i.putExtra("receiverTag", mReceiver);
              
              startService(i);
            
          }


}
 
@Override
public void onReceiveResult(int resultCode, Bundle resultData) {
     
             Log.d("vinoth","received result from Service="+resultData.getString("ServiceTag"));
             //Toast.makeText(MainActivity.this, ""+resultData.getString("ServiceTag"), Toast.LENGTH_LONG).show();

}
}
