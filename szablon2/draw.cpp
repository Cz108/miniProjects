#include "draw.h"

void initScence(void)
{
	SDL_SetRenderDrawColor(app.renderer, 215, 215, 215, 255);// r,g,b,a
	SDL_RenderClear(app.renderer);
}
void showScence(void)
{
	SDL_RenderPresent(app.renderer);
}
//void drawboard(void)
//{
//	//DrawRectangle(app.screen, 300, 180, SCREEN_WIDTH - 600, SCREEN_HEIGHT - 360, red, green);
//	for (int i = 0; i <= 3; i++) {
//		for (int j = 0; j <= 3; j++)
//		{
//			DrawRectangle(app.screen, 60, 60, SCREEN_WIDTH - 120, SCREEN_HEIGHT - 120, red, green);
//			DrawRectangle(app.screen, 4, 4, SCREEN_WIDTH - 8, 36, red, blue);
//			//DrawSurface(app.screen, app.place, 300 + 15 + 30 * i, 180 + 15 + 30 * j);
//		}
//	}
//}
//void drawnumber(void)
//{	
//	for (int i = 0; i <= 3; i++) {
//		for (int j = 0; j <= 3; j++)
//		{
//			if (fillnumber(matrix))
//			{
//				sprintf(text, "%d",matrix);
//				//DrawString(app.screen, app.screen->w / 2 - strlen(text) * 8 / 2, 26, text, app.charset);
//				DrawString(app.screen, 300, 180 + 15, text, app.charset);
//			}
//			
//		}
//	}
//}