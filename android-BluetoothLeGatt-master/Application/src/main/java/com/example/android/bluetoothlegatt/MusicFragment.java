package com.example.android.bluetoothlegatt;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MusicFragment extends Fragment {

    MediaPlayer mp;
    ListView listview;
    RelativeLayout musicPlayer;
    TextView audioName;
    ImageView musicStartStop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.musiclayout, container, false);
        String[] values = new String[]{"Achyutam", "Jamuna", "Kaanha",
                "Mahara", "Nikunj"};
        // use your custom layout

        listview = (ListView) view.findViewById(R.id.musiclist);
        musicPlayer = (RelativeLayout) view.findViewById(R.id.musicplayer);
        audioName = (TextView) view.findViewById(R.id.audioname);
        musicStartStop = (ImageView) view.findViewById(R.id.iconstartstop);

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

        musicStartStop.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                if (mp.isPlaying())
                    onStopMusic();
                else
                    onSartMusic("");
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

    private void onSartMusic(String name) {
        musicPlayer.setVisibility(View.VISIBLE);
        if (!name.isEmpty())
            audioName.setText(name);
        musicStartStop.setImageResource(R.drawable.pause);

        mp.start();

    }

    private void onStopMusic() {
        musicStartStop.setImageResource(R.drawable.play);

        mp.pause();
    }

    public int getIntFileName(String name) {
        int id = -1;//R.raw.god_audio;
//
//        switch (name) {
//            case "Achyutam":
//                id = R.raw.achyutam;
//                break;
//            case "Jamuna":
//                id = R.raw.jamuna;
//                break;
//            case "Kaanha":
//                id = R.raw.kaanha;
//                break;
//            case "Mahara":
//                id = R.raw.mahara;
//                break;
//            case "Nikunj":
//                id = R.raw.nikunj;
//                break;
//            default:
//
//                break;
//        }

        return id;
    }

    public void playAudio(String name) {
        if (mp != null && mp.isPlaying())
            mp.stop();

        try {
            mp = MediaPlayer.create(getActivity(), getIntFileName(name));
            onSartMusic(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
