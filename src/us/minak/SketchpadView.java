import java.util.List;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.util.AttributeSet;

public class SketchpadView extends GestureOverlayView implements OnGesturePerformedListener{
	private static  final double score_threshold = 3.0;
	private final GestureLibrary gestureLib;
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
		List<Prediction> predictions = gestureLib.recognize(gesture);
		Prediction bestPrediction = null;
		if(!predictions.isEmpty()){
			bestPrediction = predictions.get(0);
		}
		if(gestureRecognizer != null && bestPrediction != null){
			if(bestPrediction.score > score_threshold){
				gestureRecognizer.gestureRecognized(bestPrediction.name);
			}else{
				clear(false);
			}
		}
	} 
}
