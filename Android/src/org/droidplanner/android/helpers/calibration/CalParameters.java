package org.droidplanner.android.helpers.calibration;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;

public class CalParameters {
	private Drone myDrone;
	protected List<String> calParameterNames = new ArrayList<String>();
	protected List<Parameter> calParameterItems = new ArrayList<Parameter>();
	private boolean isUpdating = false;
	private OnCalibrationEvent listener;
	private int paramCount = 0;
	private int uploadIndex = 0;
    private boolean updateTrig = false;

	public interface OnCalibrationEvent {
		public void onReadCalibration();

		public void onSentCalibration();

		public void onCalibrationData(int index, int count, boolean isSending);
	}

	public CalParameters(Drone myDrone) {
		this.myDrone = myDrone;
	}

	public void setOnCalibrationEventListener(OnCalibrationEvent listener) {
		this.listener = listener;
	}

	public void processReceivedParam() {
		if (myDrone == null) {
			return;
		}

        paramCount = calParameterItems.size();
        //check if finished read parameter
        if (paramCount >= calParameterNames.size()) {
            if (this.listener != null && ! updateTrig) {
                updateTrig = true;
                this.listener.onReadCalibration();
            }
            return;
        }

        Parameter param = myDrone.parameters.getParameter(calParameterNames.get(paramCount));

        if (param != null) {
            calParameterItems.add(param);
            readCalibrationParameter(paramCount + 1);
        } else {
            //re-read if failed
            readCalibrationParameter(paramCount);
        }

	}

	private void compareCalibrationParameter(Parameter param) {
		Parameter paramRef = calParameterItems.get(uploadIndex);

		if (paramRef.name.equalsIgnoreCase(param.name) && paramRef.value == param.value) {
			uploadIndex++;
		}
		sendCalibrationParameters();
	}

	public void getCalibrationParameters(Drone drone) {
		this.myDrone = drone;
		calParameterItems.clear();
		paramCount = 0;

        //clear parameter value in cache Parameter Class
        for(int i=0;i<calParameterNames.size();i++){
            myDrone.parameters.clearCacheParameter(calParameterNames.get(i));
        }
        updateTrig = false;
        readCalibrationParameter(0);

	}

	private void readCalibrationParameter(int seq) {
		if (seq >= calParameterNames.size()) {
			if (this.listener != null)
				this.listener.onReadCalibration();
			return;
		}

		if (myDrone != null) {
            myDrone.parameters.ReadParameter(calParameterNames.get(seq));
        }
		if (this.listener != null) {
			this.listener.onCalibrationData(seq, calParameterNames.size(), isUpdating);
		}
	}

	public void sendCalibrationParameters() {

        isUpdating = true;
        for(int i=0;i<calParameterItems.size();i++){
            Parameter p = calParameterItems.get(i);

            if (myDrone != null) {
                myDrone.parameters.sendParameter(p);
            }
            if (this.listener != null) {
                this.listener.onCalibrationData(i, paramCount, isUpdating);
            }

        }
        isUpdating = false;

        if (this.listener != null) {
            this.listener.onSentCalibration();
        }

	}

	public boolean isParameterDownloaded() {
		return calParameterItems.size() == calParameterNames.size();
	}

	public double getParamValue(int paramIndex) {
		if (paramIndex >= calParameterItems.size())
			return -1;
		Parameter param = calParameterItems.get(paramIndex);
		return param.value;
	}

	public double getParamValueByName(String paramName) {
		for (Parameter param : calParameterItems) {
			if (param.name.contentEquals(paramName)) {
				return param.value;
			}
		}
		return -1;
	}

	public void setParamValue(int paramIndex, double value) {
		if (paramIndex >= calParameterItems.size())
			return;
		Parameter param = calParameterItems.get(paramIndex);
		param.value = value;
	}

	public void setParamValueByName(String paramName, double value) {
		for (Parameter param : calParameterItems) {
			if (param.name.contentEquals(paramName)) {
				param.value = value;
				return;
			}
		}
	}
}
