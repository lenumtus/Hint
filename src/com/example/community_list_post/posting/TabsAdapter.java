/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.community_list_post.posting;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 *
 * @author root
 */
public class TabsAdapter extends FragmentPagerAdapter
{
    //Create a constructor that will call the constructor of FragmentManager
    public TabsAdapter(FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public Fragment getItem(int index)
    {
        switch(index)
        {
            case 0:
                return(new HomeFragment());
            case 1:
                return(new CurrentFragment());
        }
        return(null);
    }
    @Override
    public int getCount()
    {
        return(2);
    }
}
