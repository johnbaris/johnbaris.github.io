#ifndef BASE_H
#define BASE_H

class Base
{
public:
    Base();

    float getPalladium() const;    /* Epistrefei tin posotita palladiou */
	void setPalladium(float);      /* Orizei tin posotita palladiou */

    float getIridium() const;
	void setIridium(float);

    float getPlatinum() const;
	void setPlatinum(float);

	float getLimit() const;        /* Epistrefei to orio sustatikwn tis vasis */
	void setLimit(float);          /* Orizei ... */

	bool isFull();

	void printInfo();


private:
	bool full;
	float palladium;               /* Posotita palladiou pou yparxei stin vasi */
    float iridium;				 /* Posotita iridiou pou yparxei stin vasi */
    float platinum;				 /* Posotita leukoxrisou pou yparxei stin vasi */
    float limit;				     /* To orio tis vasis. Otan to ftasei tote i prosomoiwsi termatizetai */
};


#endif