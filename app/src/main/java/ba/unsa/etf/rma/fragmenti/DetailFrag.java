package ba.unsa.etf.rma.fragmenti;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import ba.unsa.etf.rma.R;
import ba.unsa.etf.rma.aktivnosti.KvizoviAkt;
import ba.unsa.etf.rma.adapteri.GridViewAdapter;
import ba.unsa.etf.rma.klase.Kategorija;
import ba.unsa.etf.rma.klase.Kviz;


public class DetailFrag extends Fragment {

    private ArrayList<Kviz> kvizovi = new ArrayList<>();
    private GridView gridKvizovi;
    private GridViewAdapter kvizAdapter = null;
    private View view;


    public DetailFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) return null;
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        gridKvizovi = (GridView) view.findViewById(R.id.gridKvizovi);
        kvizovi = new ArrayList<>();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (view == null) return;

        dodajListenerNaGrid();
    }

    private void dodajListenerNaGrid() {
        gridKvizovi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Kviz odabraniKviz = (Kviz) parent.getItemAtPosition(position);

                if (odabraniKviz.getNaziv().equals("Dodaj kviz")) {
                    if (!KvizoviAkt.isOnline)
                        ((KvizoviAkt) getActivity()).izbaciAlert("Spojite se na internet da dodate kviz");
                    else
                        ((KvizoviAkt) getActivity()).otvoriAktivnostZaDodavanjeKviza(null);
                }
                else {
                    String event = ((KvizoviAkt) getActivity()).postojiDogadjaj(odabraniKviz.getPitanja().size() * 30000);
                    if (event == null)
                        ((KvizoviAkt) getActivity()).otvoriAktivnostZaIgranjeKviza(odabraniKviz);
                    else
                        ((KvizoviAkt) getActivity()).izbaciAlert(event);
                }
            }
        });

        gridKvizovi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Kviz odabraniKviz = (Kviz) parent.getItemAtPosition(position);
                if (odabraniKviz.getNaziv().equals("Dodaj kviz"))
                    if (!KvizoviAkt.isOnline)
                        ((KvizoviAkt) getActivity()).izbaciAlert("Spojite se na internet da dodate kviz");
                    else
                        ((KvizoviAkt) getActivity()).otvoriAktivnostZaDodavanjeKviza(null);
                else {
                    if (!KvizoviAkt.isOnline)
                        ((KvizoviAkt) getActivity()).izbaciAlert("Spojite se na internet da uredite kviz");
                    else
                        ((KvizoviAkt) getActivity()).otvoriAktivnostZaDodavanjeKviza(odabraniKviz);
                }
                return true;
            }
        });
    }

    public void primiPorukuOdListeFrag(ArrayList<Kviz> noviKvizovi) {
        azurirajKvizove(noviKvizovi);
    }

    public void azurirajKvizove(ArrayList<Kviz> noviKvizovi) {
        int i = 0;

        for (; i<noviKvizovi.size(); i++) {
            if (noviKvizovi.get(i).getNaziv().equals("Dodaj kviz")) {
                if (i == noviKvizovi.size()-1) break;
                noviKvizovi.remove(i);
                i = noviKvizovi.size();
                break;
            }
        }

        if (i == noviKvizovi.size())
            noviKvizovi.add(new Kviz("Dodaj kviz", null, new Kategorija("-10", "-10")));

        kvizovi = noviKvizovi;
        kvizAdapter = new GridViewAdapter(view.getContext(), kvizovi);
        gridKvizovi.setAdapter(kvizAdapter);
    }
}
