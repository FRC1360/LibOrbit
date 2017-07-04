/*
 * Copyright 2017 Oakville Community FIRST Robotics
 * 
 * This file is part of LibOrbit.
 * 
 * LibOrbit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibOrbit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributions:
 * 
 * Nicholas Mertin (2017-07-04) - set up team project
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
