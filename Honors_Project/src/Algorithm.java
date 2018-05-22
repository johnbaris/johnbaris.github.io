import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by Yannis on 22/01/2018.
 */
public class Algorithm {
    //Define the variables
    // All the rules
    List<List<String>> rulesList = new ArrayList<List<String>>();
    // A list for each rule of a specific form
    ArrayList<String> rules1 = new ArrayList<String>();
    ArrayList<String> rules1New = new ArrayList<String>();
    ArrayList<String> rules2 = new ArrayList<String>();
    ArrayList<String> rules2New = new ArrayList<String>();
    ArrayList<String> rules3 = new ArrayList<String>();
    ArrayList<String> rules3New = new ArrayList<String>();
    ArrayList<String> rules4 = new ArrayList<String>();
    ArrayList<String> rules4New = new ArrayList<String>();
    String implies = "->";
    int rule;
    long startTime;
    long startTime2;
    List<String> rules = new ArrayList<String>();
    List<String> rulesNew = new ArrayList<String>();
    ArrayList<String> resolved = new ArrayList<String>();
    ArrayList<String> resolvedNew = new ArrayList<String>();
    // The pairs of already resolved rules
    List<Pair<String>> pairs = new ArrayList<Pair<String>>();
    boolean fullRule2 = false;
    int rep = 0;
    long estimatedTime2;

// TODO PUT THE STARTTIME IN THE BEGINNING OF THE DISJUNCT CHECK
    public Algorithm() {
        startTime = System.nanoTime();
        addInput();
        readInput();
    }

    public void loop() {
        if(rules1.isEmpty()){
            System.out.println("There are no inconsistencies");
            System.exit(0);
        }
        else{
//            System.out.println("The number of DISJ is: " + rules.size());
        }
        long initialTime = System.nanoTime() - startTime;
//        System.out.println("The elapsed time is " + initialTime/1000000000.0);
        for (String s : rules1) {
            startTime2 = System.nanoTime();
//            System.out.println("THIS IS THE ONE WE NEED " + s);
            for(int i = 0;i<rules2.size();i++){
                rules2New.add(rules2.get(i));
            }
            for(int i = 0;i<rules3.size();i++){
                rules3New.add(rules3.get(i));
            }
            for(int i = 0;i<rules4.size();i++){
                rules4New.add(rules4.get(i));
            }
            for(int i = 0;i<rules.size();i++){
                rulesNew.add(rules.get(i));
            }
            for(int i = 0;i<resolved.size();i++){

                resolvedNew.add(resolved.get(i));
            }
            rules1New.add(s);
            for (int k = 0; k < rules2New.size(); k++) {
                for (int l = 0; l < rules3New.size(); l++) {
                    // Check for pairs which are already resolved
                    Pair p = new Pair(rules2New.get(k), rules3New.get(l));
                    if (!pairs.contains(p)) {
                        pairs.add(p);
//                        System.out.println("The pair: " + rules2New.get(k) + " " + rules3New.get(l) + " was added");
                        selectionFunction(rules2New.get(k), rules3New.get(l));
                    } else {
                    }
                }
            }
            for (int n = 0; n < rules2New.size(); n++) {
                for (int m = 0; m < rules4New.size(); m++) {
                    // Check for pairs which are already resolved
                    Pair p = new Pair(rules2New.get(n), rules4New.get(m));
                    if (!pairs.contains(p)) {
                        pairs.add(p);
//                        System.out.println("The pair: " + rules2New.get(n) + " " + rules4New.get(m) + " was added");
                        selectionFunction(rules2New.get(n), rules4New.get(m));
                    } else {
                    }
                }
            }
            while (!fullRule2) {
                int u;
                int v = 0;
                for (u = 0; u < rules2New.size(); u++) {
                    for (v = 0; v < rules2New.size(); v++) {
                        // Check for pairs which are already resolved
                        Pair p = new Pair(rules2New.get(u), rules2New.get(v));
                        if (!pairs.contains(p)) {
                            pairs.add(p);
//                            System.out.println("The pair: " + rules2New.get(u) + " " + rules2New.get(v) + " was added");
                            selectionFunction(rules2New.get(u), rules2New.get(v));
                        } else {
                        }
                    }
                }
                if (u == rules2New.size() && v == rules2New.size() && rep > 0) {
                    fullRule2 = true;
                } else {
                    rep++;
                }
            }
            for (int i = 0; i < rules1New.size(); i++) {
                for (int j = 0; j < rules2New.size(); j++) {
                    Pair p = new Pair(rules1New.get(i), rules2New.get(j));
                    if (!pairs.contains(p)) {
                        pairs.add(p);
//                        System.out.println("The pair: " + rules1New.get(i) + " " + rules2New.get(j) + " was added");
                        selectionFunction(rules1New.get(i), rules2New.get(j));
                    } else {
                    }
                }
            }
            for(int i = 0;i < rulesNew.size();i++){
                if(getHead(rulesNew.get(i)).equals("p") && !rules1New.contains(rulesNew.get(i))){
                    rulesNew.remove(i);
                }
            }
            rulesList.add(rulesNew);
            rules1New = new ArrayList<String>();
            rules2New = new ArrayList<String>();
            rules3New = new ArrayList<String>();
            rules4New = new ArrayList<String>();
            rulesNew = new ArrayList<String>();
            pairs = new ArrayList<Pair<String>>();
            resolvedNew = new ArrayList<String>();
            rep++;
            estimatedTime2 = System.nanoTime() - startTime2;
//            System.out.println("The elapsed time is " + estimatedTime2/1000000000.0);
        }
    }

    public void addInput(){

        int classNum = 73;
        int disjNum = 30;
        String disj = "\n";
        List<String> assertions = new ArrayList<>();
        int randomNum;
        int classesNum = ThreadLocalRandom.current().nextInt(2, 9);
        try {
            FileWriter fstream = new FileWriter("Input.txt", true);
            BufferedWriter out = new BufferedWriter(fstream);
            for (int i=0;i<disjNum;i++){
                for (int j=0;j<classesNum;j++){
                    randomNum = ThreadLocalRandom.current().nextInt(1, classNum+1);
                    while(disj.contains(Integer.toString(randomNum))){
                        randomNum = ThreadLocalRandom.current().nextInt(1, classNum+1);
                    }
                    if(j!=classesNum-1) {
                        disj = disj + "C" + Integer.toString(randomNum) + "(X)" + "&";
                    }
                    else{
                        disj = disj + "C" + Integer.toString(randomNum) + "(X)";

                    }
                }
//                System.out.println(disj);
                if(!assertions.contains(disj)) {
                    out.write(disj + "->p");
                    assertions.add(disj);
                }
                else{
                    i--;
                }
                disj = "\n";
            }
//            for (int i = 1; i < 38; i++) {
//                for (int j = i + 1; j < 38; j++){
//                    for (int k = j + 1; k < 38; k++) {
//                        out.write("\nC" + Integer.toString(i) + "(X)&C" + Integer.toString(j) + "(X)&C" + Integer.toString(k) + "(X)" + "->p");
//                    }
//                 }
//            }
            out.close();
        }catch (Exception e){
            System.err.println("Error while writing to file: " +
                    e.getMessage());
        }
    }


    public void readInput() {
        try {
            File file = new File("Input.txt");
            rules = Files.readAllLines(Paths.get(file.getAbsolutePath()));
            for(int i=0;i<rules.size();i++){
                if(checkForm(rules.get(i))==1){
                    rules1.add(rules.get(i));
                }
                else if(checkForm(rules.get(i))==2){
                    rules2.add(rules.get(i));
                }
                else if(checkForm(rules.get(i))==3){
                    rules3.add(rules.get(i));
                }
                else{
                    rules4.add(rules.get(i));
                }
            }
            String tempRule = "";
            for(int j = 0;j<rules.size();j++) {
                String[] vars = rules.get(j).split(implies)[0].split("&");
                if (!getHead(rules.get(j)).equals("p")) {
                    tempRule = rules.get(j).replace(getVariable(getHead(rules.get(j))), "X");
                } else {
                    tempRule = rules.get(j);
                }
                if (!getBody(rules.get(j)).equals("true")) {
                    for (int i = 0; i < vars.length; i++) {
                        tempRule = tempRule.replace(getVariable(vars[i]), "X");
                    }
                }
                resolved.add(tempRule);
            }
        }catch(IOException e) {
            System.out.println("File not found!");
        }
//        System.out.println("We read the input");
    }

    // Body of a rule
    public String getBody(String formula) {

        String body[] = formula.split(implies);
        return body[0];

    }

    // Head of a rule
    public String getHead(String formula) {

            String head[] = formula.split(implies);
            return head[1];

    }

    // The selection function. Given two rules, it checks their form and proceeds to select the atoms
    // TODO MGU
    public void selectionFunction(String premise1, String premise2){

        int rule1 = checkForm(premise1);
        int rule2 = checkForm(premise2);
        String chosen1 = "";
        String chosen2 = "";
        if (rule1==1 && rule2==2){
            if ((getBody(premise2).equals("true")) || (getHead(premise2).contains("f") && !getBody(premise2).contains("f"))){
                chosen2 = getHead(premise2);
                if(getBody(premise1).contains("&")){
                    String parts[] = getBody(premise1).split("&");
                    for(int i=0 ; i < parts.length ; i++){
                        if(getBody(premise1).contains("f")) {
                            if (parts[i].split("\\(")[0].equals(chosen2.split("\\(")[0].split("'")[0]) && getVariable(parts[i]).contains("f")) {
                                chosen1 = parts[i];
                            }
                        }
                        else{
                            if (parts[i].split("\\(")[0].equals(chosen2.split("\\(")[0].split("'")[0])) {
                                chosen1 = parts[i];
                            }
                        }
                    }
                }
                else{
                    chosen1 = getBody(premise1);
                }
                if(!chosen1.split("\\(")[0].equals(chosen2.split("\\(")[0].split("'")[0])){
                    return;
                }
                if(chosen1.split("\\(")[1].substring(0,1).equals("c") && (!chosen1.split("\\(")[0].equals(chosen2.split("\\(")[0]) || !chosen1.split("\\(")[1].split("\\)")[0].equals(chosen2.split("\\(")[1].split("\\)")[0]))){
                    return;
                }
            }
            else{
                return;
            }
        }
        else if(rule1==2){
            if(rule2==2){
                if(((getBody(premise2).equals("true")) && ((getHead(premise1).contains("f") && getBody(premise1).contains("f")) || (!getBody(premise1).equals("true") && !getHead(premise1).contains("f")))) || (!getBody(premise2).equals("true") && !getBody(premise2).contains("f") && getHead(premise2).contains("f")) && ((getHead(premise1).contains("f") && getBody(premise1).contains("f")) || (!getBody(premise1).equals("true") && !getHead(premise1).contains("f")))){
                    chosen2 = getHead(premise2);
                    if(getBody(premise1).contains("&")){
                        String parts[] = getBody(premise1).split("&");
                        for(int i=0 ; i < parts.length ; i++){
                            if(getBody(premise1).contains("f")) {
                                if (parts[i].split("\\(")[0].equals(chosen2.split("\\(")[0].split("'")[0]) && getVariable(parts[i]).contains("f")) {
                                    chosen1 = parts[i];
                                }
                            }
                            else{
                                if (parts[i].split("\\(")[0].equals(chosen2.split("\\(")[0].split("'")[0])) {
                                    chosen1 = parts[i];
                                }
                            }
                        }
                    }
                    else{
                        chosen1 = getBody(premise1);
                    }
                    if(!chosen1.split("\\(")[0].equals(chosen2.split("\\(")[0].split("'")[0])){
                        return;
                    }
                    if(chosen1.split("\\(")[1].substring(0,1).equals("c") && !chosen1.split("\\(")[0].equals(chosen2.split("\\(")[0]) && !chosen1.split("\\(")[1].split("\\)")[0].equals(chosen2.split("\\(")[1].split("\\)")[0])){
                        return;
                    }
                }
                else{
                    return;
                }

            }
            else if(rule2==3){
                if(premise1.contains("a") && (!getBody(premise1).contains("f") && (getHead(premise1).contains("f")||getBody(premise1).contains("true")))){
                    chosen2 = getHead(premise1);
                    String parts[] = getBody(premise2).split("&");
                    for(int i = 0; i < parts.length; i++) {
                        if(parts[i].split("\\(")[0].contains("a")){
                            chosen1 = parts[i];
                        }
                    }
                    String temp = premise1;
                    premise1 = premise2;
                    premise2 = temp;
                    if(!chosen1.split("\\(")[0].equals(chosen2.split("\\(")[0])){
                        return;
                    }

                }
                else{
                    return;
                }
            }
            else{
                if(premise1.contains("A") && (!getBody(premise1).contains("f") && (getHead(premise1).contains("f")||getBody(premise1).contains("true")))){
                    chosen2 = getHead(premise1);
                    String parts[] = getBody(premise2).split("&");
                    for(int i = 0; i < parts.length; i++) {
                        if(parts[i].split("\\(")[0].substring(0,1).equals("A")){
                            chosen1 = parts[i];
                        }
                    }
                    String temp = premise1;
                    premise1 = premise2;
                    premise2 = temp;
                    if(!chosen1.split("\\(")[0].equals(chosen2.split("\\(")[0])){
                        return;
                    }
                }
                else{
                    return;
                }
                }
            }
        unification(premise1,premise2,chosen1,chosen2);
    }

    public void unification(String premise1, String premise2, String atom1, String atom2){

        String finalResolvent = "";
        String tempResolvent = "";
        String arbitResolvent = "";
        String resolvent = "";
        String body[] = getBody(premise2).split("&");
        String finalBody = "";
        int n = 0;
        String newAtom = atom1.replace(getVariable(atom1),getVariable(atom2));
        if(getVariable(atom1).contains(",") && getVariable(atom2).contains(",")){
            String at1var1 =  getVariable(atom1).split(",")[0];
            String at1var2 =  getVariable(atom1).split(",")[1];
            String at2var1 =  getVariable(atom2).split(",")[0];
            String at2var2 =  getVariable(atom2).split(",")[1];
            tempResolvent = premise1.replaceAll(at1var1, at2var1);
            resolvent = tempResolvent.replaceAll(at1var2, at2var2);
        }
        else {
            resolvent = premise1.replaceAll(getVariable(atom1), getVariable(atom2));
        }
        if(getBody(premise2).equals("true")){
            if(resolvent.split(implies)[0].split("&")[0].equals(newAtom)){
                if(!resolvent.contains("&")){
                    finalResolvent = getBody(resolvent).replace(newAtom,"") + implies + getHead(resolvent);
                    if(getBody(finalResolvent).equals("")){
                        finalResolvent = finalResolvent.split(implies)[0].replace("","true") + implies + getHead(finalResolvent);
                    }
                }
                else{
                    finalResolvent = resolvent.replace(newAtom + "&","");
                }
            }
            else {
                finalResolvent = resolvent.replace("&" + newAtom, "");
            }
        }
        else {
            for(int i =0;i<body.length;i++) {

                    if (!getBody(resolvent).contains(body[i].split("\\(")[0]+"(")) {
                        if(n==0){
                            finalBody = finalBody + body[i];
                            n++;
                        }
                        else{
                            finalBody = finalBody + "&" + body[i];
                            n++;
                        }
                    }
            }
            if(premise1.contains("a") || premise1.contains("A")){
                finalResolvent = getBody(resolvent).replace(newAtom,finalBody) + implies + getHead(resolvent);
                finalResolvent = finalResolvent.replace("&", "");
            }
            else {
                if(resolvent.contains("&") && finalBody.equals("")) {
                    if(getBody(resolvent).split("&")[getBody(resolvent).split("&").length-1].equals(newAtom)){
                        finalResolvent = resolvent.replace("&" + newAtom, finalBody);
                    }
                    else{
                        finalResolvent = resolvent.replace(newAtom + "&", finalBody);
                    }
                }
                else {
                    finalResolvent = getBody(resolvent).replace(newAtom,finalBody) + implies + getHead(resolvent);
                }
            }
        }
        if(getBody(finalResolvent).equals("true") && getHead(finalResolvent).equals("p")){
            System.out.println("INCONSISTENCY FOUND");
            long estimatedTime = System.nanoTime() - startTime;
            System.out.println("The elapsed time is " + estimatedTime/1000000000.0);
            System.exit(0);
        }

        // Check for duplicate resolvents
        String[] res = getBody(finalResolvent).split("&");
        if(!getHead(finalResolvent).equals("p")) {
            if(!getVariable(getHead(finalResolvent)).contains("c")) {
                arbitResolvent = finalResolvent.replace(getVariable(getHead(finalResolvent)), "X");
            }
            else{
                arbitResolvent = finalResolvent;
            }
        }
        else{
            arbitResolvent = finalResolvent;
        }
        if(!getBody(arbitResolvent).equals("true")) {
            for (int i = 0; i < res.length; i++) {
                if(!getVariable(res[i]).contains("c")) {
                    arbitResolvent = arbitResolvent.replace(getVariable(res[i]), "X");
                }
            }
        }
        if(getBody(finalResolvent).equals(getHead(finalResolvent))){
            return;
        }
        String[] s1 = getBody(arbitResolvent).split("&");
        boolean dupe = false;
        int count = 0;
        // TODO delete dupe predicates from body (in the code above when replacing atoms)
        for (String s : resolvedNew){
            String[] s2 = getBody(s).split("&");
            if(s1.length == s2.length){
                for (int i = 0;i<s1.length;i++){
                    if(s.contains(s1[i]) && getHead(s).equals(getHead(arbitResolvent))){
                        count++;
                    }
                }
            }
            if(count == s1.length){
//                System.out.println("Dupe found");
                return;
            }
            count = 0;
        }
        if(resolvedNew.contains(arbitResolvent)) {
        }
        else{
            resolvedNew.add(arbitResolvent);
//            System.out.println("The resolvent was added: " + arbitResolvent);
            rulesNew.add(finalResolvent);
//            System.out.println("The resolvent was added: " + finalResolvent);
            if(checkForm(premise1)==1) {
                rules1New.add(finalResolvent);
            }
            else{
                if(getBody(finalResolvent).contains("c") && !getBody(finalResolvent).contains("&") && !arbitResolvent.contains("true->" + getBody(finalResolvent))){
                    rules2New.add("true->" + getBody(finalResolvent));
                }
                if(getHead(finalResolvent).contains("c") && !arbitResolvent.contains("true->" + getHead(finalResolvent))){
                    rules2New.add("true->" + getHead(finalResolvent));
                }
                rules2New.add(finalResolvent);
                fullRule2 = false;
            }
        }
    }

    // TODO write getPredicates() function and file the variable for each predicate (maybe arraylist)
    public String getVariable(String predicate){
        String variable[] = predicate.split("\\(");
        String var = "";
        if(predicate.contains(",")){
            if(variable.length>2){
                if(predicate.split(",")[0].contains("f")){
                    var = predicate.split("\\(",2)[1].split(",")[0] + "," + predicate.split("\\(",2)[1].split(",")[1].split("\\)")[0];
                }
                else {
                    var = predicate.split("\\(", 2)[1].split("\\)")[0] + ")";
                }
            }
            else{
                var = predicate.split("\\(",2)[1].split("\\)")[0];
            }
        }
        else {
            if (variable.length > 2) {
                var = predicate.split("\\(", 2)[1].split("\\)")[0] + ")";
            } else {
//            System.out.println("We are here 3");
                var = predicate.split("\\(")[1].split("\\)")[0];
            }
        }
        return var;
    }

    public List<String> getRules(){
        return rules;
    }

    // Checks the form of a rule
    // TODO If we can have classes such as integers involved then hardwire so that it recognises them
    public int checkForm(String formula){

        String body = getBody(formula);
        String head = getHead(formula);
        String testB[] = new String[]{};
        String testH[] = head.split("\\(");
        if (formula.contains("&")) {
            testB = body.split("&");

        }
        else{
            testB = new String[1];
            testB[0] = body;
        }
        if(body.equals("true")){
            return 2;
        }
        if(head.equals("p")){
            return 1;
        }
        if(body.contains("a")){
            return 3;
        }
        if(head.contains("a") || head.contains("A") || (head.contains("C") && !body.contains("a") & !body.contains("A"))){
            return 2;
        }
        if(body.contains("A")){
            return 4;
        }
//

        return rule;
    }

//    // Skolemize the rules
//    public String skolemize(String formula) {
//        String skolForm = "";
//        String[] side = formula.split(implies);
//        String body = side[0];
//        String head = side[1];
//        String[] parStart = head.split("\\(");
//        String[] parEnd = parStart[1].split("\\)");
//        String v1 = parEnd[0].split(",")[0];
//        String v2 = parEnd[0].split(",")[1];
//        if (!body.contains(v2)){
//            skolForm = "C" +"(" + var1 +")" + implies + binary + "(" + var1 + ",f(" + var1 + "))";
//        }
//        else if (!body.contains(v1)){
//            skolForm = "C" +"(" + var2 +")" + implies + binary + "(" + var2 + ",f(" + var2 + "))";
//        }
//        return skolForm;
//    }


    // Main function
    public static void main(String[] args) {

        Algorithm a = new Algorithm();
        a.loop();

        for(int i=0;i<a.rulesList.size();i++) {
            for (int j = 0; j < a.rulesList.get(i).size(); j++) {
                if (a.rulesList.get(i).get(j).contains("f")) {
                    a.rulesList.get(i).remove(j);
                    j = j - 1;
                }
            }
        }

        for(int i=0;i<a.rulesList.size();i++){
            for(int j=0;j<a.rulesList.get(i).size();j++) {
                String s = "";
                String temp;
                temp = a.rulesList.get(i).get(j).replace("(", "q"+Integer.toString(i)+"(");
                if (temp.contains("true->")){
                    s = temp.replace("true->","");
                    a.rulesList.get(i).set(j,s);
                }
                else {
                    a.rulesList.get(i).set(j, temp);
                }
            }
        }
        Path file = Paths.get("Output.txt");
        try {
            Files.write(file, a.rulesList.get(0), Charset.forName("UTF-8"));
            FileWriter writer = new FileWriter("Output.txt", true);
            for(int i = 1;i<a.rulesList.size();i++) {
                for (String str : a.rulesList.get(i)) {
                    writer.write(str + "\n");
                }
            }
            writer.close();
        }catch(IOException e){

        }
        long estimatedTime = System.nanoTime() - a.startTime;
        System.out.println("The elapsed time is " + estimatedTime/1000000000.0);

    }
}
