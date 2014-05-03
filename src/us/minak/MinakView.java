package us.minak;

import android.content.Context;
import android.widget.RelativeLayout;
import android.util.AttributeSet;
import android.support.v4.content.LocalBroadcastManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import java.util.*;

public class MinakView extends RelativeLayout {
	private final Context mContext;
	private final Queue<Character> minakSymbolsQueue = new LinkedList<Character>();
	
	public MinakView(Context context, AttributeSet attr) {
		super(context, attr);
		mContext = context;
		LocalBroadcastManager.getInstance(mContext).registerReceiver(minakBroadcastReceiver,
				new IntentFilter(SketchpadActivity.INTENT_ACTION));
	}

	public Queue<Character> getSymbolsQueue() {
		return minakSymbolsQueue;
	}
	
	private final BroadcastReceiver minakBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (SketchpadActivity.INTENT_ACTION.equals(intent.getAction())) {
				minakSymbolsQueue.add(intent.getCharExtra(SketchpadActivity.INTENT_EXTRA_NAME, '?'));
			}
		}
	};

}