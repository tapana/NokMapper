package org.droidplanner.android.proxy.mission.item.fragments;

import org.droidplanner.R;
import org.droidplanner.android.widgets.SeekBarWithText.SeekBarWithText;
import org.droidplanner.core.mission.MissionItemType;

import android.os.Bundle;
import android.view.View;

public class MissionCamTriggDistFragment extends MissionDetailFragment implements SeekBarWithText.OnTextSeekBarChangedListener  {

	@Override
	protected int getResource() {		
		//return R.layout.fragment_editor_detail_rtl;
		return R.layout.fragment_editor_detail_camtriggdist;
	}
	
	
	@Override
	public void onSeekBarChanged() {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		typeSpinner.setSelection(commandAdapter.getPosition(MissionItemType.CAM_TRIGG_DIST));
	
		//altitudeSeekBar = (SeekBarWithText) view.findViewById(R.id.altitudeView);
		//altitudeSeekBar.setValue(((ReturnToHome) item).getHeight().valueInMeters());
		//altitudeSeekBar.setOnChangedListener(this);
	}
	

}
