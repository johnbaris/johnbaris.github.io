#include <cstdlib>
#include <iostream>

using namespace std;

#include "Robot.h"

int Robot::totalDamageCount = 0; /* Arxikopoihsh static metavlitis se 0*/

Robot::Robot(int _x, int _y, int _moveCount, int _damageCount)
{
	setState(false);
	id = "00";
	setAccessAbility();
	setX(_x);
	setY(_y);
	setMoveCount(_moveCount);
	setDamageCount(_damageCount);
	setRoundDamaged(0);
}

bool Robot::getState() const
{
	return state;
}


void Robot::setState(bool _state)
{
	state = _state;
}

string Robot::getID() const
{
	return id;
}

void Robot::setID(string _id)
{
	id = _id;
}

double Robot::getAccessAbility() const
{
	return accessAbility;
}

void Robot::setAccessAbility()
{
	accessAbility = (1 + rand() % 10) / 10.0;
}


int Robot::getX() const
{
	return x;
}

void Robot::setX(int _x)
{
	x = _x;
}

int Robot::getY() const
{
	return y;
}

void Robot::setY(int _y)
{
	y = _y;
}

int Robot::getType() const
{
	return type;
}

void Robot::setType(int _type)
{
	type = _type;
}

int Robot::getMoveCount() const
{
	return moveCounter;
}

void Robot::setMoveCount(int _moveCount)
{
	moveCounter = _moveCount;
}

int Robot::getDamageCount() const
{
	return damageCounter;
}

int Robot::getTotalDamageCount()
{
	return totalDamageCount;
}

void Robot::updateTotalDamageCount(int total)
{
	totalDamageCount += total;
}

void Robot::setDamageCount(int _damageCount)
{
	damageCounter = _damageCount;
}

int Robot::getRoundDamaged() const
{
	return roundDamaged;
}

void Robot::setRoundDamaged(int _rd)
{
	roundDamaged = _rd;
}


/* Elegxos gia vlavi kinisis */
void Robot::checkForMoveDamage(Ground *ground[])
{
	if (ground[getX()][getY()].getAccessRisk()  * (1 - getAccessAbility()) >= 0.8)
	{
		setState(true);                          // Yparxei vlavi
		setDamageCount(getDamageCount() + 1);    // Auksanoume ton arithmo vlavwn
		updateTotalDamageCount(getDamageCount());
		setRoundDamaged(getRoundDamaged() + 1);  // Auksanoume tous gurous pou einai xalasmeno
		cout << "To robot " << getID() << " xalase logo metakinis" << endl;
	}
}