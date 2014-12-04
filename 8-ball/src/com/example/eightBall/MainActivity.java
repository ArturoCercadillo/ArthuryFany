package com.example.eightBall;

import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
 


public class MainActivity extends ActionBarActivity {

	TextView texto;
	private ImageView imagen;
	private AnimationDrawable animacion;
	private int OPACO=1;
	private int TRANSPARENTE=0;
	private int DURACION=1500;
	private MediaPlayer player;
	private AlphaAnimation animacionTexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button1);
        texto = (TextView) findViewById(R.id.textView1);
        imagen=(ImageView)findViewById(R.id.imageView1);
		animacion = (AnimationDrawable) getResources().getDrawable(R.drawable.ball_animation);
		imagen.setImageDrawable(animacion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadena=null;
                animationball();
                
                playSound();
                String [] array = getResources().getStringArray(R.array.respuestas);
                RelativeLayout vista = (RelativeLayout) findViewById(R.id.vista1);
                int x = Math.abs(new Random().nextInt(array.length-0)+0);
                texto.setTextColor(Color.WHITE);
                vista.setBackgroundColor(Color.BLACK);
                cadena=array[x];
                texto.setText(cadena);
                animateAnswer();
            }
        });
    }
private void animationball(){
	if(animacion.isRunning())
	{
		animacion.stop();
	}
	
		animacion.start();
	}
private void animateAnswer()
{
	animacionTexto=new AlphaAnimation(TRANSPARENTE, OPACO);
	animacionTexto.setDuration(DURACION);
	animacionTexto.setFillAfter(true);
	texto.setAnimation(animacionTexto);
}

private void playSound()
{
	player = new MediaPlayer().create(this, R.raw.magic_ball);
	player.setOnCompletionListener(new OnCompletionListener() {
		
		@Override
		public void onCompletion(MediaPlayer mp) {
			mp.release();
		}
	});
	player.start();
}

   
    
}
