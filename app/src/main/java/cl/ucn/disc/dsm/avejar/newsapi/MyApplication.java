package cl.ucn.disc.dsm.avejar.newsapi;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.acra.ACRA;
import org.acra.annotation.AcraMailSender;
import org.acra.annotation.AcraToast;

@AcraMailSender(mailTo="ariel.vejar@live.cl")
@AcraToast(resText = R.string.acra_crash_text ,length = Toast.LENGTH_LONG)

public final class MyApplication extends Application {

    /**
     * Message method from ACRA
     *
     * @param base - View
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ACRA.init(this);
    }

    /**
     * Fresco Initialize method
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
