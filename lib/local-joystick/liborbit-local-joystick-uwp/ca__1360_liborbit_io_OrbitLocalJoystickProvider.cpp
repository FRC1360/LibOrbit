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

  JNIEXPORT jint JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_refresh(JNIEnv *env, jclass joystickClass)
  {
    return 4;
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
