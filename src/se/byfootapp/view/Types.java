package se.byfootapp.view;

import se.byfootapp.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Types extends RelativeLayout{
    
    private int height;
    private int width;
    public Button atm;
    public Button airport;
    public Button cafe;
    public Button food;
    public Button gym;
    public Button hospital;
    public Button liquor;
    public Button movies;
    public Button club;
    public Button mall;
    public Button taxi;
    public Button zoo;
    
    public Types(Context context) {
        super(context);
        init();
    }

    public Types(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    private void init(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        inflater.inflate(R.layout.layout_types, this);
    }
    
    private void loadViews(){
        Button atm = (Button)findViewById(R.id.button_atm);
        setButtonSize(atm);
        Button airport = (Button)findViewById(R.id.button_airport);
        setButtonSize(airport);
        Button cafe = (Button)findViewById(R.id.button_cafe);
        setButtonSize(cafe);
        Button food = (Button)findViewById(R.id.button_food);
        setButtonSize(food);
        Button gym = (Button)findViewById(R.id.button_gym);
        setButtonSize(gym);
        Button hospital = (Button)findViewById(R.id.button_hospital);
        setButtonSize(hospital);
        Button liquor = (Button)findViewById(R.id.button_liquor_store);
        setButtonSize(liquor);
        Button movies = (Button)findViewById(R.id.button_movie_theater);
        setButtonSize(movies);
        Button club = (Button)findViewById(R.id.button_night_club);
        setButtonSize(club);
        Button mall = (Button)findViewById(R.id.button_shopping_mall);
        setButtonSize(mall);
        Button taxi = (Button)findViewById(R.id.button_taxi_stand);
        setButtonSize(taxi);
        Button zoo = (Button)findViewById(R.id.button_zoo);
        setButtonSize(zoo);
    }
    
    private void setButtonSize(Button button){
        button.setHeight(height/4);
        button.setWidth(width/3);
    }
    
    @Override
    public void onSizeChanged(int newW, int newH, int oldW, int oldH){
        super.onSizeChanged(newW, newH, oldW, oldH);
        width = newW;
        height = newH;
        loadViews();
    }

}
