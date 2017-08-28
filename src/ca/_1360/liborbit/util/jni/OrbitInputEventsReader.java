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
* Nicholas Mertin (2017-08-27) - Created file
*/

package ca._1360.liborbit.util.jni;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import ca._1360.liborbit.util.function.OrbitFunctionUtilities;

public class OrbitInputEventsReader {
	/*
	 * These event types and codes were obtained from
	 * include/linux/input-event-codes.h in the Linux source code.
	 * They were translated from C preprocessor macros to equivalent
	 * Java constants.
	 */
	
	// BEGIN TRANSLATED LINUX SOURCE 
	
	public static short EV_SYN = 0x00;
	public static short EV_KEY = 0x01;
	public static short EV_REL = 0x02;
	public static short EV_ABS = 0x03;
	public static short EV_MSC = 0x04;
	public static short EV_SW = 0x05;
	public static short EV_LED = 0x11;
	public static short EV_SND = 0x12;
	public static short EV_REP = 0x14;
	public static short EV_FF = 0x15;
	public static short EV_PWR = 0x16;
	public static short EV_FF_STATUS = 0x17;
	public static short EV_MAX = 0x1f;
	public static short EV_CNT = (short) (EV_MAX + 1);

    public static short SYN_REPORT = 0;
    public static short SYN_CONFIG = 1;
    public static short SYN_MT_REPORT = 2;
    public static short SYN_DROPPED = 3;
    public static short SYN_MAX = 0xf;
    public static short SYN_CNT = (short) (SYN_MAX + 1);

    public static short KEY_RESERVED = 0;
    public static short KEY_ESC = 1;
    public static short KEY_1 = 2;
    public static short KEY_2 = 3;
    public static short KEY_3 = 4;
    public static short KEY_4 = 5;
    public static short KEY_5 = 6;
    public static short KEY_6 = 7;
    public static short KEY_7 = 8;
    public static short KEY_8 = 9;
    public static short KEY_9 = 10;
    public static short KEY_0 = 11;
    public static short KEY_MINUS = 12;
    public static short KEY_EQUAL = 13;
    public static short KEY_BACKSPACE = 14;
    public static short KEY_TAB = 15;
    public static short KEY_Q = 16;
    public static short KEY_W = 17;
    public static short KEY_E = 18;
    public static short KEY_R = 19;
    public static short KEY_T = 20;
    public static short KEY_Y = 21;
    public static short KEY_U = 22;
    public static short KEY_I = 23;
    public static short KEY_O = 24;
    public static short KEY_P = 25;
    public static short KEY_LEFTBRACE = 26;
    public static short KEY_RIGHTBRACE = 27;
    public static short KEY_ENTER = 28;
    public static short KEY_LEFTCTRL = 29;
    public static short KEY_A = 30;
    public static short KEY_S = 31;
    public static short KEY_D = 32;
    public static short KEY_F = 33;
    public static short KEY_G = 34;
    public static short KEY_H = 35;
    public static short KEY_J = 36;
    public static short KEY_K = 37;
    public static short KEY_L = 38;
    public static short KEY_SEMICOLON = 39;
    public static short KEY_APOSTROPHE = 40;
    public static short KEY_GRAVE = 41;
    public static short KEY_LEFTSHIFT = 42;
    public static short KEY_BACKSLASH = 43;
    public static short KEY_Z = 44;
    public static short KEY_X = 45;
    public static short KEY_C = 46;
    public static short KEY_V = 47;
    public static short KEY_B = 48;
    public static short KEY_N = 49;
    public static short KEY_M = 50;
    public static short KEY_COMMA = 51;
    public static short KEY_DOT = 52;
    public static short KEY_SLASH = 53;
    public static short KEY_RIGHTSHIFT = 54;
    public static short KEY_KPASTERISK = 55;
    public static short KEY_LEFTALT = 56;
    public static short KEY_SPACE = 57;
    public static short KEY_CAPSLOCK = 58;
    public static short KEY_F1 = 59;
    public static short KEY_F2 = 60;
    public static short KEY_F3 = 61;
    public static short KEY_F4 = 62;
    public static short KEY_F5 = 63;
    public static short KEY_F6 = 64;
    public static short KEY_F7 = 65;
    public static short KEY_F8 = 66;
    public static short KEY_F9 = 67;
    public static short KEY_F10 = 68;
    public static short KEY_NUMLOCK = 69;
    public static short KEY_SCROLLLOCK = 70;
    public static short KEY_KP7 = 71;
    public static short KEY_KP8 = 72;
    public static short KEY_KP9 = 73;
    public static short KEY_KPMINUS = 74;
    public static short KEY_KP4 = 75;
    public static short KEY_KP5 = 76;
    public static short KEY_KP6 = 77;
    public static short KEY_KPPLUS = 78;
    public static short KEY_KP1 = 79;
    public static short KEY_KP2 = 80;
    public static short KEY_KP3 = 81;
    public static short KEY_KP0 = 82;
    public static short KEY_KPDOT = 83;

    public static short KEY_ZENKAKUHANKAKU = 85;
    public static short KEY_102ND = 86;
    public static short KEY_F11 = 87;
    public static short KEY_F12 = 88;
    public static short KEY_RO = 89;
    public static short KEY_KATAKANA = 90;
    public static short KEY_HIRAGANA = 91;
    public static short KEY_HENKAN = 92;
    public static short KEY_KATAKANAHIRAGANA = 93;
    public static short KEY_MUHENKAN = 94;
    public static short KEY_KPJPCOMMA = 95;
    public static short KEY_KPENTER = 96;
    public static short KEY_RIGHTCTRL = 97;
    public static short KEY_KPSLASH = 98;
    public static short KEY_SYSRQ = 99;
    public static short KEY_RIGHTALT = 100;
    public static short KEY_LINEFEED = 101;
    public static short KEY_HOME = 102;
    public static short KEY_UP = 103;
    public static short KEY_PAGEUP = 104;
    public static short KEY_LEFT = 105;
    public static short KEY_RIGHT = 106;
    public static short KEY_END = 107;
    public static short KEY_DOWN = 108;
    public static short KEY_PAGEDOWN = 109;
    public static short KEY_INSERT = 110;
    public static short KEY_DELETE = 111;
    public static short KEY_MACRO = 112;
    public static short KEY_MUTE = 113;
    public static short KEY_VOLUMEDOWN = 114;
    public static short KEY_VOLUMEUP = 115;
    public static short KEY_POWER = 116;
    public static short KEY_KPEQUAL = 117;
    public static short KEY_KPPLUSMINUS = 118;
    public static short KEY_PAUSE = 119;
    public static short KEY_SCALE = 120;

    public static short KEY_KPCOMMA = 121;
    public static short KEY_HANGEUL = 122;
    public static short KEY_HANGUEL = KEY_HANGEUL;
    public static short KEY_HANJA = 123;
    public static short KEY_YEN = 124;
    public static short KEY_LEFTMETA = 125;
    public static short KEY_RIGHTMETA = 126;
    public static short KEY_COMPOSE = 127;

    public static short KEY_STOP = 128;
    public static short KEY_AGAIN = 129;
    public static short KEY_PROPS = 130;
    public static short KEY_UNDO = 131;
    public static short KEY_FRONT = 132;
    public static short KEY_COPY = 133;
    public static short KEY_OPEN = 134;
    public static short KEY_PASTE = 135;
    public static short KEY_FIND = 136;
    public static short KEY_CUT = 137;
    public static short KEY_HELP = 138;
    public static short KEY_MENU = 139;
    public static short KEY_CALC = 140;
    public static short KEY_SETUP = 141;
    public static short KEY_SLEEP = 142;
    public static short KEY_WAKEUP = 143;
    public static short KEY_FILE = 144;
    public static short KEY_SENDFILE = 145;
    public static short KEY_DELETEFILE = 146;
    public static short KEY_XFER = 147;
    public static short KEY_PROG1 = 148;
    public static short KEY_PROG2 = 149;
    public static short KEY_WWW = 150;
    public static short KEY_MSDOS = 151;
    public static short KEY_COFFEE = 152;
    public static short KEY_SCREENLOCK = KEY_COFFEE;
    public static short KEY_ROTATE_DISPLAY = 153;
    public static short KEY_DIRECTION = KEY_ROTATE_DISPLAY;
    public static short KEY_CYCLEWINDOWS = 154;
    public static short KEY_MAIL = 155;
    public static short KEY_BOOKMARKS = 156;
    public static short KEY_COMPUTER = 157;
    public static short KEY_BACK = 158;
    public static short KEY_FORWARD = 159;
    public static short KEY_CLOSECD = 160;
    public static short KEY_EJECTCD = 161;
    public static short KEY_EJECTCLOSECD = 162;
    public static short KEY_NEXTSONG = 163;
    public static short KEY_PLAYPAUSE = 164;
    public static short KEY_PREVIOUSSONG = 165;
    public static short KEY_STOPCD = 166;
    public static short KEY_RECORD = 167;
    public static short KEY_REWIND = 168;
    public static short KEY_PHONE = 169;
    public static short KEY_ISO = 170;
    public static short KEY_CONFIG = 171;
    public static short KEY_HOMEPAGE = 172;
    public static short KEY_REFRESH = 173;
    public static short KEY_EXIT = 174;
    public static short KEY_MOVE = 175;
    public static short KEY_EDIT = 176;
    public static short KEY_SCROLLUP = 177;
    public static short KEY_SCROLLDOWN = 178;
    public static short KEY_KPLEFTPAREN = 179;
    public static short KEY_KPRIGHTPAREN = 180;
    public static short KEY_NEW = 181;
    public static short KEY_REDO = 182;

    public static short KEY_F13 = 183;
    public static short KEY_F14 = 184;
    public static short KEY_F15 = 185;
    public static short KEY_F16 = 186;
    public static short KEY_F17 = 187;
    public static short KEY_F18 = 188;
    public static short KEY_F19 = 189;
    public static short KEY_F20 = 190;
    public static short KEY_F21 = 191;
    public static short KEY_F22 = 192;
    public static short KEY_F23 = 193;
    public static short KEY_F24 = 194;

    public static short KEY_PLAYCD = 200;
    public static short KEY_PAUSECD = 201;
    public static short KEY_PROG3 = 202;
    public static short KEY_PROG4 = 203;
    public static short KEY_DASHBOARD = 204;
    public static short KEY_SUSPEND = 205;
    public static short KEY_CLOSE = 206;
    public static short KEY_PLAY = 207;
    public static short KEY_FASTFORWARD = 208;
    public static short KEY_BASSBOOST = 209;
    public static short KEY_PRINT = 210;
    public static short KEY_HP = 211;
    public static short KEY_CAMERA = 212;
    public static short KEY_SOUND = 213;
    public static short KEY_QUESTION = 214;
    public static short KEY_EMAIL = 215;
    public static short KEY_CHAT = 216;
    public static short KEY_SEARCH = 217;
    public static short KEY_CONNECT = 218;
    public static short KEY_FINANCE = 219;
    public static short KEY_SPORT = 220;
    public static short KEY_SHOP = 221;
    public static short KEY_ALTERASE = 222;
    public static short KEY_CANCEL = 223;
    public static short KEY_BRIGHTNESSDOWN = 224;
    public static short KEY_BRIGHTNESSUP = 225;
    public static short KEY_MEDIA = 226;

    public static short KEY_SWITCHVIDEOMODE = 227;
    public static short KEY_KBDILLUMTOGGLE = 228;
    public static short KEY_KBDILLUMDOWN = 229;
    public static short KEY_KBDILLUMUP = 230;

    public static short KEY_SEND = 231;
    public static short KEY_REPLY = 232;
    public static short KEY_FORWARDMAIL = 233;
    public static short KEY_SAVE = 234;
    public static short KEY_DOCUMENTS = 235;

    public static short KEY_BATTERY = 236;

    public static short KEY_BLUETOOTH = 237;
    public static short KEY_WLAN = 238;
    public static short KEY_UWB = 239;

    public static short KEY_UNKNOWN = 240;

    public static short KEY_VIDEO_NEXT = 241;
    public static short KEY_VIDEO_PREV = 242;
    public static short KEY_BRIGHTNESS_CYCLE = 243;
    public static short KEY_BRIGHTNESS_AUTO = 244;
    public static short KEY_BRIGHTNESS_ZERO = KEY_BRIGHTNESS_AUTO;
    public static short KEY_DISPLAY_OFF = 245;

    public static short KEY_WWAN = 246;
    public static short KEY_WIMAX = KEY_WWAN;
    public static short KEY_RFKILL = 247;

    public static short KEY_MICMUTE = 248;

    public static short BTN_MISC = 0x100;
    public static short BTN_0 = 0x100;
    public static short BTN_1 = 0x101;
    public static short BTN_2 = 0x102;
    public static short BTN_3 = 0x103;
    public static short BTN_4 = 0x104;
    public static short BTN_5 = 0x105;
    public static short BTN_6 = 0x106;
    public static short BTN_7 = 0x107;
    public static short BTN_8 = 0x108;
    public static short BTN_9 = 0x109;

    public static short BTN_MOUSE = 0x110;
    public static short BTN_LEFT = 0x110;
    public static short BTN_RIGHT = 0x111;
    public static short BTN_MIDDLE = 0x112;
    public static short BTN_SIDE = 0x113;
    public static short BTN_EXTRA = 0x114;
    public static short BTN_FORWARD = 0x115;
    public static short BTN_BACK = 0x116;
    public static short BTN_TASK = 0x117;

    public static short BTN_JOYSTICK = 0x120;
    public static short BTN_TRIGGER = 0x120;
    public static short BTN_THUMB = 0x121;
    public static short BTN_THUMB2 = 0x122;
    public static short BTN_TOP = 0x123;
    public static short BTN_TOP2 = 0x124;
    public static short BTN_PINKIE = 0x125;
    public static short BTN_BASE = 0x126;
    public static short BTN_BASE2 = 0x127;
    public static short BTN_BASE3 = 0x128;
    public static short BTN_BASE4 = 0x129;
    public static short BTN_BASE5 = 0x12a;
    public static short BTN_BASE6 = 0x12b;
    public static short BTN_DEAD = 0x12f;

    public static short BTN_GAMEPAD = 0x130;
    public static short BTN_SOUTH = 0x130;
    public static short BTN_A = BTN_SOUTH;
    public static short BTN_EAST = 0x131;
    public static short BTN_B = BTN_EAST;
    public static short BTN_C = 0x132;
    public static short BTN_NORTH = 0x133;
    public static short BTN_X = BTN_NORTH;
    public static short BTN_WEST = 0x134;
    public static short BTN_Y = BTN_WEST;
    public static short BTN_Z = 0x135;
    public static short BTN_TL = 0x136;
    public static short BTN_TR = 0x137;
    public static short BTN_TL2 = 0x138;
    public static short BTN_TR2 = 0x139;
    public static short BTN_SELECT = 0x13a;
    public static short BTN_START = 0x13b;
    public static short BTN_MODE = 0x13c;
    public static short BTN_THUMBL = 0x13d;
    public static short BTN_THUMBR = 0x13e;

    public static short BTN_DIGI = 0x140;
    public static short BTN_TOOL_PEN = 0x140;
    public static short BTN_TOOL_RUBBER = 0x141;
    public static short BTN_TOOL_BRUSH = 0x142;
    public static short BTN_TOOL_PENCIL = 0x143;
    public static short BTN_TOOL_AIRBRUSH = 0x144;
    public static short BTN_TOOL_FINGER = 0x145;
    public static short BTN_TOOL_MOUSE = 0x146;
    public static short BTN_TOOL_LENS = 0x147;
    public static short BTN_TOOL_QUINTTAP = 0x148;
    public static short BTN_TOUCH = 0x14a;
    public static short BTN_STYLUS = 0x14b;
    public static short BTN_STYLUS2 = 0x14c;
    public static short BTN_TOOL_DOUBLETAP = 0x14d;
    public static short BTN_TOOL_TRIPLETAP = 0x14e;
    public static short BTN_TOOL_QUADTAP = 0x14f;

    public static short BTN_WHEEL = 0x150;
    public static short BTN_GEAR_DOWN = 0x150;
    public static short BTN_GEAR_UP = 0x151;

    public static short KEY_OK = 0x160;
    public static short KEY_SELECT = 0x161;
    public static short KEY_GOTO = 0x162;
    public static short KEY_CLEAR = 0x163;
    public static short KEY_POWER2 = 0x164;
    public static short KEY_OPTION = 0x165;
    public static short KEY_INFO = 0x166;
    public static short KEY_TIME = 0x167;
    public static short KEY_VENDOR = 0x168;
    public static short KEY_ARCHIVE = 0x169;
    public static short KEY_PROGRAM = 0x16a;
    public static short KEY_CHANNEL = 0x16b;
    public static short KEY_FAVORITES = 0x16c;
    public static short KEY_EPG = 0x16d;
    public static short KEY_PVR = 0x16e;
    public static short KEY_MHP = 0x16f;
    public static short KEY_LANGUAGE = 0x170;
    public static short KEY_TITLE = 0x171;
    public static short KEY_SUBTITLE = 0x172;
    public static short KEY_ANGLE = 0x173;
    public static short KEY_ZOOM = 0x174;
    public static short KEY_MODE = 0x175;
    public static short KEY_KEYBOARD = 0x176;
    public static short KEY_SCREEN = 0x177;
    public static short KEY_PC = 0x178;
    public static short KEY_TV = 0x179;
    public static short KEY_TV2 = 0x17a;
    public static short KEY_VCR = 0x17b;
    public static short KEY_VCR2 = 0x17c;
    public static short KEY_SAT = 0x17d;
    public static short KEY_SAT2 = 0x17e;
    public static short KEY_CD = 0x17f;
    public static short KEY_TAPE = 0x180;
    public static short KEY_RADIO = 0x181;
    public static short KEY_TUNER = 0x182;
    public static short KEY_PLAYER = 0x183;
    public static short KEY_TEXT = 0x184;
    public static short KEY_DVD = 0x185;
    public static short KEY_AUX = 0x186;
    public static short KEY_MP3 = 0x187;
    public static short KEY_AUDIO = 0x188;
    public static short KEY_VIDEO = 0x189;
    public static short KEY_DIRECTORY = 0x18a;
    public static short KEY_LIST = 0x18b;
    public static short KEY_MEMO = 0x18c;
    public static short KEY_CALENDAR = 0x18d;
    public static short KEY_RED = 0x18e;
    public static short KEY_GREEN = 0x18f;
    public static short KEY_YELLOW = 0x190;
    public static short KEY_BLUE = 0x191;
    public static short KEY_CHANNELUP = 0x192;
    public static short KEY_CHANNELDOWN = 0x193;
    public static short KEY_FIRST = 0x194;
    public static short KEY_LAST = 0x195;
    public static short KEY_AB = 0x196;
    public static short KEY_NEXT = 0x197;
    public static short KEY_RESTART = 0x198;
    public static short KEY_SLOW = 0x199;
    public static short KEY_SHUFFLE = 0x19a;
    public static short KEY_BREAK = 0x19b;
    public static short KEY_PREVIOUS = 0x19c;
    public static short KEY_DIGITS = 0x19d;
    public static short KEY_TEEN = 0x19e;
    public static short KEY_TWEN = 0x19f;
    public static short KEY_VIDEOPHONE = 0x1a0;
    public static short KEY_GAMES = 0x1a1;
    public static short KEY_ZOOMIN = 0x1a2;
    public static short KEY_ZOOMOUT = 0x1a3;
    public static short KEY_ZOOMRESET = 0x1a4;
    public static short KEY_WORDPROCESSOR = 0x1a5;
    public static short KEY_EDITOR = 0x1a6;
    public static short KEY_SPREADSHEET = 0x1a7;
    public static short KEY_GRAPHICSEDITOR = 0x1a8;
    public static short KEY_PRESENTATION = 0x1a9;
    public static short KEY_DATABASE = 0x1aa;
    public static short KEY_NEWS = 0x1ab;
    public static short KEY_VOICEMAIL = 0x1ac;
    public static short KEY_ADDRESSBOOK = 0x1ad;
    public static short KEY_MESSENGER = 0x1ae;
    public static short KEY_DISPLAYTOGGLE = 0x1af;
    public static short KEY_BRIGHTNESS_TOGGLE = KEY_DISPLAYTOGGLE;
    public static short KEY_SPELLCHECK = 0x1b0;
    public static short KEY_LOGOFF = 0x1b1;

    public static short KEY_DOLLAR = 0x1b2;
    public static short KEY_EURO = 0x1b3;

    public static short KEY_FRAMEBACK = 0x1b4;
    public static short KEY_FRAMEFORWARD = 0x1b5;
    public static short KEY_CONTEXT_MENU = 0x1b6;
    public static short KEY_MEDIA_REPEAT = 0x1b7;
    public static short KEY_10CHANNELSUP = 0x1b8;
    public static short KEY_10CHANNELSDOWN = 0x1b9;
    public static short KEY_IMAGES = 0x1ba;

    public static short KEY_DEL_EOL = 0x1c0;
    public static short KEY_DEL_EOS = 0x1c1;
    public static short KEY_INS_LINE = 0x1c2;
    public static short KEY_DEL_LINE = 0x1c3;

    public static short KEY_FN = 0x1d0;
    public static short KEY_FN_ESC = 0x1d1;
    public static short KEY_FN_F1 = 0x1d2;
    public static short KEY_FN_F2 = 0x1d3;
    public static short KEY_FN_F3 = 0x1d4;
    public static short KEY_FN_F4 = 0x1d5;
    public static short KEY_FN_F5 = 0x1d6;
    public static short KEY_FN_F6 = 0x1d7;
    public static short KEY_FN_F7 = 0x1d8;
    public static short KEY_FN_F8 = 0x1d9;
    public static short KEY_FN_F9 = 0x1da;
    public static short KEY_FN_F10 = 0x1db;
    public static short KEY_FN_F11 = 0x1dc;
    public static short KEY_FN_F12 = 0x1dd;
    public static short KEY_FN_1 = 0x1de;
    public static short KEY_FN_2 = 0x1df;
    public static short KEY_FN_D = 0x1e0;
    public static short KEY_FN_E = 0x1e1;
    public static short KEY_FN_F = 0x1e2;
    public static short KEY_FN_S = 0x1e3;
    public static short KEY_FN_B = 0x1e4;

    public static short KEY_BRL_DOT1 = 0x1f1;
    public static short KEY_BRL_DOT2 = 0x1f2;
    public static short KEY_BRL_DOT3 = 0x1f3;
    public static short KEY_BRL_DOT4 = 0x1f4;
    public static short KEY_BRL_DOT5 = 0x1f5;
    public static short KEY_BRL_DOT6 = 0x1f6;
    public static short KEY_BRL_DOT7 = 0x1f7;
    public static short KEY_BRL_DOT8 = 0x1f8;
    public static short KEY_BRL_DOT9 = 0x1f9;
    public static short KEY_BRL_DOT10 = 0x1fa;

    public static short KEY_NUMERIC_0 = 0x200;
    public static short KEY_NUMERIC_1 = 0x201;
    public static short KEY_NUMERIC_2 = 0x202;
    public static short KEY_NUMERIC_3 = 0x203;
    public static short KEY_NUMERIC_4 = 0x204;
    public static short KEY_NUMERIC_5 = 0x205;
    public static short KEY_NUMERIC_6 = 0x206;
    public static short KEY_NUMERIC_7 = 0x207;
    public static short KEY_NUMERIC_8 = 0x208;
    public static short KEY_NUMERIC_9 = 0x209;
    public static short KEY_NUMERIC_STAR = 0x20a;
    public static short KEY_NUMERIC_POUND = 0x20b;
    public static short KEY_NUMERIC_A = 0x20c;
    public static short KEY_NUMERIC_B = 0x20d;
    public static short KEY_NUMERIC_C = 0x20e;
    public static short KEY_NUMERIC_D = 0x20f;

    public static short KEY_CAMERA_FOCUS = 0x210;
    public static short KEY_WPS_BUTTON = 0x211;

    public static short KEY_TOUCHPAD_TOGGLE = 0x212;
    public static short KEY_TOUCHPAD_ON = 0x213;
    public static short KEY_TOUCHPAD_OFF = 0x214;

    public static short KEY_CAMERA_ZOOMIN = 0x215;
    public static short KEY_CAMERA_ZOOMOUT = 0x216;
    public static short KEY_CAMERA_UP = 0x217;
    public static short KEY_CAMERA_DOWN = 0x218;
    public static short KEY_CAMERA_LEFT = 0x219;
    public static short KEY_CAMERA_RIGHT = 0x21a;

    public static short KEY_ATTENDANT_ON = 0x21b;
    public static short KEY_ATTENDANT_OFF = 0x21c;
    public static short KEY_ATTENDANT_TOGGLE = 0x21d;
    public static short KEY_LIGHTS_TOGGLE = 0x21e;

    public static short BTN_DPAD_UP = 0x220;
    public static short BTN_DPAD_DOWN = 0x221;
    public static short BTN_DPAD_LEFT = 0x222;
    public static short BTN_DPAD_RIGHT = 0x223;

    public static short KEY_ALS_TOGGLE = 0x230;

    public static short KEY_BUTTONCONFIG = 0x240;
    public static short KEY_TASKMANAGER = 0x241;
    public static short KEY_JOURNAL = 0x242;
    public static short KEY_CONTROLPANEL = 0x243;
    public static short KEY_APPSELECT = 0x244;
    public static short KEY_SCREENSAVER = 0x245;
    public static short KEY_VOICECOMMAND = 0x246;
    public static short KEY_ASSISTANT = 0x247;

    public static short KEY_BRIGHTNESS_MIN = 0x250;
    public static short KEY_BRIGHTNESS_MAX = 0x251;

    public static short KEY_KBDINPUTASSIST_PREV = 0x260;
    public static short KEY_KBDINPUTASSIST_NEXT = 0x261;
    public static short KEY_KBDINPUTASSIST_PREVGROUP = 0x262;
    public static short KEY_KBDINPUTASSIST_NEXTGROUP = 0x263;
    public static short KEY_KBDINPUTASSIST_ACCEPT = 0x264;
    public static short KEY_KBDINPUTASSIST_CANCEL = 0x265;
    public static short KEY_RIGHT_UP = 0x266;
    public static short KEY_RIGHT_DOWN = 0x267;
    public static short KEY_LEFT_UP = 0x268;
    public static short KEY_LEFT_DOWN = 0x269;

    public static short KEY_ROOT_MENU = 0x26a;
    public static short KEY_MEDIA_TOP_MENU = 0x26b;
    public static short KEY_NUMERIC_11 = 0x26c;
    public static short KEY_NUMERIC_12 = 0x26d;
    public static short KEY_AUDIO_DESC = 0x26e;
    public static short KEY_3D_MODE = 0x26f;
    public static short KEY_NEXT_FAVORITE = 0x270;
    public static short KEY_STOP_RECORD = 0x271;
    public static short KEY_PAUSE_RECORD = 0x272;
    public static short KEY_VOD = 0x273;
    public static short KEY_UNMUTE = 0x274;
    public static short KEY_FASTREVERSE = 0x275;
    public static short KEY_SLOWREVERSE = 0x276;
    public static short KEY_DATA = 0x277;
    public static short KEY_ONSCREEN_KEYBOARD = 0x278;

    public static short BTN_TRIGGER_HAPPY = 0x2c0;
    public static short BTN_TRIGGER_HAPPY1 = 0x2c0;
    public static short BTN_TRIGGER_HAPPY2 = 0x2c1;
    public static short BTN_TRIGGER_HAPPY3 = 0x2c2;
    public static short BTN_TRIGGER_HAPPY4 = 0x2c3;
    public static short BTN_TRIGGER_HAPPY5 = 0x2c4;
    public static short BTN_TRIGGER_HAPPY6 = 0x2c5;
    public static short BTN_TRIGGER_HAPPY7 = 0x2c6;
    public static short BTN_TRIGGER_HAPPY8 = 0x2c7;
    public static short BTN_TRIGGER_HAPPY9 = 0x2c8;
    public static short BTN_TRIGGER_HAPPY10 = 0x2c9;
    public static short BTN_TRIGGER_HAPPY11 = 0x2ca;
    public static short BTN_TRIGGER_HAPPY12 = 0x2cb;
    public static short BTN_TRIGGER_HAPPY13 = 0x2cc;
    public static short BTN_TRIGGER_HAPPY14 = 0x2cd;
    public static short BTN_TRIGGER_HAPPY15 = 0x2ce;
    public static short BTN_TRIGGER_HAPPY16 = 0x2cf;
    public static short BTN_TRIGGER_HAPPY17 = 0x2d0;
    public static short BTN_TRIGGER_HAPPY18 = 0x2d1;
    public static short BTN_TRIGGER_HAPPY19 = 0x2d2;
    public static short BTN_TRIGGER_HAPPY20 = 0x2d3;
    public static short BTN_TRIGGER_HAPPY21 = 0x2d4;
    public static short BTN_TRIGGER_HAPPY22 = 0x2d5;
    public static short BTN_TRIGGER_HAPPY23 = 0x2d6;
    public static short BTN_TRIGGER_HAPPY24 = 0x2d7;
    public static short BTN_TRIGGER_HAPPY25 = 0x2d8;
    public static short BTN_TRIGGER_HAPPY26 = 0x2d9;
    public static short BTN_TRIGGER_HAPPY27 = 0x2da;
    public static short BTN_TRIGGER_HAPPY28 = 0x2db;
    public static short BTN_TRIGGER_HAPPY29 = 0x2dc;
    public static short BTN_TRIGGER_HAPPY30 = 0x2dd;
    public static short BTN_TRIGGER_HAPPY31 = 0x2de;
    public static short BTN_TRIGGER_HAPPY32 = 0x2df;
    public static short BTN_TRIGGER_HAPPY33 = 0x2e0;
    public static short BTN_TRIGGER_HAPPY34 = 0x2e1;
    public static short BTN_TRIGGER_HAPPY35 = 0x2e2;
    public static short BTN_TRIGGER_HAPPY36 = 0x2e3;
    public static short BTN_TRIGGER_HAPPY37 = 0x2e4;
    public static short BTN_TRIGGER_HAPPY38 = 0x2e5;
    public static short BTN_TRIGGER_HAPPY39 = 0x2e6;
    public static short BTN_TRIGGER_HAPPY40 = 0x2e7;
    public static short KEY_MIN_INTERESTING = KEY_MUTE;
    public static short KEY_MAX = 0x2ff;
    public static short KEY_CNT = (short) (KEY_MAX + 1);

    public static short REL_X = 0x00;
    public static short REL_Y = 0x01;
    public static short REL_Z = 0x02;
    public static short REL_RX = 0x03;
    public static short REL_RY = 0x04;
    public static short REL_RZ = 0x05;
    public static short REL_HWHEEL = 0x06;
    public static short REL_DIAL = 0x07;
    public static short REL_WHEEL = 0x08;
    public static short REL_MISC = 0x09;
    public static short REL_MAX = 0x0f;
    public static short REL_CNT = (short) (REL_MAX + 1);

    public static short ABS_X = 0x00;
    public static short ABS_Y = 0x01;
    public static short ABS_Z = 0x02;
    public static short ABS_RX = 0x03;
    public static short ABS_RY = 0x04;
    public static short ABS_RZ = 0x05;
    public static short ABS_THROTTLE = 0x06;
    public static short ABS_RUDDER = 0x07;
    public static short ABS_WHEEL = 0x08;
    public static short ABS_GAS = 0x09;
    public static short ABS_BRAKE = 0x0a;
    public static short ABS_HAT0X = 0x10;
    public static short ABS_HAT0Y = 0x11;
    public static short ABS_HAT1X = 0x12;
    public static short ABS_HAT1Y = 0x13;
    public static short ABS_HAT2X = 0x14;
    public static short ABS_HAT2Y = 0x15;
    public static short ABS_HAT3X = 0x16;
    public static short ABS_HAT3Y = 0x17;
    public static short ABS_PRESSURE = 0x18;
    public static short ABS_DISTANCE = 0x19;
    public static short ABS_TILT_X = 0x1a;
    public static short ABS_TILT_Y = 0x1b;
    public static short ABS_TOOL_WIDTH = 0x1c;

    public static short ABS_VOLUME = 0x20;

    public static short ABS_MISC = 0x28;

    public static short ABS_MT_SLOT = 0x2f;
    public static short ABS_MT_TOUCH_MAJOR = 0x30;
    public static short ABS_MT_TOUCH_MINOR = 0x31;
    public static short ABS_MT_WIDTH_MAJOR = 0x32;
    public static short ABS_MT_WIDTH_MINOR = 0x33;
    public static short ABS_MT_ORIENTATION = 0x34;
    public static short ABS_MT_POSITION_X = 0x35;
    public static short ABS_MT_POSITION_Y = 0x36;
    public static short ABS_MT_TOOL_TYPE = 0x37;
    public static short ABS_MT_BLOB_ID = 0x38;
    public static short ABS_MT_TRACKING_ID = 0x39;
    public static short ABS_MT_PRESSURE = 0x3a;
    public static short ABS_MT_DISTANCE = 0x3b;
    public static short ABS_MT_TOOL_X = 0x3c;
    public static short ABS_MT_TOOL_Y = 0x3d;

    public static short ABS_MAX = 0x3f;
    public static short ABS_CNT = (short) (ABS_MAX + 1);

    public static short SW_LID = 0x00;
    public static short SW_TABLET_MODE = 0x01;
    public static short SW_HEADPHONE_INSERT = 0x02;
    public static short SW_RFKILL_ALL = 0x03;
    public static short SW_RADIO = SW_RFKILL_ALL;
    public static short SW_MICROPHONE_INSERT = 0x04;
    public static short SW_DOCK = 0x05;
    public static short SW_LINEOUT_INSERT = 0x06;
    public static short SW_JACK_PHYSICAL_INSERT = 0x07;
    public static short SW_VIDEOOUT_INSERT = 0x08;
    public static short SW_CAMERA_LENS_COVER = 0x09;
    public static short SW_KEYPAD_SLIDE = 0x0a;
    public static short SW_FRONT_PROXIMITY = 0x0b;
    public static short SW_ROTATE_LOCK = 0x0c;
    public static short SW_LINEIN_INSERT = 0x0d;
    public static short SW_MUTE_DEVICE = 0x0e;
    public static short SW_PEN_INSERTED = 0x0f;
    public static short SW_MAX = 0x0f;
    public static short SW_CNT = (short) (SW_MAX + 1);

    public static short MSC_SERIAL = 0x00;
    public static short MSC_PULSELED = 0x01;
    public static short MSC_GESTURE = 0x02;
    public static short MSC_RAW = 0x03;
    public static short MSC_SCAN = 0x04;
    public static short MSC_TIMESTAMP = 0x05;
    public static short MSC_MAX = 0x07;
    public static short MSC_CNT = (short) (MSC_MAX + 1);

    public static short LED_NUML = 0x00;
    public static short LED_CAPSL = 0x01;
    public static short LED_SCROLLL = 0x02;
    public static short LED_COMPOSE = 0x03;
    public static short LED_KANA = 0x04;
    public static short LED_SLEEP = 0x05;
    public static short LED_SUSPEND = 0x06;
    public static short LED_MUTE = 0x07;
    public static short LED_MISC = 0x08;
    public static short LED_MAIL = 0x09;
    public static short LED_CHARGING = 0x0a;
    public static short LED_MAX = 0x0f;
    public static short LED_CNT = (short) (LED_MAX + 1);

    public static short REP_DELAY = 0x00;
    public static short REP_PERIOD = 0x01;
    public static short REP_MAX = 0x01;
    public static short REP_CNT = (short) (REP_MAX + 1);

    public static short SND_CLICK = 0x00;
    public static short SND_BELL = 0x01;
    public static short SND_TONE = 0x02;
    public static short SND_MAX = 0x07;
    public static short SND_CNT = (short) (SND_MAX + 1);
    
    // END TRANSLATED LINUX SOURCE
	
	private volatile static HashMap<String, OrbitInputEventsReader> readersMap = new HashMap<>();
	
	private volatile Thread nativeThread = new Thread(this::run);
	private volatile ArrayList<EventHandler> handlers = new ArrayList<>();
	private volatile boolean work = true;
	private volatile String path;
	
	private OrbitInputEventsReader(String path) {
		this.path = path;
		nativeThread.start();
	}
	
	private native void run();
	
	private void addHandlerInternal(EventHandler handler) {
		handlers.add(handler);
	}
	
	@SuppressWarnings("deprecation")
	private void removeHandlerInternal(EventHandler handler) throws InterruptedException {
		handlers.remove(handler);
		if (handlers.size() == 0) {
			work = false;
			nativeThread.interrupt();
			nativeThread.join(500);
			if (nativeThread.isAlive()) {
				nativeThread.stop();
			}
		}
	}
	
	public static void addHandler(String path, EventHandler handler) {
		synchronized (OrbitInputEventsReader.class) {
			readersMap.computeIfAbsent(path, OrbitInputEventsReader::new).addHandlerInternal(handler);	
		}
	}
	
	public static void removeHandler(String path, EventHandler handler) throws InterruptedException {
		synchronized (OrbitInputEventsReader.class) {
			if (readersMap.containsKey(path)) {
				readersMap.get(path).removeHandlerInternal(handler);
			}
		}
	}
	
	public static void removeHandler(EventHandler handler) {
		synchronized (OrbitInputEventsReader.class) {
			readersMap.values().forEach(OrbitFunctionUtilities.specializeSecond(OrbitFunctionUtilities.wrapException(OrbitInputEventsReader::removeHandlerInternal, RuntimeException::new), handler));
		}
	}
	
	@FunctionalInterface
	public static interface EventHandler {
		void acceptInputEvent(Instant time, short type, short code, int value);
	}
}
