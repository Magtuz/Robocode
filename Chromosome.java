import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.ArrayList;


public class Chromosome
{	
	private int genes[]=new int[Helper.GENES_COUNT];
	public int[] getGenes() {
		return genes;
	}
	public void setGenes(int[] genes) {
		this.genes = genes;
	}
	
	
	public void mutation(){    
	    Chromosome result =  (Chromosome ) this.clone();

	    for (int i=0;i<Helper.GENES_COUNT;++i){
		    float randomPercent = Helper.getRandomFloat(0,100);
		    if ( randomPercent < Helper.MUTATION_LIKELIHOOD ){
		         int newValue= 	Helper.getRandomGene();			
	 	         result.getGenes()[i] = newValue;       
		   }
	    }
	    this.setGenes(result.getGenes());
	}
	
	
public void doubleCrossover(  Chromosome chromosome  ){
		
		int crossoverline=getRandomCrossoverLine();
		Chromosome[] result = new Chromosome[2];
		result[0]=new Chromosome();
		result[1]=new Chromosome();

		for (int i=0;i<Helper.GENES_COUNT;++i){
			if ( i<=crossoverline){
				result[0].getGenes()[i] =  this.getGenes()[i];
				result[1].getGenes()[i] =  chromosome.getGenes()[i];

			}
		    
		   else {
			result[0].getGenes()[i] =  chromosome.getGenes()[i];
			result[1].getGenes()[i] =  this.getGenes()[i]; 			
		    }

		}
		chromosome.setGenes(result[0].getGenes());
		this.setGenes(result[1].getGenes());

	}
	private static int getRandomCrossoverLine(){
		int line = Helper.getRandomInt(0, Helper.GENES_COUNT - 2); 
		return line;
	}
	
	protected Object clone()  {
		Chromosome resultChromosome = new Chromosome() ;
		int resultGenes[]=new int[Helper.GENES_COUNT];
		resultGenes=this.genes.clone();  
		resultChromosome.setGenes(resultGenes);
		return resultChromosome;
	}
	
										
	static void generate_battle() 		//создаем файл my.battle, в который записываем параметры боя	
	{
		
		
		try 
	    {
	        	    
	    BufferedWriter out = new BufferedWriter(new FileWriter("chromos.bs"));
		
		out.write("robocode.battleField.width=800\n");
		out.write("robocode.battleField.height=600\n");
		out.write("robocode.battle.numRounds=20\n");
		out.write("robocode.battle.gunCoolingRate=0.1\n");
		out.write("robocode.battle.rules.inactivityTime=450\n");																			
		out.write("robocode.battle.selectedRobots=EMK.Predtor,sample.Wall\n");
	    }
	    catch (IOException e)    { System.out.println("файл battle.txt не открывается\n");  }
	} 
	
	
	
			
	
	
	
	
	/////////////////////////////////////////////////////
	
	public static int main(String[] args) 
	{
	
	int x1 = 0, y1 = 0, x2 = 0, y2 = 0,generation=0,max_fitness=0;
	Specimen specimen,best;
	Helper.Init();					//создание нулевого поколения
	Helper.selection();			//турнир
	Helper.destroy();				//разбока
	Helper.crossandmut();			//селекция и мутация
			
			
	generation++;
	
//////////////  ЗАПУСК ЭВОЛЮЦИИ   ///////////////////////////////////	
	
	while( max_fitness < Helper.MAX_ENOUGH && generation < Helper.MAX_COUNT){
		
		for(int i=0;i<Helper.POPULATION_COUNT;i++)
			for(int j=0;j<Helper.POPULATION_COUNT;j++)
				for(int k=0;k<Helper.POPULATION_COUNT;k++)
					for(int z=0;z<Helper.POPULATION_COUNT;z++){
						specimen.setIndiv(0,Helper.getShotAngle()[i]);
						specimen.setIndiv(1,Helper.getMoveAngle()[j]);
						specimen.setIndiv(2,Helper.getShotEnergy()[k]);
						specimen.setIndiv(3,Helper.getShotEnergy()[z]);
						specimen.writetofile();	
						generate_battle();
						Runtime.getRuntime().exec("cmd /c robocode.bat");	//запуск робокода для теста робота
						specimen.scan_result();								//узнаем значение приспособленности
						Helper.Hlist.add(specimen); 
					}	
		Helper.selection();			//турнир
		max_fitness=Helper.maxfitness();//нужно узнать лучшую и среднию приспособленность на данный момент
		best=Helper.find_max_fitness(Helper.Hlist);
		Helper.destroy();				//разбока
		Helper.crossandmut();
		System.out.println("ПОКОЛЕНИЕ " + generation);
	
						
	}
		

	best.writetofile();//нужно записать лучшего в файлж
	return 1;

	
}
	

static class Helper{
	  		
	public static int AVERAGE_ENOUGH=80; 
	public static int MAX_ENOUGH=90;
	static int MAX_COUNT=50;	
	public static int POPULATION_COUNT = 8;
	public static double max_fitness = 0;
	public static double average_fitness = 0;
	public static float MUTATION_LIKELIHOOD= 5.0F;
	public static int GENE_MIN = 1; 
	public static int GENE_MAX = 30;
	public static int HEAD_SIZE=10;	//размер головы строки 
	public static int MAX_ARN=4; 		//максимальная арность базовой функции - 4 (у if)
	public static int GENES_COUNT = 30;
	public static ArrayList<Specimen> Hlist = new ArrayList<Specimen>();
	public static ArrayList<Specimen> Tmplist = new ArrayList<Specimen>();
	public static ArrayList<Specimen> Nlist = new ArrayList<Specimen>();
	private static Chromosome popShotAngle[]=new Chromosome[Helper.POPULATION_COUNT];
	private static Chromosome popMoveAngle[]=new Chromosome[Helper.POPULATION_COUNT];
	private static Chromosome popShotEnergy[]=new Chromosome[Helper.POPULATION_COUNT];
	private static Chromosome popMoveDistance[]=new Chromosome[Helper.POPULATION_COUNT];
	
	public int[] getShotAngle() {
		return popShotAngle;s
	}
	
	public static void setShotAngle(int[] genes) {
		popShotAngle = genes;
	}
	
	public int[] getMoveAngle() {
		return popMoveAngle;
	}
	
	public static void setMoveAngle(int[] genes) {
		this.popMoveAngle = genes;
	}
	
	public int[] getShotEnergy() {
		return popShotEnergy;
	}
	
	public static void setShotEnergy(int[] genes) {
		this.popShotEnergy = genes;
	}
	
	public int[] getMoveDistance() {
		return popMoveDistance;
	}
	
	public static void setMoveDistance(int[] genes) {
		this.popMoveDistance = genes;
	}
	
	public static int getRandomInt( int min, int max ){
		Random random;
		random = new Random();
		return  random.nextInt( max+1 ) + min ;
	}
	
	public static float getRandomFloat( float min, float max ){
		return  (float) (Math.random()*max + min) ;
	}
	
	public static  int getRandomGene(){
		return getRandomInt( GENE_MIN , GENE_MAX);
	}
	
	private static void fillChromosome( Chromosome chromosome ){
		for (int i=0;i<GENES_COUNT;++i){
			chromosome.getGenes()[i]=getRandomGene();			
		}
	}	
	private static void  createPopulation(){		//создание популяциии 0-го поколения
		for (int i = 0; i<POPULATION_COUNT;++i){
			popShotAngle[i]=new Chromosome();
			fillChromosome(popShotAngle[i]);
			popMoveAngle[i]=new Chromosome();
			fillChromosome(popMoveAngle[i]);
			popShotEnergy[i]=new Chromosome();
			fillChromosome(popShotEnergy[i]);
			popMoveDistance[i]=new Chromosome();
			fillChromosome(popMoveDistance[i]);
		}		
				
	}
	
	
	public static void Init() 
	{
		Specimen specimen =new Specimen();
		createPopulation();
		for(int i=0;i<POPULATION_COUNT;i++)
			for(int j=0;j<POPULATION_COUNT;j++)
				for(int k=0;k<POPULATION_COUNT;k++)
					for(int z=0;z<POPULATION_COUNT;z++){
						specimen.setIndiv(0,Helper.getShotAngle()[i]);
						specimen.setIndiv(1,Helper.getMoveAngle()[j]);
						specimen.setIndiv(2,Helper.getShotEnergy()[k]);
						specimen.setIndiv(3,Helper.getShotEnergy()[z]);
						specimen.writetofile();	
						generate_battle();
						Runtime.getRuntime().exec("cmd /c robocode.bat");
						specimen.scan_result();//узнаем значение приспособленности
						Hlist.add(specimen);
					}
	}
	
	
	static void selection() 		// используем турнирный метод селекции сначала делим все особи на пары по 2 выбираем лучшую, затем пары по три и т.д.
	{	int k=2;
		int j=2;
		while(k<=8)
		{
			for(int i=0;i<Hlist.size();){	
				while(j>0){
					Tmplist.add(Hlist.get(i));
					j--;
				}
				Nlist.add(find_max_fitness(Tmplist));
				j=k;
				i+=k;
				Tmplist.clear();
			}
			Hlist.clear();
			Hlist=Nlist;
			Nlist.clear();
			k++;
		}
			
	}
	static Specimen find_max_fitness(ArrayList<Specimen> list) ////возвращает особь с самой большой приспособленностью
	{	int max=0;
		for(int i=0;i<list.size();i++)
			if(list.get(i).fitness>max)
				max=i;
		return list.get(max);		
	}
	
	public static void destroy(){								//разбирает особи на хромосомы
		for(int i=0;i<POPULATION_COUNT;i++){
			setShotAngle(Hlist.get(i).getIndiv(0));
			setMoveAngle(Hlist.get(i).getIndiv(1));
			setShotEnergy(Hlist.get(i).getIndiv(2));
			setMoveDistance(Hlist.get(i).getIndiv(3));
		}
	}	
	static void crossandmut(){										//скрещиваем хромосомы попарно и мутируем их
		for(int i=0;i<POPULATION_COUNT;i++){
			popShotAngle[i].doubleCrossover(popShotAngle[i+1]);
			popShotAngle[i].mutation();
			popShotAngle[i+1].mutation();
			popMoveAngle[i].doubleCrossover(popMoveAngle[i+1]);
			popMoveAngle[i].mutation();
			popMoveAngle[i+1].mutation();
			popShotEnergy[i].doubleCrossover(popShotEnergy[i+1]);
			popShotEnergy[i].mutation();
			popShotEnergy[i+1].mutation();
			popMoveDistance[i].doubleCrossover(popMoveDistance[i+1]);
			popMoveDistance[i].mutation();
			popMoveDistance[i+1].mutation();
			i++;
		}	
	}
		static int maxfitness(){
			int max=0;
			for(int i=0;i<Hlist.size();i++)
				if((Hlist.get(i).fitness)>max)
					max=i;
			return Hlist.get(max).getFitness();
		}
		
}
	

	
	static class Specimen{										//служит для объединения хромосом в одной особи
		public int fitness;
		public int indiv[][]= new int[4][30];
		public int getFitness() {
			return this.fitness;
		}
		public void setFitness(int fitness) {
			this.fitness = fitness;
		}
		public int[] getIndiv(int i) {
			return indiv[i];
		}
		public void setIndiv(int i,int[] indiv) {
			this.indiv[i] = indiv;
		}
		
		public void writetofile()	//запиши особь в файл для последующей интерпретации в танке
		{
			int i, j;
			BufferedWriter out;
			try 
			{
				out = new BufferedWriter(new FileWriter("chromos.bs"));
				for(i = 0; i < 4; i++){
					for(j = 0; j < 30; j++){
						out.write((indiv[i][j])+" " );
					}
					out.newLine();
				}
				out.close();
		    }
		    catch (IOException e) { 
		    	System.out.println("файл chromos.bs не открывается\n");  
		    }
			
		}
		
		public void scan_result() {					//узнаем значение приспособленности
			String line,result;
			int i;
			FileInputStream fs;
			BufferedReader br;
			try
		    {
				fs= new FileInputStream("result.txt"); 
				br = new BufferedReader(new InputStreamReader(fs));
				for(i = 0; i < 2; ++i) 
					br.readLine();
				line = br.readLine();
		    
			if((line.indexOf("Predator"))!=-1)
			{
				i=line.indexOf('%');
				if(i!=0){
					result=line.substring(i-2, i);
					this.setFitness(Integer.parseInt(result));
				}
					
			}else{
				line = br.readLine();
				i=line.indexOf('%');
				if(i!=0){
					result=line.substring(i-2, i);
					this.setFitness(Integer.parseInt(result));
				}
			}
		    }
			 
		    catch(FileNotFoundException e)
		    {
		    	System.out.println("Файл не найден");
		    }
			 catch (IOException e) { 
			    	System.out.println("файл chromos.bs не открывается\n");  
			 }
			
	}						
		
	}	
}	
	
	









