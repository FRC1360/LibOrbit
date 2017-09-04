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
