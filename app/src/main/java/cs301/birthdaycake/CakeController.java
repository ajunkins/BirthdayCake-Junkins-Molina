package cs301.birthdaycake;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    private CakeView viewReference;
    private CakeModel modelReference;

    public CakeController(CakeView ref){
        this.viewReference = ref;
        this.modelReference = this.viewReference.getReference();
    }

    @Override
    public void onClick(View view) {
        Log.d("button", "click");
        modelReference.isLit = false;
        viewReference.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        modelReference.hasCandles = !modelReference.hasCandles;
        viewReference.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        modelReference.numCandles = i;
        viewReference.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("Balloon drawn", "Balloon was drawn");
        modelReference.x = motionEvent.getX();
        modelReference.y = motionEvent.getY();
        viewReference.invalidate();
        return true;
    }

}
