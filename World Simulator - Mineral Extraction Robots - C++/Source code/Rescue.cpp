#include <cstdlib>
#include <iostream>
#include <sstream>

#include "Rescue.h"
#include "Analyser.h"

using namespace std;

int rescueCount = 0;

vector< Robot * > robots;

Rescue::Rescue(int _x, int _y, int _moveCount, int _damageCount, int _repairs)
: Robot(_x, _y, _moveCount, _damageCount)
{
	setType(3);
	setRepairs(_repairs);
	rescueCount++;
	string Result;         

	ostringstream convert;  

	convert << rescueCount;    

	Result = convert.str();
	Result = "R" + Result;
	setID(Result);
}


int Rescue::getRepairs() const
{
	return repairs;
}

void Rescue::setRepairs(int _repairs)
{
	repairs = _repairs;
}

void Rescue::leitourgia(Map * map, Ground * ground[], Base * base, vector< Robot * > robot)
{
	// 1 - aristera
	// 2 - deksia
	// 3 - katw
	// 4 - panw
	// 5 - panw aristera
	// 6 - panw deksia
	// 7 - katw aristera
	// 8 - katw deksia


	if (!(getY() - 1 < 0) || !(getY() + 1 >= map->getSize()) || !(getX() - 1 < 0) || !(getX() + 1 >= map->getSize()))
	{
		for (int i = 0; i < robots.size(); i++)
		{
			// aristera
			if (robots[i]->getX() == getX() && (robots[i]->getY() == getY() - 1) && robots[i]->getState())
			{
				cout << "To robot " << getID() << " epidiorthwse to " << robots[i]->getID() << endl;
				robots[i]->setState(false);
				cout << robots[i]->getState() << endl;
				setRepairs(getRepairs() + 1);
				robots[i]->setRoundDamaged(0);
				break;
			}
			// deksia
			else if (robots[i]->getX() == getX() && (robots[i]->getY() == getY() + 1) && robots[i]->getState())
			{
				cout << "To robot " << getID() << " epidiorthwse to " << robots[i]->getID() << endl;
				robots[i]->setState(false);
				cout << robots[i]->getState() << endl;
				setRepairs(getRepairs() + 1);
				robots[i]->setRoundDamaged(0);
				break;
			}
			// katw
			else if ((robots[i]->getX() == getX() + 1) && robots[i]->getY() == getY() && robots[i]->getState())
			{
				cout << "To robot " << getID() << " epidiorthwse to " << robots[i]->getID() << endl;
				robots[i]->setState(false);
				cout << robots[i]->getState() << endl;
				setRepairs(getRepairs() + 1);
				robots[i]->setRoundDamaged(0);
				break;
			}
			// panw
			else if ((robots[i]->getX() == getX() - 1) && robots[i]->getY() == getY() && robots[i]->getState())
			{
				cout << "To robot " << getID() << " epidiorthwse to " << robots[i]->getID() << endl;
				robots[i]->setState(false);
				cout << robots[i]->getState() << endl;
				setRepairs(getRepairs() + 1);
				robots[i]->setRoundDamaged(0);
				break;
			}
			// panw aristera
			else if ((robots[i]->getX() == getX() - 1) && (robots[i]->getY() == getY() - 1) && robots[i]->getState())
			{
				cout << "To robot " << getID() << " epidiorthwse to " << robots[i]->getID() << endl;
				robots[i]->setState(false);
				setRepairs(getRepairs() + 1);
				robots[i]->setRoundDamaged(0);
				break;
			}
			// panw deksia
			else if ((robots[i]->getX() == getX() - 1) && (robots[i]->getY() == getY() + 1) && robots[i]->getState())
			{
				cout << "To robot " << getID() << " epidiorthwse to " << robots[i]->getID() << endl;
				robots[i]->setState(false);
				cout << robots[i]->getState() << endl;
				setRepairs(getRepairs() + 1);
				robots[i]->setRoundDamaged(0);
				break;
			}
			// katw aristera
			else if ((robots[i]->getX() == getX() + 1) && (robots[i]->getY() == getY() - 1) && robots[i]->getState())
			{
				cout << "To robot " << getID() << " epidiorthwse to " << robots[i]->getID() << endl;
				robots[i]->setState(false);
				cout << robots[i]->getState() << endl;
				setRepairs(getRepairs() + 1);
				robots[i]->setRoundDamaged(0);
				break;
			}
			// katw deksia
			else if ((robots[i]->getX() == getX() + 1) && (robots[i]->getY() == getY() + 1) && robots[i]->getState())
			{
				cout << "To robot " << getID() << " epidiorthwse to " << robots[i]->getID() << endl;
				robots[i]->setState(false);
				cout << robots[i]->getState() << endl;
				setRepairs(getRepairs() + 1);
				robots[i]->setRoundDamaged(0);
				break;
			}
		}
	}
}

void Rescue::move(Map * map)
{
	int move;
	bool flag = false;


	// 1 - aristera
	// 2 - deksia
	// 3 - katw
	// 4 - panw
	// 5 - panw aristera
	// 6 - panw deksia
	// 7 - katw aristera
	// 8 - katw deksia

	while (!flag)
	{
		move = 1 + rand() % 8;
		//cout << "Random R = " << move << endl;

		if (move == 1)
		{
			//elegxos gia ektos oriwn 
			if ((getY() - 1 < 0) || (!map->isEmpty(getX(), getY() - 1))
				|| (map->isFlag(getX(), getY() - 1)))
			{
				continue;
			}
			else
			{
				map->setPositions(getX(), getY(), "00");
				setY(getY() - 1);
				map->setPositions(getX(), getY(), getID());
				flag = true;
				setMoveCount(getMoveCount() + 1);
				cout << "To robot " << getID() << " metakinithike aristera " << endl;
			}
		}
		else if (move == 2)
		{
			if ((getY() + 1 >= map->getSize()) || (!map->isEmpty(getX(), getY() + 1))
				|| (map->isFlag(getX(), getY() + 1)))
			{
				continue;
			}
			else
			{
				map->setPositions(getX(), getY(), "00");
				setY(getY() + 1);
				map->setPositions(getX(), getY(), getID());
				flag = true;
				setMoveCount(getMoveCount() + 1);
				cout << "To robot " << getID() << " metakinithike deksia " << endl;
			}
		}
		else if (move == 3)
		{
			if ((getX() + 1 >= map->getSize()) || (!map->isEmpty(getX() + 1, getY()))
				|| (map->isFlag(getX() + 1, getY())))
			{
				continue;
			}
			else
			{
				map->setPositions(getX(), getY(), "00");
				setX(getX() + 1);
				map->setPositions(getX(), getY(), getID());
				flag = true;
				setMoveCount(getMoveCount() + 1);
				cout << "To robot " << getID() << " metakinithike katw " << endl;
			}
		}
		else if (move == 4)
		{
			if ((getX() - 1 < 0) || (!map->isEmpty(getX() - 1, getY()))
				|| (map->isFlag(getX() - 1, getY())))
			{
				continue;
			}
			else
			{
				map->setPositions(getX(), getY(), "00");
				setX(getX() - 1);
				map->setPositions(getX(), getY(), getID());
				flag = true;
				setMoveCount(getMoveCount() + 1);
				cout << "To robot " << getID() << " metakinithike panw " << endl;
			}
		}
		else if (move == 5)
		{
			if ((getX() - 1 < 0) || (getY() - 1 < 0) || (!map->isEmpty(getX() - 1, getY() - 1))
				|| (map->isFlag(getX() - 1, getY() - 1)))
			{
				continue;
			}
			else
			{
				map->setPositions(getX(), getY(), "00");
				setX(getX() - 1);
				setY(getY() - 1);
				map->setPositions(getX(), getY(), getID());
				flag = true;
				setMoveCount(getMoveCount() + 1);
				cout << "To robot " << getID() << " metakinithike panw aristera " << endl;
			}
		}
		else if (move == 6)
		{
			if ((getX() - 1 < 0) || (getY() + 1 >= map->getSize()) || (!map->isEmpty(getX() - 1, getY() + 1))
				|| (map->isFlag(getX() - 1, getY() + 1)))
			{
				continue;
			}
			else
			{
				map->setPositions(getX(), getY(), "00");
				setX(getX() - 1);
				setY(getY() + 1);
				map->setPositions(getX(), getY(), getID());
				flag = true;
				setMoveCount(getMoveCount() + 1);
				cout << "To robot " << getID() << " metakinithike panw deksia " << endl;
			}
		}
		else if (move == 7)
		{
			if ((getX() + 1 >= map->getSize()) || (getY() - 1 < 0) || (!map->isEmpty(getX() + 1, getY() - 1))
				|| (map->isFlag(getX() + 1, getY() - 1)))
			{
				continue;
			}
			else
			{
				map->setPositions(getX(), getY(), "00");
				setX(getX() + 1);
				setY(getY() - 1);
				map->setPositions(getX(), getY(), getID());
				flag = true;
				setMoveCount(getMoveCount() + 1);
				cout << "To robot " << getID() << " metakinithike katw aristera " << endl;
			}
		}
		else if (move == 8)
		{
			if ((getX() + 1 >= map->getSize()) || (getY() + 1 >= map->getSize()) || (!map->isEmpty(getX() + 1, getY() + 1))
				|| (map->isFlag(getX() + 1, getY() + 1)))
			{
				continue;
			}
			else
			{
				map->setPositions(getX(), getY(), "00");
				setX(getX() + 1);
				setY(getY() + 1);
				map->setPositions(getX(), getY(), getID());
				flag = true;
				setMoveCount(getMoveCount() + 1);
				cout << "To robot " << getID() << " metakinithike katw deksia " << endl;
			}
		}
	}
}

void Rescue::printInfo()
{
	cout << "Robot ID = " << getID() << endl;
	cout << "Katastasi = " << getState() << endl;
	cout << "Ikanotita Prosvasis = " << getAccessAbility() << endl;
	cout << "Sintetagmeni X = " << getX() << endl;
	cout << "Sintetagmeni Y = " << getY() << endl;
	cout << "Typos robot =  " << getType() << endl;
	cout << "Arithmos kinisewn = " << getMoveCount() << endl;
	cout << "Arithmos vlavewn = " << getDamageCount() << endl;
	cout << "Episkeues = " << getRepairs() << endl;
}