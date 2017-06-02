#include "stdafx.h"
#include "joystick.h"
#include <vector>
#include <algorithm>

static jclass javaClass;
static jfieldID idField;
static std::vector<joystick> joysticks;

static jint getId(JNIEnv *env, jobject object)
{
  return env->GetIntField(object, idField);
}

extern "C" {

  JNIEXPORT jstring JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_getName(JNIEnv *env, jobject self)
  {
    Platform::String ^name = joysticks[getId(env, self)].getName();
    return env->NewString((jchar *)name->Data(), name->Length());
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
    return joysticks.size();
  }

  JNIEXPORT void JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_reorder(JNIEnv *env, jclass joystickClass, jint from, jint to)
  {
    if (from > to)
      std::rotate(joysticks.rbegin() + (joysticks.size() - from - 1), joysticks.rbegin() + (joysticks.size() - from - 1), joysticks.rbegin() + (joysticks.size() - to - 1));
    else
      std::rotate(joysticks.begin() + from, joysticks.begin() + from, joysticks.begin() + to);
  }

  JNIEXPORT void JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_setup(JNIEnv *env, jobject self)
  {
  }

  JNIEXPORT void JNICALL Java_ca__11360_liborbit_io_OrbitLocalJoystickProvider_initialize(JNIEnv *env, jclass joystickClass)
  {
    Windows::Foundation::Initialize();
    javaClass = joystickClass;
    idField = env->GetFieldID(joystickClass, "id", "I");
    auto gamepads = Gamepad::Gamepads;
    for (int i = 0; i < gamepads->Size; ++i)
      joysticks.push_back({ (size_t)i, gamepads->GetAt(i) });
  }
}
