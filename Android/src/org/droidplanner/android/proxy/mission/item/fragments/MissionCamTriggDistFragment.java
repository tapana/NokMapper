package org.droidplanner.android.proxy.mission.item.fragments;

import org.droidplanner.R;
import org.droidplanner.android.widgets.SeekBarWithText.SeekBarWithText;
import org.droidplanner.core.mission.MissionItemType;
import org.droidplanner.core.mission.commands.CamTriggDist;
import org.droidplanner.core.mission.waypoints.Circle;

import android.os.Bundle;
import android.view.View;

public class MissionCamTriggDistFragment extends MissionDetailFragment implements SeekBarWithText.OnTextSeekBarChangedListener  {

	SeekBarWithText distanceSeekBar;
	
	@Override
	protected int getResource() {		
		//return R.layout.fragment_editor_detail_rtl;
		return R.layout.fragment_editor_detail_camtriggdist;
	}
	
	
	@Override
	public void onSeekBarChanged() {
		// TODO Auto-generated method stub		
		CamTriggDist item = (CamTriggDist) this.itemRender.getMissionItem();
		item.setTriggDist(distanceSeekBar.getValue());
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		typeSpinner.setSelection(commandAdapter.getPosition(MissionItemType.CAM_TRIGG_DIST));
		
		CamTriggDist item = (CamTriggDist) this.itemRender.getMissionItem();
		
		distanceSeekBar = (SeekBarWithText) view.findViewById(R.id.distanceView);
		distanceSeekBar.setValue(item.getTriggDist());
		distanceSeekBar.setOnChangedListener(this);
		
	}

}
