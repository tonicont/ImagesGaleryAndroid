package com.tonicont.feedbackandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

@SuppressLint("FloatMath")
public class ImageActivity extends Activity implements OnTouchListener{
	private static final String TAG = "Touch";
	
	// matrices usadas para mover y hacer zoom sobre la imagen
    Matrix matrix = new Matrix(); 
    Matrix savedMatrix = new Matrix();
    
 // nos podemos encontrar en uno de estos 3 estados
    static final int NONE = 0; 
    static final int DRAG = 1; 
    static final int ZOOM = 2; 
    int mode = NONE;
    
 // recuerda algunos aspectos para el proceso de zoom
    PointF start = new PointF();
    PointF mid = new PointF(); 
    float oldDist = 1f;

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_image);
	        Bundle extras = getIntent().getExtras(); //obtenemos los datos pasados a través del intent
	        ImageView imagen = (ImageView)findViewById(R.id.imageView1);
	        imagen.setImageResource(extras.getInt("imagenId"));
	        imagen.setOnTouchListener(this);
	 }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;
		
		// volcamos el evento tactil en el registro
		  //dumpEvent(event);
		  view.setImageMatrix(matrix);
		// TODO Auto-generated method stub
		  
		// Manejo de eventos tactiles...
		  switch (event.getAction() & MotionEvent.ACTION_MASK) { 
		  	case MotionEvent.ACTION_DOWN:
		  		savedMatrix.set(matrix); start.set(event.getX(), event.getY()); Log.d(TAG, "mode=DRAG");
		  		mode = DRAG;
		  		break;
		  	case MotionEvent.ACTION_UP:
		  	case MotionEvent.ACTION_POINTER_UP:
		  		mode = NONE;
		  		Log.d(TAG, "mode=NONE"); 
		  		break;
		  case MotionEvent.ACTION_MOVE: 
			  if (mode == DRAG) {
				// ...
				  matrix.set(savedMatrix);
				  matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
		      }else if(mode == ZOOM){
		    	  float newDist = spacing(event); 
		    	  Log.d(TAG, "newDist=" + newDist); 
		    	  if (newDist > 10f) {
		    		  matrix.set(savedMatrix);
		    		  float scale = newDist / oldDist; 
		    		  matrix.postScale(scale, scale, mid.x, mid.y);
		    	  }
		      }
			  break;
		  case MotionEvent.ACTION_POINTER_DOWN: 
			  oldDist = spacing(event); 
			  Log.d(TAG, "oldDist=" + oldDist); 
			  if (oldDist > 10f) {
				  savedMatrix.set(matrix); 
				  midPoint(mid, event); mode = ZOOM;
				  Log.d(TAG, "mode=ZOOM");
			  } 
			  break;
		  } 
		  
		return true;
	}
	
	/** determinamos la distancia entre los dedos */
	private float spacing(MotionEvent event) { 
		float x = event.getX(0) - event.getX(1); 
		float y = event.getY(0) - event.getY(1); 
		return FloatMath.sqrt(x * x + y * y);
	}
	
	/** Calculamos el punto medio entre los dedos */
	private void midPoint(PointF point, MotionEvent event) { 
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1); 
		point.set(x / 2, y / 2);
	}
}
