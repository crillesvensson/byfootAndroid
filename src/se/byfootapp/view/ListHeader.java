package se.byfootapp.view;

import se.byfootapp.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class ListHeader extends RelativeLayout{
    
    private int searchBarWidth;
    private int buttonWidth;
    private int height;

    public ListHeader(Context context) {
        super(context);
        init();
    }
    
    public ListHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    private void init(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        inflater.inflate(R.layout.layout_list_header, this);
    }
    
    private void loadViews(){
        EditText searchBar = (EditText)findViewById(R.id.list_search_input);
        searchBar.setHeight(height);
        searchBar.setWidth(searchBarWidth);
        
        Button searchButton = (Button)findViewById(R.id.list_search_button);
        searchButton.setHeight(height);
        searchButton.setWidth(buttonWidth);
        
        Button typeButton = (Button)findViewById(R.id.list_type_button);
        typeButton.setHeight(height);
        typeButton.setWidth(buttonWidth);
    }
    
    @Override
    public void onSizeChanged(int newW, int newH, int oldW, int oldH){
        super.onSizeChanged(newW, newH, oldW, oldH);
        height = newH / 7;
        searchBarWidth = (int)(newW * 0.6);
        buttonWidth = (int)(newW * 0.2);
        loadViews();
    }

}
