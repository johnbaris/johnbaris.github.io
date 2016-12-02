#ifndef MAP_H
#define MAP_H

#include <string>

using namespace std;

/* Orizoume to megethos tou map */
#define SIZE 10

class Map
{
public:
	Map();								   /* Constructor */

	void setPositions(int, int, string);   /* Orizei tin thesi sto xarti */
	void printMap();					   /* Ektipwnei ton xarti */

	bool isEmpty(int, int);				   /* Elegxei an i sigkekrimeni thesi einai adeia */
	bool isFlag(int, int);				   /* Elegxei an yparxei simaia stin sigkekrimeni thesi */
	bool isRobot(int, int);				   /* Elegxei an yparxei robot sn sigkekrimeni thesi */

	int getSize() const;			       /* Episrefei to megethos tou map */
	int getTotalFlags();                   /* Epistrefei ton sunoliko arithmo flags */


private:
	string map[SIZE][SIZE];
    static int totalFlags;                 /* Static var pou krataei ton sunoliko arithmo simaiwn */
};

#endif