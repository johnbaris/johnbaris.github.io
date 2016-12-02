#include <iostream>
#include <string>
#include "Map.h"

using namespace std;


int Map::totalFlags = 0; /* Arxikopoihsh static metavlitis totalFlags */

Map::Map()
{
    // Arxikopoihsh xarti. Dilwnoume 00 gia tis kenes theseis
	for (int i = 0; i < SIZE; i++)
	{
		for (int j = 0; j < SIZE; j++)
		{
			map[i][j] = "00";
		}
	}
}

void Map::setPositions(int x, int y, string id)
{
	map[x][y] = id;
}

/* Epistrefei ton sunoliko arithmo flags */
int Map::getTotalFlags()
{
	for (int i = 0; i < SIZE; i++)
	{
		for (int j = 0; j < SIZE; j++)
		{
			if (map[i][j] == "FF")
			{
				totalFlags++;
			}
		}
	}

	return totalFlags;
}


/* Ektupwnei ton xarti */
void Map::printMap()
{
	for (int i = 0; i < SIZE; i++)
	{
		for (int j = 0; j < SIZE; j++)
		{
			cout << map[i][j] << " ";
		}
        cout << endl;
	}
    cout << endl;
}

/* Elegxei an i thesi x, y einai adeia*/
bool Map::isEmpty(int x, int y)
{

	if (map[x][y].compare(0, 2, "00") == 0)
	{
		return true;
	}


	return false;
}

/* Elegxei an yparxei robot stin thesi x, y */
bool Map::isRobot(int x, int y)
{
	if ((map[x][y].substr(0, 1) == "A") || (map[x][y].substr(0, 1) == "E") || (map[x][y].substr(0, 1) == "R"))
	{
		return true;
	}

	return false;
}


int Map::getSize() const
{
	return SIZE;
}

/* Elegxei an yparxei flag stin thesi x,y */
bool Map::isFlag(int x, int y)
{
	if (map[x][y] == "FF")
	{
		return true;
	}

	return false;
}