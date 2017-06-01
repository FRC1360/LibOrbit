package ca._1360.liborbit.pipeline;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OrbitPipelineSmartDashboardExportConnector implements OrbitPipelineInputEndpoint {
	private final String label;
	
	public OrbitPipelineSmartDashboardExportConnector(String label) {
		this.label = label;
	}
	
	@Override
	public void accept(double value) {
		SmartDashboard.putNumber(label, value);
	}
}
