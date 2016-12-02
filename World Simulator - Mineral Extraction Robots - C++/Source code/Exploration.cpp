#include <cstdlib>
#include <iostream>
#include <sstream>

#include "Exploration.h"

using namespace std;

int explorCount = 0;

Exploration::Exploration(int _x, int _y, int _moveCount, int _damageCount, int _flag)
: Robot(_x, _y, _moveCount, _damageCount)
{
	setType(2);
	setFlags(_flag);
	explorCount++;
	string Result;          // string which will contain the result

	ostringstream convert;   // stream used for the conversion

	convert << explorCount;      // insert the textual representation of 'Number' in the characters in the stream

	Result = convert.str();
	Result = "E" + Result;
	setID(Result);
}


int Exploration::getFlags() const
{
	return flags;
}

void Exploration::setFlags(int _flags)
{
	this->flags = _flags;
}

void Exploration::leitourgia(Map * map, Ground * ground[], Base * base, vector< Robot * > robots)
{
	if ((ground[getX()][getY()].getAccessRisk() > 0.6))
	{
		map->setPositions(getX(), getY(), "FF");
		ground[getX()][getY()].setFlag(true);
		setFlags(getFlags() + 1);
		cout << "To robot " << getID() << " topothetise simaia" << endl;
	}
}

void Exploration::move(Map * map)
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

	if (getState())
	{
		return;
	}

	while (!flag)
	{
		move = 1 + rand() % 8;
		//cout << "Random E = " << move << endl;

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
				if (!map->isFlag(getX(), getY()))
				{
					map->setPositions(getX(), getY(), "00");
				}
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
				if (!map->isFlag(getX(), getY()))
				{
					map->setPositions(getX(), getY(), "00");
				}
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
				if (!map->isFlag(getX(), getY()))
				{
					map->setPositions(getX(), getY(), "00");
				}
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
				if (!map->isFlag(getX(), getY()))
				{
					map->setPositions(getX(), getY(), "00");
				}
				setX(getX() - 1);
				map->setPositions(getX(), getY(), getID());
				flag = true;
				setMoveCount(getMoveCount() + 1);
				cout << "To robot " << getID() << " metakinithike panw" << endl;
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
				if (!map->isFlag(getX(), getY()))
				{
					map->setPositions(getX(), getY(), "00");
				}
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
				if (!map->isFlag(getX(), getY()))
				{
					map->setPositions(getX(), getY(), "00");
				}
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
				if (!map->isFlag(getX(), getY()))
				{
					map->setPositions(getX(), getY(), "00");
				}
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
				if (!map->isFlag(getX(), getY()))
				{
					map->setPositions(getX(), getY(), "00");
				}
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

void Exploration::printInfo()
{
	cout << "Robot ID = " << getID() << endl;
	cout << "Katastasi = " << getState() << endl;
	cout << "Ikanotita Prosvasis = " << getAccessAbility() << endl;
	cout << "Sintetagmeni X = " << getX() << endl;
	cout << "Sintetagmeni Y = " << getY() << endl;
	cout << "Typos robot =  " << getType() << endl;
	cout << "Arithmos kinisewn = " << getMoveCount() << endl;
	cout << "Arithmos vlavewn = " << getDamageCount() << endl;
	cout << "Plithos simaiwn pou exei topothetisei = " << getFlags() << endl;
}