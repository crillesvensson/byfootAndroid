package se.byfootapp.view;

import se.byfootapp.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class ListFooter extends RelativeLayout{
    
    public ListFooter(Context context){
        super(context);
        init();
    }
    
    public ListFooter(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }
    
    private void init(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        inflater.inflate(R.layout.layout_list_footer, this);
    }

}
