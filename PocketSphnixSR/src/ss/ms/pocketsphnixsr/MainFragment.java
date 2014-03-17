package ss.ms.pocketsphnixsr;

import java.io.File;
import java.io.IOException;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.cmu.pocketsphinx.Config;
import edu.cmu.pocketsphinx.Decoder;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.NGramModel;
import edu.cmu.pocketsphinx.SphinxUtil;

public class MainFragment extends ShowcaseFragment {

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
 
       /* File appDir = ((MainActivity) context).getAppDir();
        if(appDir==null){
        	Log.e("SR-ERROR", "Null Pointer Exception");
        }
        else{
			Config config = Decoder.defaultConfig();
			config.setString("-dict", ((MainActivity) context).joinPath(appDir, "models/lm/hub4.5000.dic"));
			config.setString("-hmm", ((MainActivity) context).joinPath(appDir, "models/hmm/hub4wsj_sc_8k"));
			config.setString("-rawlogdir", appDir.getPath());
			config.setInt("-maxhmmpf", 10000);
			config.setBoolean("-fwdflat", false);
			config.setBoolean("-bestpath", false);
			config.setFloat("-kws_threshold", 1e-5);
			   
			
			NGramModel lm = new NGramModel(((MainActivity) context).joinPath(appDir, "models/lm/hub4.5000.DMP"));
			((MainActivity) context).intializeRecognizer(config, lm, null);
        }
	    */
        toggleButton.setChecked(false);
        toggleButton.setOnCheckedChangeListener(this);
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