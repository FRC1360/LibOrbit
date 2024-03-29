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

static WORD buttonTransform[] = { 0, XINPUT_GAMEPAD_A, XINPUT_GAMEPAD_B, XINPUT_GAMEPAD_X, XINPUT_GAMEPAD_Y,XINPUT_GAMEPAD_LEFT_SHOULDER, XINPUT_GAMEPAD_RIGHT_SHOULDER, XINPUT_GAMEPAD_BACK, XINPUT_GAMEPAD_START, XINPUT_GAMEPAD_LEFT_THUMB, XINPUT_GAMEPAD_RIGHT_THUMB };

static bool read(XINPUT_STATE &state, size_t index)
{
  ZeroMemory(&state, sizeof(XINPUT_STATE));
  return XInputGetState(index, &state) == ERROR_SUCCESS;
}

joystick::joystick(size_t index) : index(index)
{
  ZeroMemory(&vibration, sizeof(XINPUT_VIBRATION));
}

joystick::~joystick()
{
}

bool joystick::isConnected()
{
  XINPUT_STATE state;
  return read(state, index);
}

const char *joystick::getName()
{
  return isConnected() ? "Xbox 360 Controller" : "Disconnected";
}

double joystick::getAxis(int i)
{
  XINPUT_STATE state;
  read(state, index);
  switch (i) {
  case 0:
    return max(state.Gamepad.sThumbLX / 32767.0, -1.0);
  case 1:
    return max(state.Gamepad.sThumbLY / 32767.0, -1.0);
  case 2:
    return state.Gamepad.bLeftTrigger / 255.0;
  case 3:
    return state.Gamepad.bRightTrigger / 255.0;
  case 4:
    return max(state.Gamepad.sThumbRX / 32767.0, -1.0);
  case 5:
    return max(state.Gamepad.sThumbRY / 32767.0, -1.0);
  }
  return 0.0;
}

bool joystick::getButton(int i)
{
  XINPUT_STATE state;
  read(state, index);
  return state.Gamepad.wButtons & buttonTransform[i];
}

int joystick::getPov(int i)
{
  if (i != 0)
    return -1;
  XINPUT_STATE state;
  read(state, index);
  int count = 0;
  int total = 0;
  if (state.Gamepad.wButtons & XINPUT_GAMEPAD_DPAD_UP)
    ++count;
  if (state.Gamepad.wButtons & XINPUT_GAMEPAD_DPAD_RIGHT) {
    total += 90;
    ++count;
  }
  if (state.Gamepad.wButtons & XINPUT_GAMEPAD_DPAD_DOWN) {
    total += 180;
    ++count;
  }
  if (state.Gamepad.wButtons & XINPUT_GAMEPAD_DPAD_LEFT) {
    total += 270;
    ++count;
  }
  return count ? total / count : -1;
}

void joystick::setOutput(int i, bool v)
{
}

void joystick::setRumble(int i, jdouble v)
{
  switch (i) {
  case 0:
    vibration.wLeftMotorSpeed = (WORD)(v * 65535);
    break;
  case 1:
    vibration.wRightMotorSpeed = (WORD)(v * 65535);
    break;
  }
  XInputSetState(index, &vibration);
}
