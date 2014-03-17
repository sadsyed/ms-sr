package ss.ms.pocketsphnixsr;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Config;
import edu.cmu.pocketsphinx.Decoder;
import edu.cmu.pocketsphinx.FsgModel;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.Jsgf;
import edu.cmu.pocketsphinx.JsgfRule;
import edu.cmu.pocketsphinx.NGramModel;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SphinxUtil;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;


public class MainActivity extends Activity {
	protected static String KWS_SRCH_NAME = "wakeup_search";
	protected static String KEYPHRASE = "oh computer";
	
	static {
	    System.loadLibrary("pocketsphinx_jni");
	}
	
	public String joinPath(File dir, String path) {
	    return new File(dir, path).getPath();
	}
	
	private ActionBar tabBar;
	private SpeechRecognizer recognizer;
	
	@Override
	public void onCreate(Bundle state) {
	    super.onCreate(state);

	    File appDir = getAppDir();;
	    try {
	        appDir = SphinxUtil.syncAssets(getApplicationContext());
	    	//appDir = SphinxUtil.copyAssets(getApplicationContext(), "models");
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    
	    Config config = Decoder.defaultConfig();
	    //config.setString("-dict", joinPath(appDir, "models/lm/cmu07a.dic"));
	    config.setString("-dict", joinPath(appDir, "models/lm/hub4.5000.dic"));
	    //config.setString("-hmm", joinPath(appDir, "models/hmm/en-us-semi"));
	    config.setString("-hmm", joinPath(appDir, "models/hmm/en_US"));
	    config.setString("-rawlogdir", appDir.getPath());
	    config.setInt("-maxhmmpf", 10000);
	    config.setBoolean("-fwdflat", false);
	    config.setBoolean("-bestpath", false);
	    config.setFloat("-kws_threshold", 1e-5);
	    recognizer = new SpeechRecognizer(config);
	    
	    recognizer.setKws(KWS_SRCH_NAME, KEYPHRASE);
	    
	    /*Jsgf jsgf = new Jsgf(joinPath(appDir, "models/dialog.gram"));
	    JsgfRule rule = jsgf.getRule("<dialog.command>");
	    int lw = config.getInt("-lw");
	    FsgModel fsg = jsgf.buildFsg(rule, recognizer.getLogmath(), lw);
	    recognizer.setFsg(MainFragment.class.getSimpleName(), fsg);
	    recognizer.setSearch(MainFragment.class.getSimpleName());
	    NGramModel lm = new NGramModel(joinPath(appDir, "models/lm/weather.dmp"));
	    recognizer.setLm(MainFragment.class.getSimpleName(), lm);*/
	    
	    NGramModel lm = new NGramModel(joinPath(appDir, "models/lm/hub4.5000.dmp"));
	    intializeRecognizer(config, lm, null);
	    
	    tabBar = getActionBar();
	    tabBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	
	    Tab t = tabBar.newTab();
	    t.setText("English");
	    t.setTabListener(newTabListener(MainFragment.class, state));
	    tabBar.addTab(t);
	
	    t = tabBar.newTab();
	    t.setText("Arabic");
	    t.setTabListener(newTabListener(ArabicFragment.class, state));
	    tabBar.addTab(t);
	
	    if (null != state)
	        tabBar.setSelectedNavigationItem(state.getInt("tab", 0));
	}
	
	public SpeechRecognizer getRecognizer() {
	    return recognizer;
	}
	    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    outState.putInt("tab", tabBar.getSelectedNavigationIndex());
	    super.onSaveInstanceState(outState);
	}
	
	<T extends Fragment> TabListener newTabListener(Class<T> c, Bundle state) {
	    return new TabFragmentListener<T>(this, c.getSimpleName(), c, state);
	}
	
	public File getAppDir(){
		File appDir;
	    try {
	        appDir = SphinxUtil.syncAssets(getApplicationContext());
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    
	    Log.d("SR-APPDIR-Value", appDir.toString());
	    return appDir;
	}
	
	public void intializeRecognizer(Config config, NGramModel lm, FsgModel fsg) {
	    recognizer = new SpeechRecognizer(config);
	    
	    recognizer.setKws(KWS_SRCH_NAME, KEYPHRASE);
	    
	    if(fsg!=null){
	    	recognizer.setFsg(MainFragment.class.getSimpleName(), fsg);
	    }
	    
	    recognizer.setSearch(MainFragment.class.getSimpleName());
	    recognizer.setLm(MainFragment.class.getSimpleName(), lm);
	}
}
