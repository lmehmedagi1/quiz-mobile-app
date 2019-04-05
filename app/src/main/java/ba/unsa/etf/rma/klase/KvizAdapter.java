package ba.unsa.etf.rma.klase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconHelper;

import java.util.ArrayList;

import ba.unsa.etf.rma.R;

public class KvizAdapter extends ArrayAdapter<Kviz> {

    public KvizAdapter(Context context, ArrayList<Kviz> kvizovi) {
        super(context, R.layout.element_liste_kvizova, kvizovi);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.element_liste_kvizova, parent, false);

        final Kviz kviz = getItem(position);
        TextView tekst = (TextView) customView.findViewById(R.id.tekst);
        final ImageView ikona = (ImageView) customView.findViewById(R.id.ikona);


        tekst.setText(kviz.getNaziv());
        if (kviz.getKategorija().getNaziv().equals("Svi")) ikona.setImageResource(R.drawable.svi_icon);
        else {
            final IconHelper iconHelper = IconHelper.getInstance(getContext());
            iconHelper.addLoadCallback(new IconHelper.LoadCallback() {
                @Override
                public void onDataLoaded() {
                    // This happens on UI thread, and is guaranteed to be called.
                    ikona.setImageDrawable(iconHelper.getIcon(Integer.parseInt(kviz.getKategorija().getId())).getDrawable(getContext()));
                }
            });
        }

        return customView;
    }

    public View dajElementZaDodavanje(ViewGroup parent) {
        View element = LayoutInflater.from(getContext()).inflate(R.layout.element_liste_kvizova, parent, false);
        ((TextView) element.findViewById(R.id.tekst)).setText("Dodaj kviz");
        ((ImageView) element.findViewById(R.id.ikona)).setImageResource(R.drawable.dodaj_icon);
        return element;
    }
}
