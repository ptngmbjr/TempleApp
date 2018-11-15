package com.example.android.bluetoothlegatt;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoFragment extends Fragment {

    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.musiclayout, container, false);
        String[] values = new String[]{"Sai baba"};
        // use your custom layout

        listview = (ListView) view.findViewById(R.id.musiclist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.videolist, R.id.audioname, values);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String item = (String) listview.getAdapter().getItem(position);
                playVideo(item);
            }
        });

        return view;
    }

    public String getFileName(String name) {

        String path = "android.resource://" + getActivity().getPackageName() + "/";

        String id = path;// + R.raw.god_video;
//
//        switch (name) {
//            case "Sai baba":
//                id = path + R.raw.god_video;
//                break;
//            default:
//
//                break;
//        }

        return id;
    }

    public void playVideo(String filename) {
        final Intent intent = new Intent(getActivity(), Player.class);
        intent.putExtra(Player.EXTRAS_MEDIA_FILE_NAME, getFileName(filename));
        intent.putExtra(Player.EXTRAS_MEDIA_FILE_TYPE, Player.VIDEO);
        startActivity(intent);
    }

}
