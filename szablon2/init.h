
#ifndef INIT_H
#define INIT_H

#include "define.h"

extern int quit;

//-----------------------------------
void initSDL(void);
void cleanup(void);
int initBMP(void);
void initMenu(void);
void initMatrix(int matrix[4][4]);
struct COORDINATE findRandomCord(int matrix[4][4]);
extern int random_x;
extern int random_y;
bool fillnumber(int matrix[4][4]);
bool canGoLeft(int matrix[4][4]);
bool canGoRight(int matrix[4][4]);
bool canGoUp(int matrix[4][4]);
bool canGoDown(int matrix[4][4]);
void moveUpMatrix(int matrix[4][4]);
void moveDownMatrix(int matrix[4][4]);
void moveLeftMatrix(int matrix[4][4]);
void moveRightMatrix(int matrix[4][4]);
void mergeLeft(int matrix[4][4]);
void mergeRight(int matrix[4][4]);
void mergeUp(int matrix[4][4]);
void mergeDown(int matrix[4][4]);
extern int move_x; 
extern int move_y;
extern int stepScore;

//extern APP app;

#endif