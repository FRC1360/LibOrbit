#pragma once

class joystick
{
private:
  size_t index;
  XINPUT_VIBRATION vibration;
public:
  joystick(size_t);
  ~joystick();
  bool isConnected();
  const char *getName();
  double getAxis(int i);
  bool getButton(int i);
  int getPov(int i);
  void setOutput(int i, bool v);
};
