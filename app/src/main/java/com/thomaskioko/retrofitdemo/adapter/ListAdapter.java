/*
 * Copyright (c) 2015. Thomas Kioko.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.thomaskioko.retrofitdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thomaskioko.retrofitdemo.R;
import com.thomaskioko.retrofitdemo.data.Movie;

import java.util.List;

/**
 * This class provides data to the list.
 *
 * @author Kioko
 * @version Version 1.0
 */


public class ListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Movie> mMovieList;
    private Context mContext;

    /**
     * Default Constructor
     *
     * @param context      Application context
     * @param followerList Follower List objects
     */
    public ListAdapter(Context context, List<Movie> followerList) {
        this.mContext = context;
        this.mMovieList = followerList;
    }


    /**
     * This class uses A ViewHolder object stores to each of the component views inside the tag field
     * of the Layout, so you can immediately access them without the need to look them up repeatedly.
     * <p/>
     * {@see <a href="http://developer.android.com/training/improving-layouts/smooth-scrolling.html</a>}
     */
    public static class MovieViewHolder {
        ImageView thumbNail;
        TextView title;
        TextView genre;
    }


    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MovieViewHolder holder;

        if (inflater == null)
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.movie_item_layout, null);

        holder = new MovieViewHolder();
        holder.thumbNail = (ImageView) convertView.findViewById(R.id.album_artwork);
        holder.title = (TextView) convertView.findViewById(R.id.movie_title);
        holder.genre = (TextView) convertView.findViewById(R.id.movie_genre);

        Movie movie = mMovieList.get(position);
        holder.title.setText(movie.getTitle());
        //Use Glide to load the Image
        Glide.with(mContext).load(movie.getImage()).centerCrop().into(holder.thumbNail);

        // genre
        String genreStr = "";
        for (String str : movie.getGenre()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        holder.genre.setText(genreStr);

        return convertView;
    }

}
