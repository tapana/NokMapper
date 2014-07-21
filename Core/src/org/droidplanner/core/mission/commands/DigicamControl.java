package org.droidplanner.core.mission.commands;

import java.util.List;

import org.droidplanner.core.mission.Mission;
import org.droidplanner.core.mission.MissionItem;
import org.droidplanner.core.mission.MissionItemType;

import com.MAVLink.Messages.ardupilotmega.msg_mission_item;
import com.MAVLink.Messages.enums.MAV_CMD;

public class DigicamControl extends MissionCMD {

	public DigicamControl(MissionItem mission) {
		super(mission); 
	}
	
	public DigicamControl(msg_mission_item msg, Mission mission) {
		super(mission);
		unpackMAVMessage(msg);
	}

	@Override
	public void unpackMAVMessage(msg_mission_item mavMsg) {	
				
	}

	@Override
	public MissionItemType getType() {				
		return MissionItemType.DIGICAM_CONTROL;
	}
	
	@Override
	public List<msg_mission_item> packMissionItem() {
		List<msg_mission_item> list = super.packMissionItem();
		msg_mission_item mavMsg = list.get(0);
		mavMsg.command = MAV_CMD.MAV_CMD_DO_DIGICAM_CONTROL;		
		return list;
	}

}
