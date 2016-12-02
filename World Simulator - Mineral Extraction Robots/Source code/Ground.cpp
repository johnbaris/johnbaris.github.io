#include "Ground.h"
#include <iostream>

using namespace std;


Ground::Ground(bool flag, float accessRisk, int palladium, int iridium, int platinum)
{
	setFlag(flag);
	setAccessRisk(accessRisk);
	setPalladium(palladium);
	setIridium(iridium);
	setPlatinum(platinum);
}

Ground::Ground()
{

}

bool Ground::isFlag() const
{
	return flag;
}

void Ground::setFlag(bool flag)
{
    this->flag = flag;
}

float Ground::getAccessRisk() const
{
	return accessRisk;
}

void Ground::setAccessRisk(float accessRisk)
{
    this->accessRisk = accessRisk;
}

int Ground::getPalladium() const
{
	return palladium;
}

void Ground::setPalladium(int palladium)
{
    this->palladium = palladium;
}

int Ground::getIridium() const
{
	return iridium;
}

void Ground::setIridium(int iridium)
{
    this->iridium = iridium;
}

int Ground::getPlatinum() const
{
	return platinum;
}

void Ground::setPlatinum(int platinum)
{
    this->platinum = platinum;
}


void Ground::printInfo()
{
	cout << "Yparksi simaias = " << ( isFlag() ? "TRUE" : "FALSE") << endl;
	cout << "Epikindinotita prosvasis = " << getAccessRisk() << endl;
	cout << "Periektikotita palladiou = " << getPalladium() << endl;
	cout << "Periektikotita iridiou = " << getIridium() << endl;
	cout << "Periektikotita leukoxrusou = " << getPlatinum() << endl;
}
