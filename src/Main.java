import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int lw=0,lp=0,s=0,k=0,counter=0;  //lp-liczba polaczen w grafie, lw-liczba wierzcholkow w grafiem, s- start,k-koniec
        Vertex tabNode[]; // tablica wierzcholkow, indeks odpowiada numerowi wierzcholka
        int tabValues[];  // tablica dlugosci siezek

        try {
            Scanner in=new Scanner(new File(args[0]));
                String tmp =in.nextLine();
                String tab[] = tmp.split(" ");
                lw = Integer.parseInt(tab[0]);
                lp = Integer.parseInt(tab[1]);
                s = Integer.parseInt(tab[2]);
                k = Integer.parseInt(tab[3]);
                tabNode= new Vertex[lw];
                tabValues=new int[lw];
                for(int i=0;i<tabValues.length;i++)
                    tabValues[i]=-1;

            while(in.hasNext()){
                tmp = in.nextLine();
                if(counter<lw) {
                    tabNode[counter] = new Vertex(counter,Integer.parseInt(tmp)); // wprowadzam liczbe polaczen do Node  Integer.parseInt(tmp)
                }else {
                    String tw[] = tmp.split(" ");
                    tabNode[Integer.parseInt(tw[0])].addNode(Integer.parseInt(tw[1]), Integer.parseInt(tw[2]));
                    tabNode[Integer.parseInt(tw[1])].addNode(Integer.parseInt(tw[0]), Integer.parseInt(tw[2]));
                }
                counter++;
            }
            tabNode=Dijkstra(tabNode,tabValues,s,lp,lw);
            show(tabNode[k]);
            System.out.println("\n"+tabNode[k].getDistance());  // wyswietla dlugosc od wierzcholka startowego
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static void show(Vertex tmp) {  // wyswietla rekurencyjnie sciezke
        if (tmp.getParent() != null) {
            show(tmp.getParent());
        }
        System.out.print(tmp.getNumber() + " ");
    }

    static Vertex[] Dijkstra(Vertex tabNode[],int tabValues[],int s,int lp,int lw){

        int nrPetli=0;
        Vertex tmp=tabNode[s];  // wierzcholek startowy
        Vertex tmpMin=null;
        int values=-1;
        tabValues[tmp.getNumber()]=0;
        boolean flaga=true;
        int l=0;

        while(nrPetli < lw) {   // petla wykonuje sie zawsze n^2
            tabValues[tmp.getNumber()]=-2;
            for(int i=0;lw>i;i++) {
                if(i<tmp.getLengthOfTab()) {
                    int nr[] = tmp.getTabVertex(i);           // pobiera wartosc wierzcholka polaczonego z tmp
                    if ((tabValues[nr[0]] > (nr[1] + tmp.getDistance()) || tabValues[nr[0]] == -1) && tabValues[nr[0]] != tmp.getNumber()) {
                        tabValues[nr[0]] = nr[1] + tmp.getDistance();
                        tabNode[nr[0]].setVertex(tmp,nr[1]+tmp.getDistance());
                    }
                }
                if ( (tabValues[i] < values || flaga) && tabValues[i]!=-2   && tabValues[i]!=-1) {  //-2 oznacza, ze dana wartosc juz znalazla swoja najkrótszą sciezke. -1, ze jeszcze wierzcholek nie zostal odwiedzony
                    tmpMin=tabNode[i];
                    values=tabValues[i];
                    flaga=false;
             }
            }
            l++;
            System.out.print(l+ "  ");
            for(int i=0;i<tabValues.length;i++)
                System.out.print(tabValues[i]);
            System.out.println();
            flaga=true;
            tmp=tmpMin;
            nrPetli++;
        }
        return tabNode;
    }

    public static class Vertex{
        private int distance = 0;
        private int number=0;
        private Vertex parent = null;
        private int tabVertex[][]; // w tablicy znajduja sie sasiedzi wierzcholka
        private int i=0;  //wskaznik w tablicy

        Vertex(int number,int t){
            tabVertex=new int[t][2];
            this.number=number;
        }
        public int[] getTabVertex(int i){
            return tabVertex[i];
        }
        public void addNode(int v,int w){
            tabVertex[i][0]=v;
            tabVertex[i][1]=w;
            i++;
        }
        public int getNumber(){
            return number;
        }
        public void show(){
            for(int i=0;i<tabVertex.length;i++){
                System.out.println(tabVertex[i][0]+" "+tabVertex[i][1]);
            }
        }
        public int getLengthOfTab(){
            return tabVertex.length;
        }

       public void setVertex(Vertex parent,int distance){
            this.distance=distance;
            this.parent=parent;
        }
        public void setParent(Vertex n){ parent=n;  }
        public Vertex getParent(){
            return parent;
        }
        public void setDistance(int x){
            distance=x;
        }
        public int getDistance(){ return distance; }
    }
}
