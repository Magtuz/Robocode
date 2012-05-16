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
	
										
	static void generate_battle(int x1, int y1, int x2, int y2) 		//создаем файл my.battle, в который записываем параметры боя	
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
	
	int x1 = 0, y1 = 0, x2 = 0, y2 = 0,generation=0;
	Specimen specimen;
	Init();					//создание нулевого поколения
	selection();			//турнир
	destroy();				//разбока
	crossandmut();			//селекция и мутация
	//проверка условия завершения
	
	
	
		if(POSITIONS){
			x1 =25 + rand.nextInt(750);
			x2 = 25 + rand.nextInt(750);
			y1 = 25 + rand.nextInt(550);
			y2 = 25 + rand.nextInt(550);
		
			generate_battle(x1, y1, x2, y2);
			
			Runtime.getRuntime().exec("cmd /c robocode.bat");
			
		}	
		
			
	generation++;
	
//////////////  ЗАПУСК ЭВОЛЮЦИИ   ///////////////////////////////////	
	
	while( max_fitness < Helper.MAX_ENOUGH && generation < MAX_COUNT){
		
		for(int i=0;i<Helper.POPULATION_COUNT;i++)
			for(int j=0;j<Helper.POPULATION_COUNT;j++)
				for(int k=0;k<Helper.POPULATION_COUNT;k++)
					for(int z=0;z<Helper.POPULATION_COUNT;z++){
						specimen.setIndiv(0,Helper.getPop()[i]);
						specimen.setIndiv(1,Helper.getPop()[j]);
						specimen.setIndiv(2,Helper.getPop()[k]);
						specimen.setIndiv(3,Helper.getPop()[z]);
						specimen.writetofile();	
						generate_battle(int x1,int y1,int x2,int y2);
						Runtime.getRuntime().exec("cmd /c robocode.bat");	//запуск робокода для теста робота
						specimen.scan_result();								//узнаем значение приспособленности
						Hlist.add(specimen); 
					}	
		selection();			//турнир
		//нужно узнать лучшую и среднию приспособленность на данный момент
		destroy();				//разбока
		crossandmut();
		System.out.println("ПОКОЛЕНИЕ " + generation);
	
						
	}
		

	best_num = find_best(fitness, f);//нужно записать лучшего в файлж
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
	private static Chromosome population[]=new Chromosome[Helper.POPULATION_COUNT];
	private Chromosome population[]=new Chromosome[Helper.POPULATION_COUNT];
	private Chromosome population[]=new Chromosome[Helper.POPULATION_COUNT];
	private Chromosome population[]=new Chromosome[Helper.POPULATION_COUNT];
	
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
	private static void  createPopulation(){
		for (int i = 0; i<POPULATION_COUNT;++i){
			population[i]=new Chromosome();
			fillChromosome(population[i]);
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
						specimen.setIndiv(0,Helper.getPop()[i]);
						specimen.setIndiv(1,Helper.getPop()[j]);
						specimen.setIndiv(2,Helper.getPop()[k]);
						specimen.setIndiv(3,Helper.getPop()[z]);
						specimen.writetofile();	
						generate_battle(int x1,int y1,int x2,int y2);
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
				Tmplist.removeAll();
			}
			Hlist.removeAll();
			Hlist=Nlist;
			Nlist.removeAll();
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
			population[i]=Hlist.get(i).getIndiv(0);
			population[i]=Hlist.get(i).getIndiv(1);
			population[i]=Hlist.get(i).getIndiv(2);
			population[i]=Hlist.get(i).getIndiv(3);
		}
	}	
	static void crossandmut(){										//скрещиваем хромосомы попарно и мутируем их
		for(int i=0;i<POPULATION_COUNT;i++){
			population[i].doubleCrossover(population[i+1]);
			population[i].mutation();
			population[i+1].mutation();
			population[i].doubleCrossover(population[i+1]);
			population[i].mutation();
			population[i+1].mutation();
			population[i].doubleCrossover(population[i+1]);
			population[i].mutation();
			population[i+1].mutation();
			population[i].doubleCrossover(population[i+1]);
			population[i].mutation();
			population[i+1].mutation();
			i++;
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
			BufferedWriter out = new BufferedWriter(new FileWriter("chromos.bs"));
			try 
			{
				
				for(i = 0; i < 4; i++){
					for(j = 0; j < 30; j++){
						out.write((indiv[i][j])+" " );
					}
					out.newLine();
				}
		    }
		    catch (IOException e) { 
		    	System.out.println("файл chromos.bs не открывается\n");  
		    }
			out.close();
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
		    }
		 
		    catch(FileNotFoundException e)
		    {
		    	System.out.println("Файл не найден");
		    }
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
		
	}	
}	
	
	









