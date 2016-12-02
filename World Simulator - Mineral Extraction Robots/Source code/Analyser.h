#ifndef ANALYSER_H
#define ANALYSER_H

#include "Robot.h"

class Analyser : public Robot
{
public:

	Analyser(int, int, int, int, float, float, float, float, float, float, bool); /* Constructor */

	float getMaxCargo() const;        /* Epistrefei to megisto fortio tou robot */
	void setMaxCargo(float);		  /* Orizei to megisto fortio tou robot */

	float getPalladiumCargo() const;  /* Epistrefei to fortio palladiou pou exei to robot */
	void setPalladiumCargo(float);    /* Orizei to fortio ..... */

	float getIridiumCargo() const;    /* Epistrefei to fortio iridiou pou exei to robot */
	void setIridiumCargo(float);	  /* Orizei to fortio ....  */

	float getPlatinumCargo() const;
	void setPlatinumCargo(float);

	float getMineCargo() const;
	void setMineCargo(float);

	float getTotalCargo() const;     /* Epistrefei to sunoliko fortio pou exei mazepsei to robot */
	void setTotalCargo(float);       /* Orizei .... */

	int getMaxAmount(Ground *[]);    /* Epistrefei poio sustatiko exei tin megisti periektikotita 0 - palad, 1 - iri, 2 - plat */

	void transfer(Base *);           /* Metaferei sustatika stin vasi */

	bool isMineDamaged();            /* Epistrefei true an to robot exei vlavi apo eksoruksi alliws false */
	void setMineDamage(bool);

	void checkForMineDamage();       /* Elenxei mipws dimiourgithei vlavi apo eksoruksi*/

	virtual void printInfo();
	virtual void move(Map *);		 /* Leitourgia kinisis tou robot */
	virtual void leitourgia(Map *, Ground *[], Base * base, vector< Robot * >);

	static int getTotalMinedCargo();

private:
    float maxCargo;		             /* Megisto Fortio */
    float mineCargo;                 /* Poso pou exei kanei eksoriksi */
    float palladiumCargo;			 /* Fortio Palladiou */
    float iridiumCargo;			 	 /* Fortio Iridiou */
    float platinumCargo;			 /* Fortio Leukoxrustou */
    float totalCargo;                /* Sunoliko fortio pou exei eksoruksi kathe robot */
	bool mineDamage;                 /* Pithanotita na pathei vlavi kata tin eksoriksi, false = dn exei vlavi, true = exei */

	static int totalMinedCargo;
};

#endif