#ifndef MAIN_H
#define MAIN_H
#include "define.h"
extern int quit;
extern int newgame;
extern char text[128];
extern int matrix[4][4];
extern int rand_number;
extern int matrix_value;
extern int GRID_SIZE;
extern int totalScore;

void save();
void load(int load_i);
bool isWin(int matrix[4][4]);
bool isLose(int matrix[4][4]);
void saveruntime();
void undoing();

extern int loadMenu_flag;
extern int playDisplay;
extern char * value_ctime;
void myTime(void);
extern int load_i;
extern int save_i;
extern char *pointer_timesets;

extern char *savetime1;
extern char *savetime2;
extern char *savetime3;
extern char *savetime4;
void mySaveTime(void);
#endif

