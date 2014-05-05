package us.minak;

import android.inputmethodservice.InputMethodService;
import java.util.Queue;
import android.view.View;
import android.view.inputmethod.EditorInfo;


public class Minak extends InputMethodService {
	private MinakView m_minak_view;

	@Override
	public View onCreateInputView() {
		final MinakView minak_view = (MinakView) getLayoutInflater().inflate(R.layout.minak, null);

		minak_view.setOnCharacterEnteredListener(new OnCharacterEnteredListener() {
			@Override
			public void characterEntered(String character) {
				getCurrentInputConnection().commitText(character, 1);
			}
		});


		m_minak_view = minak_view;
		return minak_view;
	}

	@Override
	public void onStartInput(EditorInfo attribute, boolean restarting) {
		if (m_minak_view != null) {
			final Queue<Character> symbolsQueue = m_minak_view.getSymbolsQueue();
			while (!symbolsQueue.isEmpty()) {
				final Character character = symbolsQueue.poll();
				getCurrentInputConnection().commitText(String.valueOf(character), 1);
			}
		}
	}
}
