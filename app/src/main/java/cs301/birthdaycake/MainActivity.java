package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView cakeView = findViewById(R.id.cakeview);

        CakeController controller = new CakeController(cakeView);


        Button blowoutButton = findViewById(R.id.button2);
        blowoutButton.setOnClickListener(controller);

        Switch candles = findViewById(R.id.switch2);
        candles.setOnCheckedChangeListener(controller);

        SeekBar numCandles = findViewById(R.id.seekBar);
        numCandles.setOnSeekBarChangeListener(controller);
    }

    public void goodbye(View button) {
        Log.i("button", "Goodbye");
        finishAffinity();
    }
}
