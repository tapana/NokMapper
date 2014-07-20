package org.droidplanner.core.mission.commands;

import java.util.List;

import org.droidplanner.core.mission.Mission;
import org.droidplanner.core.mission.MissionItem;
import org.droidplanner.core.mission.MissionItemType;

import com.MAVLink.Messages.ardupilotmega.msg_mission_item;
import com.MAVLink.Messages.enums.MAV_CMD;

public class CamTriggDist extends MissionCMD{
	
	private double triggerDist = 30.0;

	public CamTriggDist(MissionItem mission) {
		super(mission); 
	}
	
	public CamTriggDist(msg_mission_item msg, Mission mission) {
		super(mission);
		unpackMAVMessage(msg);
	}
	
	public void setTriggDist(double dist){
		triggerDist = dist;		
	}
	public double getTriggDist(){
		return triggerDist;	
	}

	@Override
	public void unpackMAVMessage(msg_mission_item mavMsg) {	
		triggerDist = mavMsg.param1;		
	}

	@Override
	public MissionItemType getType() {				
		return MissionItemType.CAM_TRIGG_DIST;
	}
	
	@Override
	public List<msg_mission_item> packMissionItem() {
		List<msg_mission_item> list = super.packMissionItem();
		msg_mission_item mavMsg = list.get(0);
		mavMsg.command = MAV_CMD.MAV_CMD_DO_SET_CAM_TRIGG_DIST;
		mavMsg.param1 = (float) triggerDist;		
		return list;
	}
	
}
