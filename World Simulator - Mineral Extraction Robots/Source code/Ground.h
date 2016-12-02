#ifndef GROUND_H
#define GROUND_H

class Ground
{
public:

	Ground();
    Ground(bool, float, int, int, int);

	bool isFlag() const;
	
	float getAccessRisk() const;

    int getPalladium() const;
    int getIridium() const;
    int getPlatinum() const;

    void setPlatinum(int);
    void setFlag(bool);
    void setAccessRisk(float);
    void setPalladium(int);
    void setIridium(int);
    void printInfo();            /* Ektupwnei plirofories gia thesi edafous */


private:
	bool flag;			         /* Yparksi simaias */
	float accessRisk;            /* Epikindinotita prosvasis perioxis */
	int palladium;	             /* Periektikotita palladiou */
    int iridium;		         /* Periektikotita iridiou */
    int platinum;                /* Periektikotita leukoxrisou */
};

#endif