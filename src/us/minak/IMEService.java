package us.minak;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.inputmethod.EditorInfo;

public class IMEService extends InputMethodService {
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

		view.setOutput(new StringReciever() {
			@Override
			public void putString(String character) {
				getCurrentInputConnection().commitText(character, 1);
			}
		});

		return view;
	}

	/**
	 * Called to inform the input method that text input has started in an editor.
	 */
	public void onStartInput(EditorInfo info, boolean restarting) {
		// TODO: I don't even know
	}
}
