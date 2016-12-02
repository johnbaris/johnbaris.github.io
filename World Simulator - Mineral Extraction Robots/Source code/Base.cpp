#include "Base.h"
#include <iostream>

using namespace std;


Base::Base()
{
	setPalladium(0);
	setIridium(0);
	setPlatinum(0);
	setLimit(1000);
}

float Base::getPalladium() const
{
	return palladium;
}

void Base::setPalladium(float _palladium)
{
	palladium = _palladium;
}

float Base::getIridium() const
{
	return iridium;
}

void Base::setIridium(float _iridium)
{
	iridium = _iridium;
}

float Base::getPlatinum() const
{
	return platinum;
}

void Base::setPlatinum(float _platinum)
{
	platinum = _platinum;
}

float Base::getLimit() const
{
	return limit;
}

void Base::setLimit(float _limit)
{
	limit = _limit;
}

void Base::printInfo()
{
	cout << "Posotita palladiou =  " << getPalladium() << endl;
	cout << "Posotita iridiou = " << getIridium() << endl;
	cout << "Posotita leukoxrusou = " << getPlatinum() << endl;
	cout << "Orio vasis = " << getLimit() << endl;
}

bool Base::isFull()
{
	if (getPalladium() + getIridium() + getPlatinum() >= 990)
	{
		return true;
	}

	return false;
}