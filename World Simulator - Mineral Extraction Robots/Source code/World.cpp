#include <stdlib.h>
#include <cstdlib>
#include <ctime>
#include <iostream>
#include <windows.h>
#include <conio.h>
#include <dos.h>
#include <algorithm>


#include "World.h"
#include "Robot.h"
#include "Analyser.h"
#include "Exploration.h"
#include "Rescue.h"
#include "Map.h"
#include "Ground.h"
#include "Base.h"

#include <vector>

using namespace std;


void World::init()
{
	// Dimiourgia tuxaiwn arithmwn
	srand(time(0));

	// Dimiourgia antikeimenou xarti
	Map *map = new Map();

	// Dimiourgia antikeimenou vasis
	Base *base = new Base();

	// Topothetisi vasis ston xarti, stin thesi 5,5
	map->setPositions(5, 5, "BA");

	// Dimiourgia pinaka edafous
	Ground **ground = new Ground*[10];

	// Arxikopoihsh pinaka edafous
	initGroundMap(ground);

	// Dimiourgia - arxikopoihsh robot vector
	initVector(map);

	// Topothetisi robot ston xarti
	placeToMap(map);

	// Ksekinaei i prosomoiwsi
	start(map, ground, base);
}

void World::start(Map * map, Ground *ground[], Base * base)
{
    // Ektupwnei arxikes theseis ton robot
	map->printMap();

    // Loop prosomoisis
	while (true)   
	{
		cout << "==========================================================" << endl;

        // Elegxoume an ola ta robot exoun xalasei
		if (checkRobots()) 
		{
			cout << "I prosomiwsi termatizetai epidi ola ta robot xalasan" << endl;
			break;
		}

        // Elegxoume an i vasi gemise
		if (base->isFull())
		{
			cout << "I prosomoiwsi termatizetai epitixws. I vasi GEMISE" << endl;
			break;
		}

        // Kalountai oi sinartiseis leitourgias,kinisis kathe robot
		action(map, ground, base);

        // Ektupwnoume tis nees theseis
		map->printMap();

        // Elegxos an yparxei xalasmeno robot gia >=15 gurous
		checkDamagedRobots(map); 
		cout << "==========================================================" << endl;
		Sleep(2500);


		if (_kbhit())
		{
			menu(ground, map, base);
		}
	}
}


void World::checkDamagedRobots(Map * map)
{
	for (int i = 0; i < robots.size(); i++)
	{
		if (robots[i]->getRoundDamaged() >= 15)
		{
			// Aposuroume to robot apo ton xarti
			cout << "To robot " << robots[i]->getID() << " svistike apo ton xarti epidi itan anenergo gia >= 15 gurous" << endl;
			map->setPositions(robots[i]->getX(), robots[i]->getY(), "00");
			robots.erase(robots.begin() + i);
		}
	}
}

/* Elegxei an ola ta robot exoun xalasei */
bool World::checkRobots()
{
    // An to counter == robots.size() simainei oti ola ta robot xalasane opote epistrefoume true
	int counter = 0;

	for (int i = 0; i < robots.size(); i++)
	{
		if (robots[i]->getState())
		{
			counter++;
		}
	}

	if (counter == robots.size())
	{
		return true;
	}

	return false;
}

/* Kalei tis sinartiseis leitourgias,kinisis kathe robot */
void World::action(Map * map, Ground * ground[], Base * base)
{
	for (int i = 0; i < robots.size(); i++)
	{
        /* Ean to robot dn einai xalasmeno kaloume tis sinartiseis leitourgias,kinisis
         * alliws auksanoume ton metriti pou apothikeuoume gia posous gurous einai xalasmeno
         * kathe robot
         */
 		if (!robots[i]->getState())
		{
			robots[i]->leitourgia(map, ground, base, robots); // Ektelesi leitourgias
			robots[i]->move(map);                             // Ektelesi kiniss
			robots[i]->checkForMoveDamage(ground);            // Elegxos gia vlavi kinisis
		}
		else
		{
			robots[i]->setRoundDamaged(robots[i]->getRoundDamaged() + 1);
		}
		cout << "To robot " << robots[i]->getID() << " exei  state " << robots[i]->getState() << endl;
	}
	cout << endl;
}

void World::menu(Ground * ground[], Map * map, Base * base)
{
	int choice;

	cout << "Ti theleis na kaneis?" << endl;
	cout << "1 - Prosthiki neou oximatos" << endl;
	cout << "2 - Epeksergasia mias thesis edafous" << endl;
	cout << "3 - Eisagwgi katastasis vlavis oximatos" << endl;
	cout << "4 - Emfanisi genikwn pliroforiwn" << endl;
	cout << "5 - Sunexisi prosomoiwsis" << endl;
	cout << "---> : ";

	cin >> choice;

	if (choice == 1)
	{
		addRobot();
	}
	else if (choice == 2)
	{
		groundEdit(ground, map);
	}
	else if (choice == 3)
	{
		changeState(map);
	}
	else if (choice == 4)
	{
		displayInfo(ground, map, base);
	}
	else
	{
		// Sunexisi prosomoiwsis
	}
}

void World::displayInfo(Ground *ground[], Map * map, Base * base)
{
	int choice;

	cout << "1 - Sunoliko arithmo vlavwn" << endl;
	cout << "2 - Sunoliko arithmo simaiwn kindinou" << endl;
	cout << "3 - Sunoliko fortio pou exei ginei eksoruksi" << endl;
	cout << "4 - Plirofories gia kapoio oxima" << endl;
	cout << "5 - Plirofories gia kapoia thesi edafous" << endl;
	cout << "6 - Meso oro epikindininotitas edafous" << endl;
	cout << "7 - Sunoliki posotita kathe stoixeiou sto xarti" << endl;
	cout << "8 - Emfanisi stoixeiwn vasis" << endl;
	cin >> choice;

	if (choice == 1)
	{
		cout << "O sunolikos arithmos vlavwn einai " << robots[0]->getTotalDamageCount() << endl;
	}
	else if (choice == 2)
	{
		cout << "O sunolikos arithmos simaiwn kindinou einai " << map->getTotalFlags() << endl;
	}
	else if (choice == 3)
	{
		cout << "To sunoliko eksoruxthen fortio einai " << Analyser::getTotalMinedCargo() << endl;
	}
	else if (choice == 4)
	{
		robotInfo();
	}
	else if (choice == 5)
	{
		groundInfo(ground, map);
	}
	else if (choice == 6)
	{
		cout << "O mesos oros epikindinotitas edafous einai " << averageAccessRisk(ground) << endl;
	}
	else if (choice == 7)
	{
		totalOreAmount(ground);
	}
	else if (choice == 8)
	{
		base->printInfo();
	}
}

void World::totalOreAmount(Ground *ground[])
{
	int palladium = 0;
	int iridium = 0;
	int platinum = 0;

	for (int i = 0; i < 10; i++)
	{
		for (int j = 0; j < 10; j++)
		{
			palladium += ground[i][j].getPalladium();
			iridium += ground[i][j].getIridium();
			platinum += ground[i][j].getPlatinum();
		}
	}

	cout << "I sunoliki periektikotita palladiou einai " << palladium << endl;
	cout << "I sunoliki periektikotita iridiou einai " << iridium << endl;
	cout << "I sunoliki periektikotita leukoxrusou einai " << platinum << endl;
}

/* Ypologizei ton meso oro epikindinotitas edafous */
float World::averageAccessRisk(Ground *ground[])
{
	float total = 0;

	for (int i = 0; i < 10; i++)
	{
		for (int j = 0; j < 10; j++)
		{
			total += ground[i][j].getAccessRisk();
		}
	}

	return (total / 100.0);
}

/* Emfanizei pliroforis gia kapoia sigkekrimeni thesi edafous */
void World::groundInfo(Ground *ground[], Map * map)
{
	int x;
	int y;

	cout << "Dwse sintetagmenes tis perioxis pou thes na deis ta stoixeia" << endl;
	cout << "X = ";
	cin >> x;
	cout << "Y = ";
	cin >> y;

	if ((x >= 0 || x < map->getSize()) && (y >= 0 || y < map->getSize()))
	{
		ground[x][y].printInfo();
	}
	else
	{
		cout << "Oi sintetagmenes den einai egkures" << endl;
	}
}

/* Emfanizei plirofories gia kapoio robot */
void World::robotInfo()
{
	string robot;
    int pos = -1;
	int x;
	int y;

	cout << "Pianou robot theleis na emfaniseis tis plirofories?" << endl;
	cout << "---> : ";
	cin >> robot;

    // Metatropi se uppercase
    for (int i = 0; i < robot.length(); i++)
    {
        robot[i] = toupper(robot[i]);
    }

	for( int i = 0; i < robots.size(); i++ )
	{
		if ( robots[i]->getID() == robot )
		{
			x = robots[i]->getX();
			y = robots[i]->getY();
			pos = i;
			break;
		}

	}

	if (pos == -1)
	{
		cout << "To sigkekrimeno robot den yparxei. I prosomoiwsi sunexizetai..." << endl;
		return;
	}

	robots[pos]->printInfo();
}

/* Allazei tin katastasi enos robot */
void World::changeState(Map * map)
{
	string robot;
	int x;
	int y;
	int pos = -1;
	int choice;

	cout << "Se poio robot theleis na allakseis tin katastasi?" << endl;
	cout << "---> : ";
	cin >> robot;

    // Metatropi se uppercase
    for (int i = 0; i < robot.length(); i++)
    {
        robot[i] = toupper(robot[i]);
    }

	for (int i = 0; i < robots.size(); i++)
	{
		if (robots[i]->getID() == robot)
		{
			x = robots[i]->getX();
			y = robots[i]->getY();
			pos = i;
			break;
		}
	}

	if (pos == -1)
	{
		cout << "To sigkekrimeno robot den yparxei. I prosomoiwsi sunexizetai..." << endl;
		return;
	}

	cout << "Theleis na to epidiorthoseis H na to xalaseis?" << endl;
	cout << "1 - Epidiorthwsi" << endl;
	cout << "2 - Vlavi" << endl;
	cin >> choice;

	if (choice == 1)
	{
		if (!robots[pos]->getState())
		{
			cout << "To robot " << robots[pos]->getID() << " den einai xalasmeno." << endl;
			return;
		}
		robots[pos]->setState(false);
        robots[pos]->setRoundDamaged(0);
		cout << "To robot " << robots[pos]->getID() << " epidiorthwthike" << endl;
	}
	else if (choice == 2)
	{
		if (robots[pos]->getState())
		{
			cout << "To robot " << robots[pos]->getID() << " einai idi xalasmeno. " << endl;
			return;
		}
		robots[pos]->setState(true);
		robots[pos]->setDamageCount(robots[pos]->getDamageCount() + 1);
		cout << "To robot " << robots[pos]->getID() << " epathe vlavi" << endl;
	}
	else
	{
		cout << "I epilogi pou zitisate den ufistatai. I prosomoiwsi sunexizetai kanonika" << endl;
	}
}

/* Epeksergazetai mia sigkekrimeni thesi edafous */
void World::groundEdit(Ground *ground[], Map * map)
{
	float accessRisk;
	int choice;
	int x;
	int y;
	int palladium;
	int iridium;
	int platinum;

	cout << "Ti theleis na epeksergasteis?" << endl;
	cout << "1 - Topothetisi simaias" << endl;
	cout << "2 - Epikindinotita prosvasis" << endl;
	cout << "3 - Palladio" << endl;
	cout << "4 - Iridiou" << endl;
	cout << "5 - Leukoxruso" << endl;

	cin >> choice;
	cout << "X coordinate = ";
	cin >> x;
	cout << "Y coordinate = ";
	cin >> y;

	if (choice == 1)
	{
		if (map->isEmpty(x, y))
		{
			ground[x][y].setFlag(true);
			map->setPositions(x, y, "FF");
		}
		else
		{
			cout << "Den mporei na topothetithei simaia stin sigkekrimeni thesi" << endl;
		}
	}
	else if (choice == 2)
	{
		cout << "Dwse mia timi 0 - 0.9" << endl;
		cout << "----> : ";
		cin >> accessRisk;
		ground[x][y].setAccessRisk(accessRisk);
	}
	else if (choice == 3)
	{
		cout << "Dwse timi palladiou ---> ";
		cin >> palladium;
		ground[x][y].setPalladium(palladium);
	}
	else if (choice == 4)
	{
		cout << "Dwse timi iridiou ---> ";
		cin >> iridium;
		ground[x][y].setIridium(iridium);
	}
	else if (choice == 5)
	{
		cout << "Dwse timi leukoxrusou ---> ";
		cin >> platinum;
		ground[x][y].setPlatinum(platinum);
	}
	else
	{
		cout << "I epilogi pou zitisate den yparxei. I prosomoiwsi sunexizetai. " << endl;
	}
}


/* Arxika dimiourgia 3 robot kai ta topothetei sto vector robots */
void World::initVector(Map * map)
{
	Exploration *exploration = new Exploration(0, 0, 0, 0, 0);
	Rescue *rescue = new Rescue(0, 0, 0, 0, 0);
	Analyser *analyser = new Analyser(0, 0, 0, 0, 50, 0, 0, 0, 0, 0, false);

	robots.push_back(exploration);
	robots.push_back(rescue);
	robots.push_back(analyser);
}

/* Topothetei ta robot ston xarti */
void World::placeToMap(Map * map)
{
	int x;     /* Random x coordinate */
	int y;	   /* Random y coordinate */

	/* Topothetisi robot tuxaia ston xarti mas */
	for (int i = 0; i < robots.size(); i++)
	{
		x = rand() % 10;
		y = rand() % 10;

		robots[i]->setX(x);
		robots[i]->setY(y);

		map->setPositions(robots[i]->getX(), robots[i]->getY(), robots[i]->getID());
	}
}

/* Topothetei ta nea robot pou eisigage o xristis sto xarti */
void World::placeNewRobot(Map * map)
{
	int x;
	int y;
	bool flag = false;

	while (!flag)
	{
        // Epilegei tuxaia mia thesi kai elegxei ean einai keni kai ta topothetei, alliws psaxnei alli
		x = rand() % 10;
		y = rand() % 10;

		if (map->isEmpty(x, y))
		{
			robots.back()->setX(x);
			robots.back()->setY(y);
			map->setPositions(x, y, robots.back()->getID());
			flag = true;
		}
	}
}


/* Arxikopoiei ton pinaka edafous - ground */
void World::initGroundMap(Ground * ground[])
{
	float ar;		 /* Epikindinotita prosvasis */
	float amountPal; /* Periektikotita palladiou */
	float amountIri; /* Periektikotita iridiou */
	float amountPla; /* Periektikotita leukoxrisou */


	for (int i = 0; i < 10; i++)
	{
		ground[i] = new Ground[10];
	}

	for (int i = 0; i < 10; i++)
	{
		for (int j = 0; j < 10; j++)
		{
			ar = (rand() % 10) / 10.0;
			amountPal = rand() % 11;
			amountIri = rand() % 11;
			amountPla = rand() % 11;

			ground[i][j] = Ground(false, ar, amountPal, amountIri, amountPla);
		}
	}
}

/* Prosthetei neo robot sto vector */
void World::addRobot()
{
	int robotType;

	cout << "Ti oxima thes na eisageis ?" << endl;
	cout << "1 - Robot Analisis" << endl;
	cout << "2 - Robot Eksereunisis" << endl;
	cout << "3 - Robot Diaswsis" << endl;

	cin >> robotType;

	if (robotType == 1)
	{
		Analyser *anal = new Analyser(0, 0, 0, 0, 50, 0, 0, 0, 0, 0, false);
		robots.push_back(anal);
	}
	else if (robotType == 2)
	{
		Exploration *exploration = new Exploration(0, 0, 0, 0, 0);
		robots.push_back(exploration);
	}
	else if (robotType == 3)
	{
		Rescue *rescue = new Rescue(0, 0, 0, 0, 0);
		robots.push_back(rescue);
	}
}