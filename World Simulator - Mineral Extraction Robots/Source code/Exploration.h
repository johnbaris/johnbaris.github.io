#ifndef EXPLORATION_H
#define EXPLORATION_H

#include "Robot.h"


class Exploration : public Robot
{
public:
	Exploration(int, int, int, int, int); /* Constructor */

	int getFlags() const;  /* Epistrefei ton arithmo twn simaiwn pou exei topothetisi to robot */
	void setFlags(int);  /* Orizei ton arithmo twn simaiwn ... */

	virtual void printInfo();
	virtual void move(Map *);
	virtual void leitourgia(Map *, Ground *[], Base *, vector< Robot * >);

private:
	int flags;            /* Plithos apo simaies pou exei topothetisei */
};

#endif