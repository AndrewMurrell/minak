package us.minak;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.inputmethod.EditorInfo;

public class IMEService extends InputMethodService {
	private IMEView imeView;
	/**
	 * Loads the configuration.
	 */
	@Override
	public void onInitializeInterface() {
		// TODO
	}
	
	@Override
	public View onCreateInputView() {
		final IMEView view = (IMEView) getLayoutInflater().inflate(R.layout.ime, null);
		
		// TODO: there probably needs to be more stuff here
		
		this.imeView = view;
		return view;
	}
	
	/**
	 * Called to inform the input method that text input has started in an editor.
	 */
	public void onStartInput(EditorInfo info, boolean restarting) {
		// TODO: get characters from this.imeView, and pass them to getCurrentInputConnection().commitText(..., 1);
	}
}
