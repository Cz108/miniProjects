
#ifndef STRUCTS_H
#define STRUCTS_H

#include "define.h"
#include "stdio.h"

struct APP{
	struct SDL_Renderer *renderer;
	struct SDL_Window *window;
	struct SDL_Texture *scrtex;
	struct SDL_Surface *screen;
	struct SDL_Surface *charset;
	struct SDL_Surface *place;

};
extern APP app;

struct COORDINATE {
	int x;
	int y;
};
struct ALLINFO {
	int x;
	int y;
	int value;
	
};
//extern COORDINATE CoXY;




#endif 
