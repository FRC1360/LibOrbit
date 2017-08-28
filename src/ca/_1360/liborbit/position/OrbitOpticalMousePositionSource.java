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
* Nicholas Mertin (2017-08-27) - Created file
*/

package ca._1360.liborbit.position;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.pipeline.filters.OrbitIdentityFilter;

public class OrbitOpticalMousePositionSource implements OrbitPositionSource {
	private OrbitIdentityFilter orientation = new OrbitIdentityFilter();
	private double x;
	private double y;

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.position.OrbitPositionSource#getX()
	 */
	@Override
	public OrbitPipelineOutputEndpoint getX() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.position.OrbitPositionSource#getY()
	 */
	@Override
	public OrbitPipelineOutputEndpoint getY() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.position.OrbitPositionSource#getOrientation()
	 */
	@Override
	public OrbitPipelineOutputEndpoint getOrientation() {
		return orientation;
	}
	
	public OrbitPipelineInputEndpoint getOrientationInput() {
		return orientation;
	}
}
