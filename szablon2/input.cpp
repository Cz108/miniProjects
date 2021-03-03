#include "input.h"

int doInputflag = 1;

void loadInput(void)
{
	SDL_Event event2;
	while (SDL_PollEvent(&event2))
	{
		switch (event2.type)
		{
		case SDL_KEYDOWN:
			if (event2.key.keysym.sym == SDLK_ESCAPE) quit = 1;
			else if (event2.key.keysym.sym == SDLK_1)
			{
				doInputflag = 1;
				loadMenu_flag = 0;
				playDisplay = 1;
				load_i = 1;
				load(load_i);
			}
			else if (event2.key.keysym.sym == SDLK_2)
			{
				doInputflag = 1;
				loadMenu_flag = 0;
				playDisplay = 1;
				load_i = 2;
				load(load_i);
			}
			else if (event2.key.keysym.sym == SDLK_3)
			{
				doInputflag = 1;
				loadMenu_flag = 0;
				playDisplay = 1;
				load_i = 3;
				load(load_i);
			}
			else if (event2.key.keysym.sym == SDLK_4)
			{
				doInputflag = 1;
				loadMenu_flag = 0;
				playDisplay = 1;
				load_i = 4;
				load(load_i);
			}
			break;
		case SDL_QUIT:
			exit(0);
			break;
		default:
			break;
		}
	}

}
void doInput(void)
{
	SDL_Event event;
	while (SDL_PollEvent(&event))
	{
		switch (event.type)
		{
			case SDL_KEYDOWN: 
				if (event.key.keysym.sym == SDLK_ESCAPE) quit = 1;
				else if (event.key.keysym.sym == SDLK_n)
				{
					
					newgame = 1;
					initMatrix(matrix);
					fillnumber(matrix);	
					totalScore = 0;
					save();
					save();
					save();
					save();
					saveruntime();
					//flag = 0;
				//saveruntime();
					
				}
				else if (event.key.keysym.sym == SDLK_UP) //dui
				{
					saveruntime();
					moveUpMatrix(matrix);
					//mergeUp(matrix); //left
						mergeUp(matrix);
						totalScore = stepScore + totalScore;
						stepScore = 0;
					moveUpMatrix(matrix);
					fillnumber(matrix);
					//if (flag == 1)
					//{
						//saveruntime();
						//~flag;
					//}
					//flag = 1;
					
				//	saveruntime();
					
				}
				else if (event.key.keysym.sym == SDLK_DOWN)
				{
					saveruntime();
					moveDownMatrix(matrix);
					mergeDown(matrix); 
					totalScore = stepScore + totalScore;
					stepScore = 0;
					moveDownMatrix(matrix);
					fillnumber(matrix);
					//if (flag == 1)
					//{
						
				//		~flag;
					//}
					//saveruntime();
					
				}
				else if (event.key.keysym.sym == SDLK_LEFT) //dui
				{
					saveruntime();
					moveLeftMatrix(matrix);
					mergeLeft(matrix); //left
					totalScore = stepScore + totalScore;
					stepScore = 0;
					moveLeftMatrix(matrix);

					fillnumber(matrix);
					//if (flag == 1)
				//	{
				//		saveruntime();
					//	~flag;
				//	}
					//saveruntime();
					//saveruntime();
				}
				else if (event.key.keysym.sym == SDLK_RIGHT)
				{
					saveruntime();
					moveRightMatrix(matrix);
					mergeRight(matrix);
					totalScore = stepScore + totalScore;
					stepScore = 0;
					moveRightMatrix(matrix);
					fillnumber(matrix);
					//if (flag == 1)
					//{
					//	saveruntime();
					//	~flag;
					//}
					//saveruntime();
					//saveruntime();
				}
				else if (event.key.keysym.sym == SDLK_u)
				{
					undoing();
					//flag = 1;
				}
				else if (event.key.keysym.sym == SDLK_s)
				{
					save();
					//saveruntime();
				}
				else if (event.key.keysym.sym == SDLK_l)
				{
					loadMenu_flag = 1;
					playDisplay = 0;
					doInputflag = 0;
					//load();
					//undoing();
				}
				else if (event.key.keysym.sym == SDLK_1)
				{
					GRID_SIZE = 40;
				}
				else if (event.key.keysym.sym == SDLK_2)
				{
					GRID_SIZE = 50;
				}
				else if (event.key.keysym.sym == SDLK_3)
				{
					GRID_SIZE = 60;
				}
				else if (event.key.keysym.sym == SDLK_4)
				{
					GRID_SIZE = 65;
				}
				
				break;
			case SDL_QUIT:
				exit(0);
				break;
			default:
				break;
		}
	}

}

