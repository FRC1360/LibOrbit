#include "stdafx.h"
#include "joystick.h"

static unsigned buttonTransform[] = { 0, (unsigned)GamepadButtons::A, (unsigned)GamepadButtons::B, (unsigned)GamepadButtons::X, (unsigned)GamepadButtons::Y,(unsigned)GamepadButtons::LeftShoulder, (unsigned)GamepadButtons::RightShoulder, (unsigned)GamepadButtons::View, (unsigned)GamepadButtons::Menu, (unsigned)GamepadButtons::LeftThumbstick, (unsigned)GamepadButtons::RightThumbstick };

joystick::joystick(size_t index, Gamepad ^controller) : index(index), controller(controller)
{
}

joystick::~joystick()
{
}

Platform::String ^joystick::getName()
{
  return controller->ToString();
}

double joystick::getAxis(int i)
{
  auto reading = controller->GetCurrentReading();
  switch (i) {
  case 0:
    return reading.LeftThumbstickX;
  case 1:
    return reading.LeftThumbstickY;
  case 2:
    return reading.LeftTrigger;
  case 3:
    return reading.RightTrigger;
  case 4:
    return reading.RightThumbstickX;
  case 5:
    return reading.LeftThumbstickY;
  }
  return 0.0;
}

bool joystick::getButton(int i)
{
  return ((unsigned)controller->GetCurrentReading().Buttons & buttonTransform[i]) == buttonTransform[i];
}

int joystick::getPov(int i)
{
  if (i != 0)
    return -1;
  GamepadButtons buttons = controller->GetCurrentReading().Buttons;
  int count = 0;
  int total = 0;
  if ((buttons & GamepadButtons::DPadUp) == GamepadButtons::DPadUp)
    ++count;
  if ((buttons & GamepadButtons::DPadRight) == GamepadButtons::DPadRight) {
    total += 90;
    ++count;
  }
  if ((buttons & GamepadButtons::DPadDown) == GamepadButtons::DPadDown) {
    total += 180;
    ++count;
  }
  if ((buttons & GamepadButtons::DPadLeft) == GamepadButtons::DPadRight) {
    total += 270;
    ++count;
  }
  return count ? total / count : -1;
}

void joystick::setOutput(int i, bool v)
{
  GamepadVibration vibration = controller->Vibration;
  switch (i) {
  case 0:
    vibration.LeftMotor = v;
    break;
  case 1:
    vibration.RightMotor = v;
    break;
  case 2:
    vibration.LeftTrigger = v;
    break;
  case 3:
    vibration.RightTrigger = v;
    break;
  }
  controller->Vibration = vibration;
}
