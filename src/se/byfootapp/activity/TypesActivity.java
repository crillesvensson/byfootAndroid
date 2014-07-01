package se.byfootapp.activity;

import se.byfootapp.view.Types;
import android.app.Activity;
import android.os.Bundle;

public class TypesActivity extends Activity{
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(new Types(this));
    }

}
