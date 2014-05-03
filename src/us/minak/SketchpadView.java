import java.util.List;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.util.AttributeSet;

public class SketchpadView extends GestureOverlayView implements OnGesturePerformedListener{
	//Setting score thresh hold
	private static  final double score_threshold = 3.0;
	//loads gesture library
	private final GestureLibrary gestureLib;
	//used to capture drawn gestures
	private onGestureRecognizedListener gestureRecognizer;
	
	public SketchpadView (Context context, AttributeSet attrs){
		super(context, attrs);
		mGestureLibrary = GestureLibraries.fromRawResource(context, R.raw.gestures);
		mGestureLibrary.load();
		addOnGesturePerformedListener(this);
	}

	public void setOnGestureRecognizedListener(OnGestureRecognizedListener onGestureRecognizedListener) {
		gestureRecognizer = onGestureRecognizedListener;	
	}

	@Override
	public void onGesturePerformed(GestureOverlayView view, Gesture gesture){
		//create list of predicted characters to be entered
		List<Prediction> predictions = gestureLib.recognize(gesture);
		Prediction bestPrediction = null;
		//if we have a prediction
		if(!predictions.isEmpty()){
			//I believe that this just blidnly adds a predicted character without any checks
			//we need to see if there is a way to actual get the best prediction
			//if this lists them based on the best, then fuck me and ignore this/delete this
			bestPrediction = predictions.get(0);
		}
		//if we have a gesture and a decent prediction
		if(gestureRecognizer != null && bestPrediction != null){
			//if the prediction is good enough
			if(bestPrediction.score > score_threshold){
				//we recognize it
				gestureRecognizer.gestureRecognized(bestPrediction.name);
			}else{
				//why?
				clear(false);
			}
		}
	} 
}
