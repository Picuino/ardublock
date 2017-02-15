/*
 
  Original Author: David Souder - souder.d@gmail.com
  Date de derni�re modification : 30/05/2014
  
*/

#ifndef SDPlus_H
#define SDPlus_H

#if defined(ARDUINO) && ARDUINO >= 100
#include "Arduino.h"
#else
#include <WProgram.h>
#include "Wire.h"
#endif


#include <SD.h>
#include "EDU_debug.h"

//#define DEBUG

#define SDPLUS_DEFAULT_FILE_NAME 	"file.txt"
#define SDPLUS_DEFAULT_SPACER 		"\t"
#define GROVE_CS   					4
#define DUPONT_CS  					10
#define DUPONT_CS  					8
#define	SDPLUS_MESSAGE_PERTE_DE_DONNEES	"SD:ERROR"

class SDPlus{

	public:
	/*EDU US*/	SDPlus();
	/*EDU FR*/	void 	brancher				(uint8_t pinCs, SDClass *sd=&SD);
	/*EDU US*/ 	void	branch  				(uint8_t pinCs, SDClass *sd=&SD);
	
	/*EDU FR*/	void	definirFichierCourant	(String fileName);
	/*EDU US*/	void	setCurentFile        	(String fileName);
	
	/*EDU FR*/	void 	definirSeparateur		(String spacer="\t");
	/*EDU US*/	void	setSpacer        		(String spacer="\t");
	
	/*EDU FR*/	void 	ecrire					(long valueL);
	/*EDU US*/	void 	write 					(long valueL);	
	
	/*EDU FR*/	void 	ecrire					(String valueS);
	/*EDU US*/	void 	write 					(String valueS);
	
	
	/*EDU FR*/	void	espace					(uint8_t nbr=1);
	/*EDU US*/	void 	space  					(uint8_t nbr=1);
    
	/*EDU US*/	void 	tabulation				(uint8_t nbr=1);
	
	/*EDU FR*/	void	nouvelleLigne			(uint8_t nbr=1);
	/*EDU US*/	void 	newLine					(uint8_t nbr=1);
	

	/*EDU US*/	void 	serialInfosPrint		(String message);		        // Affichage infos s�ries 	

	File 		file;
	uint8_t 	serialInfos;				 	// 1 = on veut les infos s�ries
		#define OUI 	1
		#define YES 	1
		#define NON 	0
		#define NON		0
	
	protected:
	SDClass 	*m_SD;				        
	
	char 		m_fileName[32];
	String 		m_spacer;					// S�parateur entre enregistrements
	

};
#endif

/* PROGRAMME EXEMPLE
#include <SD.h>
#include <SDPlus.h>

SDPlus maSD;

void setup(){
  maSD.brancher(GROVE_CS);
}

void loop(){
  maSD.ecrire("Temp�rature");
  maSD.ecrire(analogRead(A0));
  maSD.ecrire("Pression");
  maSD.ecrire(analogRead(A1));
  maSD.espace(1);
}
*/
