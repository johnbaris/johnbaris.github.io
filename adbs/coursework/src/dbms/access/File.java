package dbms.access;

import dbms.memory.AbstractBufferManager;
import dbms.memory.FullBufferException;

import java.io.IOException;

/**
 * Created by s1443483 on 14/03/2018.
 */
public class File extends AbstractFile {

    public File(AbstractBufferManager manager, int addr){
        super(manager, addr);
    }

    public int printPage(int addr) throws FullBufferException {

        int frameAddr = this.manager.fetch(addr);
        byte[] signInt = new byte[2];
        byte[] bigSignInt = new byte[2];
        int decimalInt;
        // The first two bytes that show the disk address of the next page
        signInt[0] = this.manager.bufferPool[frameAddr];
        signInt[1] = this.manager.bufferPool[frameAddr+1];
        // The byte that shows the number of records
        int recordNum = this.manager.bufferPool[frameAddr+2];
        int n = 8;
        int nTemp = n;
        byte[] recordInt = new byte[2];
        byte[] recordString = new byte[10];
        byte[] recordData = new byte[12];
        String field1 = "";
        int field2;
        // Get the records of the page
        for(int i=0;i<recordNum;i++){
            for(int j=nTemp;j<nTemp+12;j++){
                recordData[j-nTemp] = this.manager.bufferPool[frameAddr+j];
                n++;
            }
            for(int k=0;k<10;k++){
                recordString[k] = recordData[k];
            }
            try {
                // Convert the first 10 bytes of a record to the required string
                field1 = new String(recordString, "UTF-8");
            }
            catch (IOException e){

            }
            recordInt[0] = recordData[10];
            recordInt[1] = recordData[11];
            // The unsigned integer value
            field2 = toUnDecimal(toBigEndian(recordInt));
            printRecord(field1, field2);
            nTemp = n;
        }
        // Address of next page
        bigSignInt = toBigEndian(signInt);
        decimalInt = toDecimal(bigSignInt);
        int nextAddr = decimalInt;
        this.manager.release(frameAddr,false);
        return nextAddr;
    }


    // Convert little endian to big endian
    public byte[] toBigEndian(byte[] signedInt){

        byte temp = signedInt[0];
        signedInt[0] = signedInt[1];
        signedInt[1] = temp;
        return signedInt;

    }

    // Convert bytes to unsigned integer decimal
    public int toUnDecimal(byte[] unsignedInt) {

        double decimal = 0;
        // Covert the bytes to strings
        String b1=String.format("%8s",Integer.toBinaryString(unsignedInt[0]));
        String b2=String.format("%8s",Integer.toBinaryString(unsignedInt[1]));
        String newB1 = "";
        String newB2 = "";
        String newString1 = "";
        String newString2 = "";
        // Add the extra padding zeros to make 8 bytes in total if fewer
        for(int i=0;i<b1.length();i++){
            if (b1.charAt(i) != '1' && b1.charAt(i)!='0'){
                newB1 = newB1 + "0";
            }
            else
            {
                newB1 = newB1 + b1.charAt(i);
            }
        }
        for(int j=0;j<b2.length();j++){
            if (b2.charAt(j) != '1' && b2.charAt(j)!='0'){
                newB2 = newB2 + "0";
            }
            else
            {
                newB2 = newB2 + b2.charAt(j);
            }
        }
        // Reverse the order of bits
        for(int k=newB1.length()-1;k>newB1.length()-9;k--){
            newString1 = newString1 + newB1.charAt(k);
        }

        for(int l=newB2.length()-1;l>newB2.length()-9;l--){
            newString2 = newString2 + newB2.charAt(l);
        }
        // Perform the sum of the bits to calculate the decimal
        for (int i = 0; i < newString2.length(); i++) {
            if (newString2.charAt(i) == '1') {
                decimal = decimal + Math.pow(2, i);
            }
        }
        for (int i = 0; i < newString1.length(); i++) {
            if (newString1.charAt(i) == '1') {
                decimal = decimal + Math.pow(2, i + 8);
            }
        }
        return (int) decimal;
    }

    // Convert bytes to signed integer decimal
    public int toDecimal(byte[] signedInt){

        boolean isNeg = false;
        double decimal = 0;
        // Covert the bytes to strings
        String b1=String.format("%8s",Integer.toBinaryString(signedInt[0]));
        String b2=String.format("%8s",Integer.toBinaryString(signedInt[1]));
        String newB1 = "";
        String newB2 = "";
        // Add the extra padding zeros to make 8 bytes in total if fewer
        for(int i=0;i<b1.length();i++){
            if (b1.charAt(i) != '1' && b1.charAt(i)!='0'){
                newB1 = newB1 + "0";
            }
            else
            {
                newB1 = newB1 + b1.charAt(i);
            }
        }
        for(int j=0;j<b2.length();j++){
            if (b2.charAt(j) != '1' && b2.charAt(j)!='0'){
                newB2 = newB2 + "0";
            }
            else
            {
                newB2 = newB2 + b2.charAt(j);
            }
        }
        String newString1 = "";
        String newString2 = "";
        String newString12 = "";
        String newString21 = "";
        if(newB1.charAt(0) == '1'){
            isNeg = true;
        }
        if(isNeg){
            for(int i=0;i<newB1.length();i++){
                if (newB1.charAt(i) == '0'){
                    newString1 = newString1 + '1';
                }
                else{
                    newString1 = newString1 + '0';
                }
            }
            for(int j=0;j<newB2.length();j++){
                if (newB2.charAt(j) == '0'){
                    newString2 = newString2 + '1';
                }
                else{
                    newString2 = newString2 + '0';
                }
            }
            // Reverse the order of bits
            for(int k=newString1.length()-1;k>newString1.length()-9;k--){
                newString12 = newString12 + newString1.charAt(k);
            }
            newString12 = new StringBuilder(newString12).reverse().toString();

            for(int l=newString2.length()-1;l>newString2.length()-9;l--){
                newString21 = newString21 + newString2.charAt(l);
            }
            newString21 = new StringBuilder(newString21).reverse().toString();

            String twos1 = "";
            String twos2 = "";
            boolean allOnes = true;

            // Regain the original bytes from the 2's complement form
            for(int i=newString21.length()-1;i>-1;i--){
                if(newString21.charAt(i) == '1' && allOnes){
                    twos1 = twos1 + '0';
                }
                else if(newString21.charAt(i) == '0' && allOnes) {
                    twos1 = twos1 + '1';
                    allOnes = false;
                }
                else{
                    twos1 = twos1 + newString21.charAt(i);
                }
            }
            if(allOnes){
                for(int i=newString12.length()-1;i>-1;i--){
                    if(newString12.charAt(i) == '1' && allOnes){
                        twos2 = twos2 + '0';
                    }
                    else if(newString12.charAt(i) == '0' && allOnes) {
                        twos2 = twos2 + '1';
                        allOnes = false;
                    }
                    else{
                        twos2 = twos2 + newString12.charAt(i);
                    }
                }
            }
            // Perform the sum of the bits to calculate the decimal
            for(int i=0;i<twos1.length();i++){
                if(twos1.charAt(i) == '1'){
                    decimal =  decimal + Math.pow(2,i);
                }
            }
            for(int i=0;i<twos2.length();i++){
                if(twos2.charAt(i) == '1'){
                    decimal =  decimal + Math.pow(2,i+8);
                }
            }

        }
        else {
            for(int k=newB1.length()-1;k>newB1.length()-9;k--){
                newString1 = newString1 + newB1.charAt(k);
            }

            for(int l=newB2.length()-1;l>newB2.length()-9;l--){
                newString2 = newString2 + newB2.charAt(l);
            }
            for (int i = 0; i < newString2.length(); i++) {
                if (newString2.charAt(i) == '1') {
                    decimal = decimal + Math.pow(2, i);
                }
            }
            for (int i = 0; i < newString1.length(); i++) {
                if (newString1.charAt(i) == '1') {
                    decimal = decimal + Math.pow(2, i + 8);
                }
            }
        }

        if(isNeg){
            return - (int) decimal;
        }

        return (int) decimal;

    }

}
