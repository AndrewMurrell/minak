package us.minak;

import android.inputmethodservice.InputMethodService;
import java.util.Queue;
import android.view.View;
import android.view.inputmethod.EditorInfo;


public class Minak extends InputMethodService {
	private MinakView minview;
	
	@Override
	public void onStartInput(EditorInfo attribute, boolean restart) {
		if (minview != null) {
			final Queue<Character> symbolsQueue = minview.getSymbolsQueue();
			while (!symbolsQueue.isEmpty()) {
				final Character character = symbolsQueue.poll();
				getCurrentInputConnection().commitText(String.valueOf(character), 1);
			}
		}
	}
	
	@Override
	public View onCreateInputView() {
		final MinakView mv = getLayoutInflater().inflate(R.layout.minak, null);
		
		// TODO set Listeners here
		
		minview = mv;
		return mv;
	}
}
