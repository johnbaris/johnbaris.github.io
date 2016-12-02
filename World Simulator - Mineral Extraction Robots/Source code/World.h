#ifndef WORLD_H
#define WORLD_H

#include "Robot.h"
#include "Map.h"
#include "Ground.h"
#include "Analyser.h"
#include "Base.h"


class World
{
public:
	void init();                                     /* Arxikopoiei ta antikeimena tis prosomoiwsis */

private:
    // private - helper functions
    void start(Map *, Ground *[], Base *);           /* Ksekinaei tin prosomoiwsi */
    void initVector(Map *);				             /* Arxikopoihsh vector pou periexei ta robot */
    void placeToMap(Map *);				             /* Topothetei ta robot tuxaia sto map */
    void action(Map *, Ground *[], Base *);          /* Kalei tin methodo kinisis-leitourgias ton robot */
    void initGroundMap(Ground *[]);		             /* Arxikopoiei to edafos me tuxaia sustatika */
    void addRobot();                                 /* Dimiourgei neo robot sto vector - user input */
    void placeNewRobot(Map *);                       /* Topothetei to neo robot sto map */
    void groundEdit(Ground *[], Map *);              /* Epeksergazetai mia sigkekrimeni thesi edafous */
    void menu(Ground *[], Map *, Base *);            /* Ektupwnei to menu */
    void displayInfo(Ground *[], Map * , Base *);
    void changeState(Map *);                         /* Allazei tin katastasi enos robot */
    void robotInfo();                                /* Plirofories gia to robot */
    void groundInfo(Ground *[], Map *);              /* Pliroforis gia kapoia thesi edafous */
    void totalOreAmount(Ground *[]);                 /* Sunoliko eksoruxthen fortio */
    void checkDamagedRobots(Map *);                  /* Elegxei an kapoio robot einai broken gia >= 15 gurous */

    float averageAccessRisk(Ground *[]);             /* Mesos oros epikindinotitas prosvasis */

    bool checkRobots();                              /* Elegxei an ola ta robot xalasane */


    // Robot Vector
	vector< Robot * > robots;				         /* Apothikeuoume ta robot */

};


#endif