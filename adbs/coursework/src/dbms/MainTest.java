package dbms;

import java.io.IOException;

/**
 * Created by Yannis on 14/03/2018.
 */
public class MainTest {

    public byte[] toBigEndian(byte[] signedInt){

        byte temp = signedInt[0];
        signedInt[0] = signedInt[1];
        signedInt[1] = temp;
        return signedInt;

    }

    public int toDecimal(byte[] signedInt){

        boolean isNeg = false;
        double decimal = 0;
        String b1=String.format("%8s",Integer.toBinaryString(signedInt[0]));
        String b2=String.format("%8s",Integer.toBinaryString(signedInt[1]));
        String newB1 = "";
        String newB2 = "";
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
        System.out.println("Fixed byte 1: " + newB1);
        System.out.println("Fixed byte 2: " + newB2);
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

            for(int k=newString1.length()-1;k>newString1.length()-9;k--){
                newString12 = newString12 + newString1.charAt(k);
            }
            newString12 = new StringBuilder(newString12).reverse().toString();

            for(int l=newString2.length()-1;l>newString2.length()-9;l--){
                newString21 = newString21 + newString2.charAt(l);
            }
            newString21 = new StringBuilder(newString21).reverse().toString();

            System.out.println("Deleting the extra 0s and reversing the bits of 2nd byte: " + newString12);
            System.out.println("Deleting the extra 0s and reversing the bits of 1st byte: " + newString21);

            String twos1 = "";
            String twos2 = "";
            boolean allOnes = true;
//            String byte1 = new StringBuilder(newString12).reverse().toString();
//            String byte2 = new StringBuilder(newString21).reverse().toString();


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
            System.out.println("2's complement of 1st byte: " + twos1);
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
            System.out.println("2's complement of 2nd byte: " + twos2);
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
//            newString1 = new StringBuilder(newString1).reverse().toString();

            for(int l=newB2.length()-1;l>newB2.length()-9;l--){
                newString2 = newString2 + newB2.charAt(l);
            }
//            newString2 = new StringBuilder(newString2).reverse().toString();

            System.out.println(newString1);
            System.out.println(newString2);
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

    public int toUnDecimal(byte[] unsignedInt) {

        double decimal = 0;
        String b1=String.format("%8s",Integer.toBinaryString(unsignedInt[0]));
        String b2=String.format("%8s",Integer.toBinaryString(unsignedInt[1]));
        String newB1 = "";
        String newB2 = "";
        String newString1 = "";
        String newString2 = "";
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
        for(int k=newB1.length()-1;k>newB1.length()-9;k--){
            newString1 = newString1 + newB1.charAt(k);
        }
//            newString1 = new StringBuilder(newString1).reverse().toString();

        for(int l=newB2.length()-1;l>newB2.length()-9;l--){
            newString2 = newString2 + newB2.charAt(l);
        }
//            newString2 = new StringBuilder(newString2).reverse().toString();

        System.out.println(newString1);
        System.out.println(newString2);
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

    public void byteToString(byte[] b){
        byte[] recordString = new byte[10];
        for(int k=0;k<10;k++){
            recordString[k] = b[k];
        }
        try {
            String field1 = new String(recordString, "UTF-8");
            System.out.println(field1);
        }
        catch (IOException e){

        }
    }

public static void main(String args[]){
    MainTest test = new MainTest();
    byte b1 = (byte) -62;
    byte b2 = (byte) 16;
    byte[] testBytes = new byte[2];
    byte b3 = (byte) -62;
    byte b4 = (byte) 16;
    byte[] testBytes1 = new byte[2];
    testBytes[0] = b1;
    testBytes[1] = b2;
    testBytes1[0] = b3;
    testBytes1[1] = b4;

//    for(int i=0;i<testBytes.length;i++) {
    System.out.println("This is the two bytes: " + Integer.toBinaryString(testBytes[0]) + Integer.toBinaryString(testBytes[1]));
//    }
    byte[] converted = test.toBigEndian(testBytes);
//    System.out.println(Integer.toBinaryString(converted[1]));
    int decimal = test.toDecimal(converted);
    int decimal1 = test.toUnDecimal(test.toBigEndian(testBytes1));
    System.out.println("The signed int is: " + decimal);
    System.out.println("The unsigned int is: " + decimal1);
    byte[] stringB = new byte[10];
    stringB[0] = (byte) 97;
    stringB[1] = (byte) 98;
    stringB[2] = (byte) 99;
    stringB[3] = (byte) 100;
    stringB[4] = (byte) 101;
    stringB[5] = (byte) 102;
    stringB[6] = (byte) 103;
    stringB[7] = (byte) 104;
    stringB[8] = (byte) 105;
    stringB[9] = (byte) 106;
    test.byteToString(stringB);
//    System.out.println(Integer.toString(converted[0],2));


}

}
