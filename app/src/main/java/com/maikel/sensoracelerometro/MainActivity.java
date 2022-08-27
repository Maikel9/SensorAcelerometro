package com.maikel.sensoracelerometro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SensorManager manejador; //Nos premite acceder a los sensores  de mi dispositivo movel
    Sensor miSensor ; //Representa el sensor que se va a utilizar
    SensorEventListener detectaSensor; //nos avisa cuando el dispositivo cambie de orientacion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Iniciar nuestro manejador
        manejador = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Especificar que tipo de sensor vamos a utilizar
        miSensor = manejador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Verficar si puede utilizar el sensor
        if(miSensor == null){
            Toast.makeText(this, "NO tiene el sensor", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
        detectaSensor = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //Obtenemos el valor del eje x
                float ejex = event.values[0];

                if (ejex < -5){
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }else{
                    if(ejex > 5){
                        getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
                    }
                }

                //Eje Y
                float ejey = event.values[1];

                if (ejey < -5){
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }else{
                    if(ejey > 5){
                        getWindow().getDecorView().setBackgroundColor(Color.RED);
                    }
                }

                //Eje Z
                float ejez = event.values[2];

                if (ejez < -5){
                    getWindow().getDecorView().setBackgroundColor(Color.GRAY);
                }else{
                    if(ejez > 5){
                        getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            } //Fin onAccuracyChanged
        }; //Fin onSensorChanged
    } //Fin onCreate

    //Metodo para iniciar el sensor aceleromtro
    public void iniciar(){
        manejador.registerListener(detectaSensor, miSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Metodo para detener el sensor acelerometro
    public void detener(){
        manejador.unregisterListener(detectaSensor);
    }

    @Override
    protected void onPause() {
        detener();
        super.onPause();
    }

    @Override
    protected void onResume() {
        iniciar();
        super.onResume();
    }
}