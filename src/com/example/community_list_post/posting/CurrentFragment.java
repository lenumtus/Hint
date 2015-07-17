/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.community_list_post.posting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.community_list_post.R;

/**
 *
 * @author root
 */
public class CurrentFragment extends Fragment
{
    //Create all the private and public data members
//    private MapView currentMapView;
//    private GoogleMap map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.current_location, container,false);
        
        return(rootView);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Create the spiiner drop down menu
        Spinner dropdown = (Spinner)getView().findViewById(R.id.spinner_current);
        String[] items = new String[]{"General", "Events", "Crime","Missing","Market","Jobs","Specials"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        
        EditText postCurrent = (EditText)getView().findViewById(R.id.post_current);
        
        Button current_button = (Button)getView().findViewById(R.id.post_current_button);
        //Create the mapview        
    } 
}
