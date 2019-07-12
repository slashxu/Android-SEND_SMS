package com.soliwork.send_sms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private EditText telefone;
    private EditText mensagem;
    private Button btnEnviar;

    String phoneNo = null;
    String message = "";

    private SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telefone = findViewById(R.id.editTelefone);
        mensagem = findViewById(R.id.editMensagem);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicou", "clicou no botão");

                requestSmsPermission();
            }
        });

    }

    private void requestSmsPermission() {

        // check permission is given
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
            Log.i("Passou", "Passou no 1");
        } else {
            // permission already granted run sms send
            enviarSms();
            Log.i("Passou", "Passou no 2");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                Log.i("Passou", "Passou no 3");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    enviarSms();
                    Log.i("Passou", "Passou no 4");
                } else {
                    // permission denied
                    Log.i("Passou", "Passou no 5");
                    Toast.makeText(getApplicationContext(),
                            "SMS falhou, por favor tente novamente.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void enviarSms(){
        String texto =  "eStacione \nCódigo: ";
        phoneNo = telefone.getText().toString();
        message = texto + mensagem.getText().toString();

        Log.i("Passou", "Passou no sms");
        Log.e("Mensagem ", message);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS enviado.",
                Toast.LENGTH_LONG).show();

        telefone.setText(null);
        mensagem.setText(null);
    }

}
