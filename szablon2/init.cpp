#include "init.h"

void initMenu(void)
{
	sprintf(text, "2048");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT * 2 / 11), text, app.charset);
	sprintf(text, "Press ARROWS to move in the direction");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT * 4 / 11), text, app.charset);
	sprintf(text, "Press 'n' to NEW the GAME");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT * 5 / 11), text, app.charset);
	sprintf(text, "Press 'Esc' to play Escape");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, (SCREEN_HEIGHT * 6 / 11), text, app.charset);
	sprintf(text, "s - save game, l - load game, 1 - Size one, 2 - size two, 3 - Size three, 4 - Size four");
	DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, 26, text, app.charset);
	SDL_UpdateTexture(app.scrtex, NULL, app.screen->pixels, app.screen->pitch);
	SDL_RenderCopy(app.renderer, app.scrtex, NULL, NULL);
	SDL_RenderPresent(app.renderer);
}

void initSDL(void)
{
	int rc;

	if (SDL_Init(SDL_INIT_EVERYTHING) != 0) {
		printf("SDL_Init error: %s\n", SDL_GetError());
		exit(1);
	}

	rc = SDL_CreateWindowAndRenderer(SCREEN_WIDTH, SCREEN_HEIGHT, 0, &app.window, &app.renderer);
	if (rc != 0) {
		SDL_Quit();
		printf("SDL_CreateWindowAndRenderer error: %s\n", SDL_GetError());
		exit(1);
	};

	SDL_SetHint(SDL_HINT_RENDER_SCALE_QUALITY, "linear");
	SDL_RenderSetLogicalSize(app.renderer, SCREEN_WIDTH, SCREEN_HEIGHT);
	SDL_SetRenderDrawColor(app.renderer, 0, 0, 0, 255);
	SDL_SetWindowTitle(app.window, "Project 2 - Hang Liu s179216");
	app.screen = SDL_CreateRGBSurface(0, SCREEN_WIDTH, SCREEN_HEIGHT, 32, 0x00FF0000, 0x0000FF00, 0x000000FF, 0xFF000000);
	app.scrtex = SDL_CreateTexture(app.renderer, SDL_PIXELFORMAT_ARGB8888, SDL_TEXTUREACCESS_STREAMING, SCREEN_WIDTH, SCREEN_HEIGHT);

}
int initBMP(void)
{
	app.charset = SDL_LoadBMP("./cs8x8.bmp");
	if (app.charset == NULL) {
		printf("SDL_LoadBMP(cs8x8.bmp) error: %s\n", SDL_GetError());
		SDL_FreeSurface(app.screen);
		SDL_DestroyTexture(app.scrtex);
		SDL_DestroyWindow(app.window);
		SDL_DestroyRenderer(app.renderer);
		SDL_Quit();
		return 1;
	};
	SDL_SetColorKey(app.charset, true, 0x000000);

	app.place = SDL_LoadBMP("./BMP/point.bmp");
	if (app.place == NULL) {
		printf("SDL_LoadBMP(point.bmp) error: %s\n", SDL_GetError());
		SDL_FreeSurface(app.screen);
		SDL_DestroyTexture(app.scrtex);
		SDL_DestroyWindow(app.window);
		SDL_DestroyRenderer(app.renderer);
		SDL_Quit();
		return 1;
	}
	SDL_SetColorKey(app.charset, true, 0x000000);


};

void cleanup(void)
{
	SDL_DestroyRenderer(app.renderer);

	SDL_DestroyWindow(app.window);

	SDL_Quit();
}
void initMatrix(int matrix[4][4])
{
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4; j++)
		{
			matrix[i][j] = 0;
		}
	}
}

COORDINATE findRandomCord(int matrix[4][4])
{
	int length = 0;
	//int empty[2];
	//struct COORDINATE *pointer;
	struct COORDINATE temp[17];
	memset(&temp, 0, sizeof(temp));
	temp[0].x = 100;  //the other values means no free place
	temp[0].y = 100;
	//pointer = &temp[0];
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4; j++)
		{
			if (matrix[i][j] == 0)
			{
				length++;
				temp[length].x = i;  //start from temp[1]
				temp[length].y = j;
			}
		}
	}
	if (length == 0) return temp[0]; //means no free place
	else if (length == 1) 
	{ 		
		//pointer = &temp[1];
		return temp[1];
	}//other otions mean that we have more than 1 free place
	else {
		int placechoice = 1 + rand() % length; //so we choose a random value in the range of length.(from 1 to length)
		return temp[placechoice];
	}
}


int random_x = 0, random_y = 0;
bool fillnumber(int matrix[4][4])
{
	struct COORDINATE item[1];
	memset(&item, 0, sizeof(item));
	item[0] = findRandomCord(matrix);
	if (item[0].x <= 4)
	{		
		random_x = item[0].x;
		random_y = item[0].y;
		matrix[random_x][random_y] = 2;
		matrix_value = matrix[random_x][random_y];		
		return true;
	}
	else
		return false;
}
bool canGoLeft(int matrix[4][4])
{
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4-1; j++)
		{
			if (matrix[i][j] > 0 && matrix[i][j] == matrix[i][j + 1]) return true;			
			if (matrix[i][j] == 0 && matrix[i][j + 1] > 0) return true;			
		}
	}
	return false;
}
bool canGoRight(int matrix[4][4])
{
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4 - 1; j++)
		{
			if (matrix[i][j] > 0 && matrix[i][j] == matrix[i][j + 1]) return true;
			if (matrix[i][j] > 0 && matrix[i][j + 1] == 0) return true;
		}
	}
	return false;
}
bool canGoUp(int matrix[4][4])
{
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4 - 1; j++)
		{
			if (matrix[j][i] > 0 && matrix[j][i] == matrix[j + 1][i]) return true;
			if (matrix[j][i] == 0 && matrix[j + 1][i] > 0) return true;			
		}
	}
	return false;
}
bool canGoDown(int matrix[4][4])
{
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4 - 1; j++)
		{
			if (matrix[j][i] > 0 && matrix[j][i] == matrix[j + 1][i]) return true;
			if (matrix[j][i] > 0 && matrix[j + 1][i] == 0) return true;
		}
	}
	return false;
}
int getfilledinfolength;
ALLINFO* getFilledInfo(int matrix[4][4]) //matrix[0204] -> filled[0]=2 filled[1]=4 //have location info
{
	getfilledinfolength = 0;
	struct ALLINFO filled[16];
	memset(&filled, 0, sizeof(filled));
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4; j++)
		{
			if (matrix[i][j] > 0)
			{
				filled[getfilledinfolength].x = i;  //start from temp[1]
				filled[getfilledinfolength].y = j;
				filled[getfilledinfolength].value = matrix[i][j];
				getfilledinfolength++;
			}
		}
	}
	return filled;
}
// go to left or right we find columns; for[i][j], we choose j
//go to up or down we find rows;  i;
int move_x; int move_y;

void moveUpMatrix(int matrix[4][4])  //works
{
	struct ALLINFO *filled;
	filled = (ALLINFO*)malloc(sizeof(ALLINFO));
	//memset(&filled, 0, sizeof(filled));
	struct ALLINFO columns[5];
	memset(&columns, 0, sizeof(columns));
	filled = getFilledInfo(matrix);
	int length = 0;
	for(int i=0;i<4;i++)
	{
		for (int m = 0; m < getfilledinfolength; m++)
		{
			if (filled[m].x == i)
			{
				columns[length] = filled[m];
				length++;
			}
		}
		for(int j=0;j<length;j++)
		{

				matrix[columns[j].x][columns[j].y] = 0;
				matrix[i][j] = columns[j].value;	
		}	
		length = 0;
	}
}
void moveDownMatrix(int matrix[4][4])
{
	struct ALLINFO *filled;
	filled = (ALLINFO*)malloc(sizeof(ALLINFO));
	//memset(&filled, 0, sizeof(filled));
	struct ALLINFO columns[5];
	memset(&columns, 0, sizeof(columns));
	filled = getFilledInfo(matrix);
	int length = 0;
	for (int i = 0; i<4; i++)
	{
		for (int m = 0; m < getfilledinfolength; m++)
		{
			if (filled[m].x == i)
			{
				columns[length] = filled[m];
				length++;
			}
		}
		for (int j = 0; j<length; j++)
		{

			matrix[columns[length - j - 1 ].x][columns[length - j - 1].y] = 0;
			matrix[i][4 - j - 1] = columns[length - j - 1].value;
		}
		length = 0;
	}
}
void moveLeftMatrix(int matrix[4][4])
{
	struct ALLINFO *filled;
	filled = (ALLINFO*)malloc(sizeof(ALLINFO));
	//memset(&filled, 0, sizeof(filled));
	struct ALLINFO columns[5];
	memset(&columns, 0, sizeof(columns));
	filled = getFilledInfo(matrix);
	int length = 0;
	for (int i = 0; i<4; i++)
	{
		for (int m = 0; m < getfilledinfolength; m++)
		{
			if (filled[m].y == i)
			{
				columns[length] = filled[m];
				length++;
			}
		}
		for (int j = 0; j<length; j++)
		{

			matrix[columns[j].x][columns[j].y] = 0;
			matrix[j][i] = columns[j].value;
		}
		length = 0;
	}
}
void moveRightMatrix(int matrix[4][4])
{
	struct ALLINFO *filled;
	filled = (ALLINFO*)malloc(sizeof(ALLINFO));
	//memset(&filled, 0, sizeof(filled));
	struct ALLINFO columns[5];
	memset(&columns, 0, sizeof(columns));
	filled = getFilledInfo(matrix);
	int length = 0;
	for (int i = 0; i<4; i++)
	{
		for (int m = 0; m < getfilledinfolength; m++)
		{
			if (filled[m].y == i)
			{
				columns[length] = filled[m];
				length++;
			}
		}
		for (int j = 0; j<length; j++)
		{

			matrix[columns[length - j - 1].x][columns[length - j -1 ].y] = 0;
			matrix[4-j-1][i] = columns[length-j-1].value;
		}
		length = 0;
	}
}
int stepScore = 0;
void mergeLeft(int matrix[4][4])
{
	
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4 - 1; j++)
		{
			if (matrix[j][i] > 0 && matrix[j][i] == matrix[j + 1][i])
			{
				matrix[j][i] *= 2;
				stepScore += matrix[j][i];
				matrix[j + 1][i] = 0;
			}
		}
	}

}
void mergeRight(int matrix[4][4])
{
	
	for (int i = 0; i < 4; i++)
	{
		for (int j = 4 - 1; j >0; j--)
		{
			if (matrix[j][i] > 0 && matrix[j][i] == matrix[j - 1][i])
			{
				matrix[j][i] *= 2;
				stepScore += matrix[j][i];
				matrix[j - 1][i] = 0;
			}
		}
	}
}
void mergeUp(int matrix[4][4])
{

	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4 - 1; j++)
		{
			if (matrix[i][j] > 0 && matrix[i][j] == matrix[i][j + 1])
			{
				matrix[i][j] *= 2;
				stepScore += matrix[i][j];
				matrix[i][j + 1] = 0;
			}
		}
	}
}
void mergeDown(int matrix[4][4])
{
	
	for (int i = 0; i < 4; i++)
	{
		for (int j = 4 - 1; j >0; j--)
		{
			if (matrix[i][j] > 0 && matrix[i][j] == matrix[i][j - 1])
			{
				matrix[i][j] *= 2;
				stepScore += matrix[i][j];
				matrix[i][j - 1] = 0;
			}
		}
	}
}