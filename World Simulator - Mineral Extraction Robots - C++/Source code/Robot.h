#ifndef ROBOT_H
#define ROBOT_H

#include <vector>
#include <string>
#include <cstdlib>
#include <ctime>

using namespace std;

#include "Map.h"
#include "Base.h"
#include "Ground.h"

class Robot
{
public:
	Robot(int, int, int, int);			 /* Constructor */

	bool getState() const;				 /* Epistrefei tin katastasi tou robot */

    string getID() const;                /* Epistrefei to id tou robot */

	double getAccessAbility() const;	 /* Epistrefei tin ikanotita prosvasis */

	int getSpeed() const;				 /* Epistrefei tin taxitita tou robot */
    int getX() const;                    /* Epistrefei tin sintetagmeni x */
    int getY() const;                    /* Epistrefei tin sintetagmeni y */
    int getMoveCount() const;            /* Epistrefei ton arithmo ton kinisewn pou exei pragmatopoihsh */
    int getDamageCount() const;          /* Epistrefei ton arithmo ton vlavwn */
    int getType() const;                 /* Epistrefei ton tupo tou robot */
    int getRoundDamaged() const;         /* Epistrefei ton arithmo ton guron pou einai xalasmeno */

	void checkForMoveDamage(Ground *[]); /* Elenxei an dimiourgithike vlavi logo metakinisis */
    void setRoundDamaged(int);
    void setDamageCount(int);
    void setType(int);
    void setMoveCount(int);
    void setX(int);
    void setY(int);
    void setSpeed(int);				     /* Orizei tin taxitita tou robot */
    void setAccessAbility();			 /* Orizei tin ikanotita prosvasis tou robot */
    void setID(string);
    void setState(bool);				 /* FALSE = dn exei vlavi, TRUE = exei */

    // Virtual Functions
    virtual void printInfo() = 0;        /* Ektupwnei plirofories gia to robot */
    virtual void move(Map *) = 0;        /* Kinisi tou kathe robot */
    virtual void leitourgia(Map *, Ground *[], Base *, vector< Robot * >) = 0; /* Leitourgia kathe robot */

    // Static Functions
    static int getTotalDamageCount();
    static void updateTotalDamageCount(int);

private:
	bool state;                       /* Periexei tin katastasi tou robot */
	double accessAbility;			  /* Ikanotita prosvasis tou robot ( 0.1 - 1 ) */
	int x;							  /* X sintetagmeni */
    int y;                            /* Y sintetagmeni */
	int type;						  /* Tipos tou robot! 1 - Analyser, 2 - Exploration, 3 - Rescue */
	int moveCounter;				  /* Poses theseis exei metakinithei apo tin arxi tis prosomoiwsis */
	int damageCounter;                /* Poses vlaves exei ypostei */
    int roundDamaged;                 /* Posous gurous einai xalasmeno */
	string id;						  /* TO id tou robot */

	static int totalDamageCount;
};

#endif