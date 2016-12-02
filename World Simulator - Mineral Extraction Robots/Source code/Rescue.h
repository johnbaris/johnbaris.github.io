#ifndef RESCUE_H
#define RESCUE_H

#include "Robot.h"
#include "Map.h"
#include "Base.h"

class Rescue : public Robot
{
public:
	Rescue(int, int, int, int, int);

	int getRepairs() const;  /* Epistrefei ton arithmo ton epidiorthwsewn pou exei kanei to robot */
	void setRepairs(int);  /* Orizei ton arithmo ... */

	virtual void printInfo();
	virtual void move(Map *);
	virtual void leitourgia(Map *, Ground *[], Base * base, vector< Robot * >);
private:
	int repairs;             /* Posa oximata exei epidiorthwsei */
};

#endif