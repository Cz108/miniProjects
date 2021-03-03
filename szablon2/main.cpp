#define _USE_MATH_DEFINES
//-------------------------
#include "main.h"
#include "define.h"
#include "input.h"
#include "draw.h"
#include "init.h"
#include "structs.h"
//-------------------------
#include<math.h>
#include<stdio.h>
#include<string.h>
#include<time.h>
#include<fstream>
#include<iostream> 
#include "function.h"

//sdl_platform.h from line 132 to line137 commented
extern "C" { 
#include"./sdl-2.0.7/include/SDL.h"
#include"./sdl-2.0.7/include/SDL_main.h"
}
//#define GRID_SIZE gs
int quit = 0; //extern
int newgame = 0;
char text[128];
//int black,green,red,blue;
int t1, t2;
double delta, worldTime, fpsTimer, fps;
//void game()
//{
//	int black = SDL_MapRGB(app.screen->format, 0x00, 0x00, 0x00);
//	int green = SDL_MapRGB(app.screen->format, 0x12, 0x78, 0x87);
//	int red = SDL_MapRGB(app.screen->format, 0xFF, 0x00, 0x00);
//	int blue = SDL_MapRGB(app.screen->format, 0x11, 0x11, 0xCC);
//	SDL_FillRect(app.screen, NULL, black);
//	DrawRectangle(app.screen, 60, 60, SCREEN_WIDTH - 120, SCREEN_HEIGHT - 120, red, green);
//	DrawRectangle(app.screen, 4, 4, SCREEN_WIDTH - 8, 36, red, blue);
//	//            "template for the second project, elapsed time = %.1lf s  %.0lf frames / s"
//	sprintf(text, "Szablon drugiego zadania, czas trwania = %.1lf s  %.0lf klatek / s", worldTime, fps);
//	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, 10, text, app.charset);
//	//	      "Esc - exit, \030 - faster, \031 - slower"
//	sprintf(text, "Esc - wyjscie, \030 - przyspieszenie, \031 - zwolnienie");
//	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, 26, text, app.charset);
//
//	/*DrawSurface(app.screen, app.place,SCREEN_WIDTH / 2 ,SCREEN_HEIGHT / 2);*/
//	//--------------------------------------------------------------------------------------------------------
//	SDL_UpdateTexture(app.scrtex, NULL, app.screen->pixels, app.screen->pitch);
//	//		SDL_RenderClear(renderer);
//	SDL_RenderCopy(app.renderer, app.scrtex, NULL, NULL);
//	SDL_RenderPresent(app.renderer);
//	//--------------------------------------------------------------------------------------------------------
//}
int GRID_SIZE = 60;

int matrix[4][4];

int rand_number;
int matrix_value;
int totalScore = 0;
int getMax(int matrix[4][4])
{
	int max = 0;
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4; j++)
		{
			if (max < matrix[i][j])
			{
				max = matrix[i][j];
			}
		}
	}
	return max;
}
bool isWin(int matrix[4][4])
{
	if (getMax(matrix) == 2048)
	{
		return true;
	}
	else
		return false;
}
bool isLose(int matrix[4][4])
{
	if (canGoDown(matrix) || canGoLeft(matrix) || canGoRight(matrix) || canGoUp(matrix))
	{
		return false;
	}
	else
		return true;
	
}

FILE *undo;
void undoing(void)
{
	if ((undo = fopen("undoing.txt", "r+")) == NULL)
	{
		fprintf(stderr, "Cannot open Undo.txt\n");
		exit(1);
	}
	fread(matrix, sizeof(int), sizeof(matrix), undo);
	fclose(undo);
}
void saveruntime(void)
{
	if ((undo = fopen("undoing.txt", "w+")) == NULL)
	{
		fprintf(stderr, "Cannot open Undo.txt\n");
		exit(1);
	}
	fwrite(matrix, sizeof(int), sizeof(matrix), undo);
	fclose(undo);
}

int save_i = 1;

FILE *mem;
char filename[30];
char load_filename[30];
//char *savetime_i = 0;
char *savetime1 = 0;
char timetemp = 0;
char *savetime2 = 0;
char *savetime3 = 0;
char *savetime4 = 0;
void save(void)
{
	/*myTime();
	char * filename;
	filename = (char*)malloc(sizeof(char));
	sprintf(filename,"%s.txt",*value_ctime);
	if ((mem = fopen(filename, "w+")) == NULL)
	{
		fprintf(stderr, "Cannot open memory.txt\n");
		exit(1);
	}*/
	//mySaveTime();
	sprintf(filename, "file%d.txt",save_i);
	//if (save_i == 1)
	//{
	//	//savetime1 = value_ctime;
	//	memcpy(savetime1,value_ctime,256);
	//}
	//else if (save_i == 2)
	//{
	//	savetime2 = value_ctime;	
	//}
	//else if (save_i == 3)
	//{
	//	savetime3 = value_ctime;
	//}
	//else if (save_i == 4)
	//{
	//	savetime4 = value_ctime;
	//}
	if((mem = fopen(filename, "w+")) == NULL)
	{
		fprintf(stderr, "Cannot open savefile\n");
		exit(1);
	}
	//myTime();
	/*if ((mem = fopen("memory.txt", "w+")) == NULL)
	{
		fprintf(stderr, "Cannot open memory.txt\n");
		exit(1);
	}*/
	fwrite(matrix, sizeof(int), sizeof(matrix), mem);
	fclose(mem);
	save_i++;
	if (save_i >= 5)
		save_i = 0;
}
int load_i;
void load(int load_i)
{
	sprintf(load_filename, "file%d.txt", load_i);
	if ((mem = fopen(load_filename, "r+")) == NULL)
	{
		fprintf(stderr, "Cannot open loadfile\n");
		exit(1);
	}
	fread(matrix, sizeof(int), sizeof(matrix), mem);
	fclose(mem);
}

//void mySaveTime(void)
//{
//	char* temp_ctime;
//	time_t clt;
//	clt = time(NULL);
//	temp_ctime = ctime(&clt);
//	int len; int len1;
//	len = strlen(temp_ctime);
////	len1 = strlen(savetime1);
//	if (save_i == 1)
//	{
//		savetime1 = strcpy(savetime1,temp_ctime);
//		for (int i = 0; i < len - 1; i++)
//		{
//			savetime1[i] = temp_ctime[i];
//			
//		}
//		savetime1[len - 1] = '\0';
//	}
//	else if (save_i == 2)
//	{
//		//savetime2 = strcpy(savetime1, temp_ctime);
//	}
//}
char * value_ctime = 0;
void myTime(void)
{	
	//struct tm *pt;
	time_t clt;
	clt = time(NULL);
	value_ctime = ctime(&clt);
	//pt = localtime(&clt);
	
}
int loadMenu_flag = 0;
int playDisplay = 1;

void loadMenu()
{
	sprintf(text, "Choose your saved game, then press the key of the number");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT * 2 / 11), text, app.charset);
	//sprintf(text,savetime1);

	sprintf(text, "No.1");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT * 4 / 11), text, app.charset);	
	//sprintf(text, savetime2);
	sprintf(text, "No.2");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT * 5 / 11), text, app.charset);
//	sprintf(text, savetime3);
	sprintf(text, "No.3");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT * 6 / 11), text, app.charset);
	//sprintf(text, savetime4);
	sprintf(text,"No.4");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT * 7 / 11), text, app.charset);
	//SDL_UpdateTexture(app.scrtex, NULL, app.screen->pixels, app.screen->pitch);
	//SDL_RenderCopy(app.renderer, app.scrtex, NULL, NULL);
	//SDL_RenderPresent(app.renderer);
}
int main(int argc, char **argv)
{	
	srand((unsigned int)(time(NULL)));
	memset(&app, 0, sizeof(APP));
	initSDL();
	atexit(cleanup);	
	initBMP();
	
	int black = SDL_MapRGB(app.screen->format, 0x00, 0x00, 0x00);
	int green = SDL_MapRGB(app.screen->format, 0x12, 0x78, 0x87);
	int red = SDL_MapRGB(app.screen->format, 0xFF, 0x00, 0x00);
	int blue = SDL_MapRGB(app.screen->format, 0x11, 0x11, 0xCC);
	int gray_level1_background = SDL_MapRGB(app.screen->format, 221, 213, 195);
	int gray_level2_background = SDL_MapRGB(app.screen->format, 175, 156, 112);
	int orange_level_background = SDL_MapRGB(app.screen->format, 203, 194, 178); //0
	int orange_level_line = SDL_MapRGB(app.screen->format, 235, 228, 217);  

	int orange_level_1 = SDL_MapRGB(app.screen->format, 238, 222, 199);  //2
	int orange_level_2 = SDL_MapRGB(app.screen->format, 243, 151, 99); //4
	int orange_level_3 = SDL_MapRGB(app.screen->format, 242, 156, 92); //8
	int orange_level_4 = SDL_MapRGB(app.screen->format, 239, 129, 97); //16
	int orange_level_5 = SDL_MapRGB(app.screen->format, 241, 100, 50);  //32
	int orange_level_6 = SDL_MapRGB(app.screen->format, 237, 206, 93);  //64
	int orange_level_7 = SDL_MapRGB(app.screen->format, 237, 200, 80);  //128
	int orange_level_8 = SDL_MapRGB(app.screen->format, 237, 197, 63);   //256
	int orange_level_9 = SDL_MapRGB(app.screen->format, 237, 194, 46);  //512
	int orange_level_10 = SDL_MapRGB(app.screen->format, 184, 132, 172);  //1024
	int orange_level_11= SDL_MapRGB(app.screen->format, 127, 61, 122);  //2048
	
	while (!quit)
	{
		SDL_FillRect(app.screen, NULL, black);
		DrawRectangle(app.screen, 60, 60, SCREEN_WIDTH - 120, SCREEN_HEIGHT - 120, red, gray_level2_background);
		initMenu();		
		doInput(); 
		
		//int f = 0;
		if (newgame == 1)
		{
			t1 = SDL_GetTicks();
			worldTime = 0;
			fps = 0;
			fpsTimer = 0;
			delta = 0;
			newgame=0;  //important
			doInputflag = 1;
			while (!quit&&!newgame)//important
			{
				t2 = SDL_GetTicks();
				delta = (t2 - t1) * 0.001;
				t1 = t2;
				worldTime += delta;		
				
				SDL_FillRect(app.screen, NULL, black);
			//	DrawRectangle(app.screen, 4, 42, SCREEN_WIDTH -8, SCREEN_HEIGHT - 50 , red, gray_level1_background);//the whole background				
				DrawRectangle(app.screen, 4, 4, SCREEN_WIDTH - 8, 36, red, orange_level_2);//The toppest one

			//	DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 , (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 - GRID_SIZE, 1.6*GRID_SIZE , 0.9*GRID_SIZE, red, orange_level_2);
			//	sprintf(text, "Score!");
			//	DrawString(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2+ (1.6*GRID_SIZE)/2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 - GRID_SIZE + (0.9*GRID_SIZE)*0.25, text, app.charset);
			//	sprintf(text, "%d",totalScore);
			//	DrawString(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (1.6*GRID_SIZE) / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 - GRID_SIZE + (0.9*GRID_SIZE)*0.75, text, app.charset);
				//            "template for the second project, elapsed time = %.1lf s  %.0lf frames / s"
				myTime();
				sprintf(text, "Running time = %.1lf s ", worldTime);
				DrawString(app.screen, app.screen->w *0.75 - strlen(text) * 8 / 2, 10, text, app.charset);
				sprintf(text, value_ctime);
				DrawString(app.screen, app.screen->w *0.25 - strlen(text) * 8 / 2, 10, text, app.charset);
				//	      "Esc - exit, \030 - faster, \031 - slower"
				sprintf(text, "Esc - Quit, U - Undo, 1 - Size one, 2 - size two, 3 - Size three, 4 - Size four");
				DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, 26, text, app.charset);
				if (playDisplay == 1)
				{
					DrawRectangle(app.screen, 4, 42, SCREEN_WIDTH - 8, SCREEN_HEIGHT - 50, red, gray_level1_background);//whole background for game
					DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2, (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 - GRID_SIZE, 1.6*GRID_SIZE, 0.9*GRID_SIZE, red, gray_level2_background);
					sprintf(text, "Score!");
					DrawString(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (1.6*GRID_SIZE) / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 - GRID_SIZE + (0.9*GRID_SIZE)*0.25, text, app.charset);
					sprintf(text, "%d", totalScore);
					DrawString(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (1.6*GRID_SIZE) / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 - GRID_SIZE + (0.9*GRID_SIZE)*0.75, text, app.charset);
					for (int i = 0; i <= 3; i++) {
						for (int j = 0; j <= 3; j++)
						{
							DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_background);
							if (matrix[i][j] != 0)
							{
								if (matrix[i][j] == 2) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_1);
								if (matrix[i][j] == 4) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_2);
								if (matrix[i][j] == 8) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_3);
								if (matrix[i][j] == 16) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_4);
								if (matrix[i][j] == 32) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_5);
								if (matrix[i][j] == 64) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_6);
								if (matrix[i][j] == 128) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_7);
								if (matrix[i][j] == 256) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_8);
								if (matrix[i][j] == 512) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_9);
								if (matrix[i][j] == 1024) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_10);
								if (matrix[i][j] == 2048) DrawRectangle(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE * i), (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE * j), GRID_SIZE, GRID_SIZE, orange_level_line, orange_level_11);

								sprintf(text, "%d", matrix[i][j]);
								DrawString(app.screen, (SCREEN_WIDTH - 4 * GRID_SIZE) / 2 + (GRID_SIZE / 2) + GRID_SIZE * i - strlen(text) * 8 / 2, (SCREEN_HEIGHT - 4 * GRID_SIZE) / 2 + (GRID_SIZE / 2) + GRID_SIZE * j, text, app.charset);
																		

							}

						}

					}
				}
					
				if (isWin(matrix))
				{
					DrawRectangle(app.screen, (SCREEN_WIDTH - 180) / 2, (SCREEN_HEIGHT - 90) / 2, 180, 90, orange_level_line, orange_level_2);
					sprintf(text, "You Win!");
					DrawString(app.screen, (SCREEN_WIDTH - 180) / 2 + 90 - strlen(text) * 8 / 2, (SCREEN_HEIGHT - 90) / 2 + 45, text, app.charset);
				}
				else if (isLose(matrix))
				{
					DrawRectangle(app.screen, (SCREEN_WIDTH - 180) / 2, (SCREEN_HEIGHT - 90) / 2, 180, 90, orange_level_line, orange_level_2);
					sprintf(text, "You Lose!");
					DrawString(app.screen, (SCREEN_WIDTH - 180) / 2 + 90 - strlen(text) * 8 / 2, (SCREEN_HEIGHT - 90) / 2 + 45, text, app.charset);
				}
				if (loadMenu_flag == 1)
				{
					loadMenu();
					loadInput();
				}
				SDL_UpdateTexture(app.scrtex, NULL, app.screen->pixels, app.screen->pitch);
				//		SDL_RenderClear(renderer);
				SDL_RenderCopy(app.renderer, app.scrtex, NULL, NULL);
				SDL_RenderPresent(app.renderer);	
				if (doInputflag == 1)
				{
					doInput();
				}				
			}
		}
	}
	SDL_Quit();
	return 0;
}
