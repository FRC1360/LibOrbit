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

#include "stdafx.h"
#include "joystick.h"
#include <vector>
#include <algorithm>
#include <iostream>

static jclass javaClass;
static jfieldID idField;
static std::vector<int> map({ 0, 1, 2, 3 });
static joystick joysticks[4] = { {0}, {1}, {2}, {3} };

static jint getId(JNIEnv *env, jobject object)
{
  return map[env->GetIntField(object, idField)];
}

extern "C" {

  JNIEXPORT jstring JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_getName(JNIEnv *env, jobject self)
  {
    return env->NewStringUTF(joysticks[getId(env, self)].getName());
  }

  JNIEXPORT jdouble JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_getAxis(JNIEnv *env, jobject self, jint i)
  {
    return joysticks[getId(env, self)].getAxis(i);
  }

  JNIEXPORT jboolean JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_getButton(JNIEnv *env, jobject self, jint i)
  {
    return joysticks[getId(env, self)].getButton(i);
  }

  JNIEXPORT jint JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_getPov(JNIEnv *env, jobject self, jint i)
  {
    return joysticks[getId(env, self)].getPov(i);
  }

  JNIEXPORT void JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_setOutput(JNIEnv *env, jobject self, jint i, jboolean value)
  {
    joysticks[getId(env, self)].setOutput(i, value);
  }

  JNIEXPORT void JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_setRumble(JNIEnv *env, jobject self, jint i, jdouble value)
  {
    joysticks[getId(env, self)].setRumble(i, value);
  }

  JNIEXPORT jbooleanArray JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_refresh(JNIEnv *env, jclass joystickClass)
  {
    jbooleanArray array = env->NewBooleanArray(4);
    jboolean connected[4];
    for (int i = 0; i < 4; ++i)
      connected[i] = joysticks[map[i]].isConnected();
    env->SetBooleanArrayRegion(array, 0, 4, connected);
    return array;
  }

  JNIEXPORT void JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_reorder(JNIEnv *env, jclass joystickClass, jint from, jint to)
  {
    if (from > to)
      std::rotate(map.rbegin() + (map.size() - from - 1), map.rbegin() + (map.size() - from - 1), map.rbegin() + (map.size() - to - 1));
    else
      std::rotate(map.begin() + from, map.begin() + from, map.begin() + to);
  }

  JNIEXPORT void JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_setup(JNIEnv *env, jobject self)
  {
  }

  JNIEXPORT void JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_initialize(JNIEnv *env, jclass joystickClass)
  {
    javaClass = joystickClass;
    idField = env->GetFieldID(joystickClass, "id", "I");
  }
}
