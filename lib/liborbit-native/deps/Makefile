# Copyright 2017 Oakville Community FIRST Robotics
#
# This file is part of LibOrbit.
#
# LibOrbit is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# LibOrbit is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
#
# Contributions:
#
# Nicholas Mertin (2017-09-04) - Created file

.PHONY: all clean configure ;
.SECONDARY: configure-% ;

all: libssh2.a

clean:
	rm -f $(wildcard *.a) $(wildcard configure-*)

configure: configure-libssh2

configure-libssh2:
	git submodule init
	git submodule update
	cd ../../libssh2; mkdir -p bin; cd bin; cmake .. -DBUILD_SHARED_LIBS=OFF
	touch configure-libssh2

libssh2.a: configure-libssh2
	$(MAKE) -C ../../libssh2/bin
	rm -f libssh2.a include/libssh2
	ln -s ../../libssh2/bin/src/libssh2.a libssh2.a
	ln -s ../../../libssh2/include include/libssh2
