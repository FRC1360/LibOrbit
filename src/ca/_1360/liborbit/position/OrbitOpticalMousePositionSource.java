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
* Nicholas Mertin (2017-10-10) - Implemented acceptInputEvent and pipeline endpoints, and added scale endpoint
*/

package ca._1360.liborbit.position;

import java.time.Instant;
import java.util.OptionalDouble;

import ca._1360.liborbit.pipeline.OrbitPipelineInputEndpoint;
import ca._1360.liborbit.pipeline.OrbitPipelineOutputEndpoint;
import ca._1360.liborbit.pipeline.filters.OrbitIdentityFilter;
import ca._1360.liborbit.util.jni.OrbitInputEventsReader;
import ca._1360.liborbit.util.jni.OrbitInputEventsReader.EventHandler;

public final class OrbitOpticalMousePositionSource implements OrbitPositionSource, EventHandler {
	private OrbitIdentityFilter orientation = new OrbitIdentityFilter();
	private OrbitIdentityFilter scale = new OrbitIdentityFilter();
	private volatile double x;
	private volatile double y;
	private OrbitPipelineOutputEndpoint xEndpoint = () -> OptionalDouble.of(x);
	private OrbitPipelineOutputEndpoint yEndpoint = () -> OptionalDouble.of(y);
	
	public OrbitOpticalMousePositionSource() {
		OrbitInputEventsReader.addHandler("/dev/input/mice", this);
	}

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.position.OrbitPositionSource#getX()
	 */
	@Override
	public OrbitPipelineOutputEndpoint getX() {
		return xEndpoint;
	}

	/* (non-Javadoc)
	 * @see ca._1360.liborbit.position.OrbitPositionSource#getY()
	 */
	@Override
	public OrbitPipelineOutputEndpoint getY() {
		return yEndpoint;
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
	
	public OrbitPipelineInputEndpoint getScaleInput() {
		return scale;
	}

	@Override
	public void acceptInputEvent(Instant time, short type, short code, int value) {
		if (type == OrbitInputEventsReader.EV_REL) {
			if (code == OrbitInputEventsReader.REL_X) {
				x += scale.get().orElse(1.0) * Math.cos(orientation.get().orElse(0.0)) * value;
			} else if (code == OrbitInputEventsReader.REL_Y) {
				y += scale.get().orElse(1.0) * Math.sin(orientation.get().orElse(0.0)) * value;
			}
		}
	}
}
