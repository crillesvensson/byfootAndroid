package se.byfootapp.adapter;

import java.util.List;

import se.byfootapp.R;
import se.byfootapp.model.PlaceText;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TextAdapter extends ArrayAdapter<PlaceText>{
    
    private Context context;
    private final List<PlaceText> placeTexts;

    public TextAdapter(Context context, int resource, List<PlaceText> placeTexts) {
        super(context, resource, placeTexts);
        this.context = context;
        this.placeTexts = placeTexts;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_text_list_item, parent, false);
        TextView text = (TextView)rowView.findViewById(R.id.place_text);
        text.setText(placeTexts.get(position).getText());
        return rowView;
   }

}
