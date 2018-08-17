package com.example.android.bluetoothlegatt;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MusicFragment extends Fragment {

    MediaPlayer mp;
    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.musiclayout, container, false);
        String[] values = new String[]{"Achyutam", "Jamuna", "Kaanha",
                "Mahara", "Nikunj"};
        // use your custom layout

        listview = (ListView) view.findViewById(R.id.musiclist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.audiolist, R.id.audioname, values);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String item = (String) listview.getAdapter().getItem(position);
                playAudio(item);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mp != null)
            mp.stop();
    }

    public int getIntFileName(String name) {
        int id = R.raw.god_audio;

        switch (name) {
            case "Achyutam":
                id = R.raw.achyutam;
                break;
            case "Jamuna":
                id = R.raw.jamuna;
                break;
            case "Kaanha":
                id = R.raw.kaanha;
                break;
            case "Mahara":
                id = R.raw.mahara;
                break;
            case "Nikunj":
                id = R.raw.nikunj;
                break;
            default:

                break;
        }

        return id;
    }

    public void playAudio(String name) {

        try {
            mp = MediaPlayer.create(getActivity(), getIntFileName(name));
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
