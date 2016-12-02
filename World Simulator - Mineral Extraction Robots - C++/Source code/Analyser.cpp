#include <iostream>
#include <ctime>
#include <cstdlib>
#include <string>
#include <sstream>

using namespace std;

#include "Analyser.h"

int counter = 0;
int analcount = 0;
int Analyser::totalMinedCargo = 0;


Analyser::Analyser(int _x, int _y, int _moveCount, int _damageCount, float _maxCargo, float _mineCargo,
	float _palladium, float _iridium, float _platinum, float _totalCargo, bool _minedmg)
	: Robot(_x, _y, _moveCount, _damageCount)
{
	//setState(false);
	setType(1);
	setMaxCargo(_maxCargo);
	setMineCargo(_mineCargo);
	setPalladiumCargo(_palladium);
	setIridiumCargo(_iridium);
	setPlatinumCargo(_platinum);
	setTotalCargo(_totalCargo);
	setMineDamage(_minedmg);
	analcount++;

	string Result;          // string which will contain the result

	ostringstream convert;   // stream used for the conversion

	convert << analcount;      // insert the textual representation of 'Number' in the characters in the stream

	Result = convert.str();
	Result = "A" + Result;
	setID(Result);
}

float Analyser::getMaxCargo() const
{
	return maxCargo;
}

void Analyser::setMaxCargo(float _maxCargo)
{
	maxCargo = _maxCargo;
}

float Analyser::getPalladiumCargo() const
{
	return palladiumCargo;
}

void Analyser::setPalladiumCargo(float _paladium)
{
	palladiumCargo = _paladium;
}

float Analyser::getIridiumCargo() const
{
	return iridiumCargo;
}

void Analyser::setIridiumCargo(float _iridium)
{
	iridiumCargo = _iridium;
}

float Analyser::getPlatinumCargo() const
{
	return platinumCargo;
}

void Analyser::setPlatinumCargo(float _platinum)
{
	platinumCargo = _platinum;
}


float Analyser::getMineCargo() const
{
	return mineCargo;
}

void Analyser::setMineCargo(float _mineCargo)
{
	mineCargo = _mineCargo;
}

float Analyser::getTotalCargo() const
{
	return totalCargo;
}

void Analyser::setTotalCargo(float _totalCargo)
{
	totalCargo = _totalCargo;
}

bool Analyser::isMineDamaged()
{
	return mineDamage;

}

void Analyser::setMineDamage(bool _mineDamage)
{
	mineDamage = _mineDamage;
}

int Analyser::getMaxAmount(Ground * ground[])
{
	/* Vriskoume poio sustatiko exei tin megaluteri periektikotita stin
	sigkekrimeni perioxi prokeimenou na ginei eksoruksi */

	int max = ground[getX()][getY()].getPalladium();
	int ore = 0;

	if (ground[getX()][getY()].getIridium() > max)
	{
		max = ground[getX()][getY()].getIridium();
		ore = 1;
	}

	if (ground[getX()][getY()].getPlatinum() > max)
	{
		max = ground[getX()][getY()].getPlatinum();
		ore = 2;
	}

	return ore;
}

int Analyser::getTotalMinedCargo()
{
	return totalMinedCargo;
}

void Analyser::checkForMineDamage()
{
	int perc;

	perc = 1 + rand() % 100;

	if (perc <= 10)
	{
		setMineDamage(true);
		setRoundDamaged(getRoundDamaged() + 1);
		setDamageCount(getDamageCount() + 1);  /* Auksanoume tis sunolikes vlaves p exei pathei */
		Robot::updateTotalDamageCount(getDamageCount());
		cout << "To robot " << getID() << " xalase logo eksoruksis." << endl;
	}
	else
	{
		setMineDamage(false);
	}
}

void Analyser::leitourgia(Map * map, Ground * ground[], Base * base, vector< Robot * > robots)
{
	int ore;
	float amount; /* periektikotita sustatikou */

	/* Vriskoume poio exei tin megaliteri periektikotita */
	ore = getMaxAmount(ground);

	if (ore == 0)
	{
		amount = ground[getX()][getY()].getPalladium();
	}
	else if (ore == 1)
	{
		amount = ground[getX()][getY()].getIridium();
	}
	else if (ore == 2)
	{
		amount = ground[getX()][getY()].getPlatinum();
	}


	/* An i periektikotita einai < 5 den aksizei na ginei eksoriksi */
	if (amount < 5)
	{
		return;
	}


	/* Elegxoume an exei kseperasei to megisto fortio tou */
	if (getMineCargo() + amount <= getMaxCargo())
	{
		if (ore == 0)
		{
			this->setPalladiumCargo(getPalladiumCargo() + amount); /* Auksanoume to fortio palladiou */
			ground[getX()][getY()].setPalladium(0);				   /* Midenizoume tin periektikotita palladiou tis sigkekrimenis perioxis */
			cout << "To robot " << getID() << " ekane eksoriksi : " << amount << " palladiou" << endl;
		}
		else if (ore == 1)
		{
			this->setIridiumCargo(getIridiumCargo() + amount);
			ground[getX()][getY()].setIridium(0);
			cout << "To robot " << getID() << " ekane eksoriksi : " << amount << " iridiou" << endl;
		}
		else if (ore == 2)
		{
			this->setPlatinumCargo(getPlatinumCargo() + amount);
			ground[getX()][getY()].setPlatinum(0);
			cout << "To robot " << getID() << " ekane eksoriksi : " << amount << " leukoxrusou" << endl;
		}

		checkForMineDamage();  /* Afou egine i eksoruksi, elenxoume an dimiourgithike vlavi */

		if (isMineDamaged())
		{
			setState(true);    /* An dimiourgithike thetoume tin katastasi tou ws TRUE - pou simanei vlavi */
		}

		setMineCargo(getMineCargo() + amount);
		setTotalCargo(getTotalCargo() + amount);
		totalMinedCargo += amount;
	}
	else
	{
		/* Elenxoume poia thesi gurw apo tin vasi einai adeia prokeimenou
		na metaferthei ekei kai na metaferei ta sustatika */
		// TODO : An einai gemates oles oi theseis, then what? 
		if (map->isEmpty(4, 5))
		{
			map->setPositions(getX(), getY(), "00");
			setX(4);
			setY(5);
			map->setPositions(4, 5, getID());

		}
		else if (map->isEmpty(5, 6))
		{
			map->setPositions(getX(), getY(), "00");
			setX(5);
			setY(6);
			map->setPositions(5, 6, getID());
		}
		else if (map->isEmpty(5, 4))
		{
			map->setPositions(getX(), getY(), "00");
			setX(5);
			setY(4);
			map->setPositions(5, 4, getID());
		}
		else if (map->isEmpty(6, 5))
		{
			map->setPositions(getX(), getY(), "00");
			setX(6);
			setY(5);
			map->setPositions(6, 5, getID());
		}
		else if (map->isEmpty(4, 6))
		{
			map->setPositions(getX(), getY(), "00");
			setX(4);
			setY(6);
			map->setPositions(4, 6, getID());

		}
		else if (map->isEmpty(4, 4))
		{
			map->setPositions(getX(), getY(), "00");
			setX(4);
			setY(4);
			map->setPositions(4, 4, getID());
		}
		else if (map->isEmpty(6, 4))
		{
			map->setPositions(getX(), getY(), "00");
			setX(6);
			setY(4);
			map->setPositions(6, 4, getID());
		}
		else if (map->isEmpty(6, 6))
		{
			map->setPositions(getX(), getY(), "00");
			setX(6);
			setY(6);
			map->setPositions(6, 6, getID());
		}
		setMineCargo(0);
		counter = 1;
		transfer(base);
	}
}

void Analyser::move(Map * map)
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

	/* Otan epistrepsei sti vasi den prpei n kinithei kateutheian alla na paramenei ekei gia 1 guro */
	if (counter == 1)
	{
		counter = 0;
		return;
	}

	while (!flag)
	{
		move = 1 + rand() % 8;
		//cout << "Random A = " << move << endl;

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
				cout << "To robot " << getID() << " metakinithike katw" << endl;
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
				cout << "To robot " << getID() << " metakinithike panw deksia" << endl;
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

void Analyser::transfer(Base * base)
{
	base->setPalladium(base->getPalladium() + this->getPalladiumCargo());
	base->setIridium(base->getIridium() + this->getIridiumCargo());
	base->setPlatinum(base->getPlatinum() + this->getPlatinumCargo());

	/* Midenismos twrinou fortiou afou metaferthike idi sti vasi */
	this->setPalladiumCargo(0);
	this->setIridiumCargo(0);
	this->setPlatinumCargo(0);


}

void Analyser::printInfo()
{
	cout << "Robot ID = " << getID() << endl;
	cout << "Katastasi = " << getState() << endl;
	cout << "Ikanotita Prosvasis = " << getAccessAbility() << endl;
	cout << "Sintetagmeni X = " << getX() << endl;
	cout << "Sintetagmeni Y = " << getY() << endl;
	cout << "Typos robot =  " << getType() << endl;
	cout << "Arithmos kinisewn = " << getMoveCount() << endl;
	cout << "Arithmos vlavewn = " << getDamageCount() << endl;
	cout << "Megisto Fortio = " << getMaxCargo() << endl;
	cout << "Fortio pou exei eksoruksei mexri stigmis = " << getMineCargo() << endl;
	cout << "Fortio pou exei eksoruksei sunolika afto to robot = " << getTotalCargo() << endl;

}