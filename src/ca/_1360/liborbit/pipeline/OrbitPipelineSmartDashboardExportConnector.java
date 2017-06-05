/*
 * Name: Nicholas Mertin
 * Course: ICS4U
 * OrbitPipelineSmartDashboardExportConnector.java
 * An input endpoint that displays the value to SmartDashboard
 */

package ca._1360.liborbit.pipeline;

import ca._1360.liborbit.OrbitRealRobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class OrbitPipelineSmartDashboardExportConnector implements OrbitPipelineInputEndpoint {
	private final String label;
	
	public OrbitPipelineSmartDashboardExportConnector(String label) {
		this.label = label;
	}
	
	@Override
	public void accept(double value) {
		if (OrbitRealRobotBase.isReal())
			SmartDashboard.putNumber(label, value);
	}
}
