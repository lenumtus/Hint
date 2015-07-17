package com.example.community_list_post.listwidget.extend;

/**
 * 
 * @author Yao Changwei(yaochangwei@gmail.com)
 * 
 *        Pull To  Refresh List View Demo, Including the arrow and text change.
 */

import com.example.community_list_post.listwidget.RefreshableListView;

import com.example.community_list_post.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PullToRefreshListView extends RefreshableListView {

	public PullToRefreshListView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		addPullDownRefreshFeature(context);

		/*if you do not need the pull up to refresh, just uncomment follow line.*/
		// addPullUpRefreshFeature(context);
	}

	private void addPullDownRefreshFeature(final Context context) {

		setContentView(R.layout.pull_to_refresh);
		mListHeaderView.setBackgroundColor(0xffe0e0e0);
		setOnHeaderViewChangedListener(new OnHeaderViewChangedListener() {

			@Override
			public void onViewChanged(View v, boolean canUpdate) {
				TextView tv = (TextView) v.findViewById(R.id.refresh_text);
				ImageView img = (ImageView) v.findViewById(R.id.refresh_icon);
				Animation anim;
				if (canUpdate) {
					anim = AnimationUtils.loadAnimation(context,
							R.anim.rotate_up);
					tv.setText(R.string.refresh_release);
				} else {
					tv.setText(R.string.refresh_pull_down);
					anim = AnimationUtils.loadAnimation(context,
							R.anim.rotate_down);
				}
				img.startAnimation(anim);
			}

			@Override
			public void onViewUpdating(View v) {
				TextView tv = (TextView) v.findViewById(R.id.refresh_text);
				ImageView img = (ImageView) v.findViewById(R.id.refresh_icon);
				ProgressBar pb = (ProgressBar) v
						.findViewById(R.id.refresh_loading);
				pb.setVisibility(View.VISIBLE);
				tv.setText(R.string.loading);
				img.clearAnimation();
				img.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onViewUpdateFinish(View v) {
				TextView tv = (TextView) v.findViewById(R.id.refresh_text);
				ImageView img = (ImageView) v.findViewById(R.id.refresh_icon);
				ProgressBar pb = (ProgressBar) v
						.findViewById(R.id.refresh_loading);

				tv.setText(R.string.refresh_pull_down);
				pb.setVisibility(View.INVISIBLE);
				tv.setVisibility(View.VISIBLE);
				img.setVisibility(View.VISIBLE);
			}

		});
	}

	private void addPullUpRefreshFeature(final Context context) {
		this.setBottomContentView(R.layout.pull_to_refresh_bottom);
		mListBottomView.setBackgroundColor(0xffe0e0e0);
		this.setOnBottomViewChangedListener(new OnBottomViewChangedListener() {

			@Override
			public void onViewChanged(View v, boolean canUpdate) {
				TextView tv = (TextView) v.findViewById(R.id.refresh_text);
				ImageView img = (ImageView) v.findViewById(R.id.refresh_icon);
				Animation anim;
				if (canUpdate) {
					anim = AnimationUtils.loadAnimation(context,
							R.anim.rotate_up);
					tv.setText(R.string.refresh_release);
				} else {
					tv.setText(R.string.refresh_pull_up);
					anim = AnimationUtils.loadAnimation(context,
							R.anim.rotate_down);
				}
				img.startAnimation(anim);
			}

			@Override
			public void onViewUpdating(View v) {
				TextView tv = (TextView) v.findViewById(R.id.refresh_text);
				ImageView img = (ImageView) v.findViewById(R.id.refresh_icon);
				ProgressBar pb = (ProgressBar) v
						.findViewById(R.id.refresh_loading);
				pb.setVisibility(View.VISIBLE);
				tv.setText(R.string.loading);
				img.clearAnimation();
				img.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onViewUpdateFinish(View v) {
				TextView tv = (TextView) v.findViewById(R.id.refresh_text);
				ImageView img = (ImageView) v.findViewById(R.id.refresh_icon);
				ProgressBar pb = (ProgressBar) v
						.findViewById(R.id.refresh_loading);

				tv.setText(R.string.refresh_pull_up);
				pb.setVisibility(View.INVISIBLE);
				tv.setVisibility(View.VISIBLE);
				img.setVisibility(View.VISIBLE);
			}

		});
	}

}
