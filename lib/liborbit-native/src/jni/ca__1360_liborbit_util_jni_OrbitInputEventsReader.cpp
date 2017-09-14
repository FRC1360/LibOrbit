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
* Nicholas Mertin (2017-08-29) - Created file
* Nicholas Mertin (2017-09-11) - Implemented functionality
*/

#include "ca__1360_liborbit_util_jni_OrbitInputEventsReader.h"
#include <cstdint>
#include <sys/types.h>
#include "../main/input_reader.h"

JNIEXPORT void JNICALL Java_ca__11360_liborbit_util_jni_OrbitInputEventsReader_run (JNIEnv *env, jobject _this) {
    jclass clazz = env->GetObjectClass(_this);
    jfieldID field = env->GetFieldID(clazz, "path", "Ljava/lang/String;");
    jstring _path = (jstring)env->GetObjectField(_this, field);
    const char *path = env->GetStringUTFChars(_path, NULL);
    jmethodID handleInternal = env->GetMethodID(clazz, "handleInternal", "(JSSI)V");
    jclass ex = env->FindClass("ca/_1360/liborbit/util/jni/OrbitJniException");
    try {
        liborbit::input_reader reader(path, [&] (input_event event) {
            env->CallVoidMethod(_this, handleInternal, (int64_t) event.time.tv_sec * 1000000 + event.time.tv_usec, (int16_t) event.type, (int16_t) event.code, (int32_t) event.value);
        }, [&] (std::string error) {
            env->ThrowNew(ex, error.c_str());
        });
        field = env->GetFieldID(clazz, "work", "Z");
        reader.run_loop(std::bind(&JNIEnv::GetBooleanField, *env, _this, field));
    } catch (const std::string &error) {
        env->ThrowNew(ex, error.c_str());
    } catch (...) {
        env->ThrowNew(ex, "Unknown error occured");
    }
}
