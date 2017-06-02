#pragma once

using namespace Windows::Gaming::Input;

class joystick
{
private:
  size_t index;
  Gamepad ^controller;
public:
  joystick(size_t, Gamepad ^);
  ~joystick();
  Platform::String ^getName();
  double getAxis(int i);
  bool getButton(int i);
  int getPov(int i);
  void setOutput(int i, bool v);
};
