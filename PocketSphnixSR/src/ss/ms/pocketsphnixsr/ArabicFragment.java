package ss.ms.pocketsphnixsr;

import java.io.File;

import ss.ms.arabic.font.ArabicUtilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.cmu.pocketsphinx.Config;
import edu.cmu.pocketsphinx.Decoder;
import edu.cmu.pocketsphinx.FsgModel;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.Jsgf;
import edu.cmu.pocketsphinx.JsgfRule;
import edu.cmu.pocketsphinx.NGramModel;
import edu.cmu.pocketsphinx.SpeechRecognizer;

public class ArabicFragment extends ShowcaseFragment {

	protected static String KWS_SRCH_NAME = "wakeup_search";
	protected static String KEYPHRASE = "oh computer";
	
    private TextView resultText;
    private ToggleButton toggleButton;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
        toggleButton = (ToggleButton) v.findViewById(R.id.start_button);
        resultText = (TextView) v.findViewById(R.id.result);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

       /*File appDir = ((MainActivity) context).getAppDir();
        
        Config config = Decoder.defaultConfig();
	    //config.setString("-dict",  ((MainActivity) context).joinPath(appDir, "models/lm/hub4.5000.dic"));
	    config.setString("-hmm",  ((MainActivity) context).joinPath(appDir, "models/hmm/ar"));
	    config.setString("-rawlogdir", appDir.getPath());
	    config.setInt("-maxhmmpf", 10000);
	    config.setBoolean("-fwdflat", false);
	    config.setBoolean("-bestpath", false);
	    config.setFloat("-kws_threshold", 1e-5);
	    //recognizer = new SpeechRecognizer(config);
	    
	    //recognizer.setKws(KWS_SRCH_NAME, KEYPHRASE);
	    Jsgf jsgf = new Jsgf (((MainActivity) context).joinPath(appDir, "models/dialog.gram"));
	    JsgfRule rule = jsgf.getRule("<dialog.command>");
	    int lw = config.getInt("-lw");
	    FsgModel fsg = jsgf.buildFsg(rule, recognizer.getLogmath(), lw);
	    //NGramModel lm = new NGramModel(joinPath(appDir, "models/lm/hub4.5000.dmp"));
	    NGramModel lm = new NGramModel(((MainActivity) context).joinPath(appDir, "models/lm/AASR.lm.DMP"));
   
        ((MainActivity) context).intializeRecognizer(config, null, fsg);/*/
        
        toggleButton.setChecked(false);
        toggleButton.setOnCheckedChangeListener(this);
        
        resultText.setText(ArabicUtilities.reshape("\u0641\u0631\u0633"));
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        super.onPartialResult(hypothesis);
        if (hypothesis.getHypstr().equals(MainActivity.KEYPHRASE))
            return;
        resultText.setText(hypothesis.getHypstr());
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis.getHypstr().equals(MainActivity.KEYPHRASE))
            return;
        resultText.setText(hypothesis.getHypstr());
    }
    
    @Override
    protected void setButtonPressed() {
        toggleButton.setChecked(true);
    }
}