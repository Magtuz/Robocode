import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;



public class Chromosome
{	
	private int genes[]=new int[Helper.GENES_COUNT];
	public int[] getGenes() {
		return genes;
	}
	public void setGenes(int[] genes) {
		this.genes = genes;
	}
	
	
	public Chromosome mutation(){    
	    Chromosome result =  (Chromosome ) this.clone();

	    for (int i=0;i<Helper.GENES_COUNT;++i){
		    float randomPercent = Helper.getRandomFloat(0,100);
		    if ( randomPercent < Helper.MUTATION_LIKELIHOOD ){
		         int newValue= 	Helper.getRandomGene();			
	 	         result.getGenes()[i] = newValue;       
		   }
	    }	
	           return result;		
	}
	
	
public Chromosome[] doubleCrossover(  Chromosome chromosome  ){
		
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
         return result;

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
	


	static int MAX_COUNT=50;	//если за 50 поколений ничего не вышло, то уж врядли выйдет
										
	static void write_person(int[][] f) 			//запиши особь в файл для последующей интерпретации в танке
	{
		int i, j;
		try 
	    {
	        File file = new File("chromos.bs");
	     
	    
	    BufferedWriter out = new BufferedWriter(new FileWriter("chromos.bs"));
		
		for(i = 0; i < 4; i++){
			for(j = 0; j < size; j++){
				out.write((f[i][j]) );
			}
			out.write("\n");
		}
	    }
	    catch (IOException e)    { System.out.println("файл chromos.bs не открывается\n");  }
	}											
	static void generate_battle(int x1, int y1, int x2, int y2) 		//создаем файл my.battle, в который записываем параметры боя	
	{
		
		
		try 
	    {
	        	    
	    BufferedWriter out = new BufferedWriter(new FileWriter("chromos.bs"));
		
		out.write("robocode.battleField.width=800\n");
		out.write("robocode.battleField.height=600\n");
		
		if(POSITIONS){
			out.write("robocode.battle.numRounds=100\n");
		} else{
			out.write("robocode.battle.numRounds=200\n");
		}
		
		out.write("robocode.battle.gunCoolingRate=0.1\n");
		out.write("robocode.battle.rules.inactivityTime=450\n");																			
		out.write("robocode.battle.selectedRobots=EMK.Predtor,sample.Wall\n");
	    }
	    catch (IOException e)    { System.out.println("файл battle.txt не открывается\n");  }
	} 
	
	
	static double results_scan() 			//считывает файл с результатми битвы и возвращает значение функции пригодности
	{
		

		try
	         {
		         BufferedReader in = new BufferedReader(new FileReader("results.txt"));
		         String bbb = in.readLine();
	        	 aaa.close();
	         }
		 
		 catch(FileNotFoundException e)
		 {
		 	System.out.println("Файл не найден");
		 }
				
				
				return 1;
	}												

	static int find_sum_and_max_fitness(double[] fitness, int[][][] f)
	{
		int i, j;
		int index = -1;
		
		
		max_fitness = 0;
		sum_fitness = 0;
		
		//суммируем
		for(i = 0; i < psize; i++){
			sum_fitness += fitness[i];
			if(fitness[i] >= max_fitness){
				max_fitness = fitness[i];
				index = i;
			}
		}
		
		average_fitness = sum_fitness/psize;
		
		
		
		
		
		//нормализация
		for(i = 0; i < psize; i++){
			fitness[i]*=100;
			fitness[i]/=sum_fitness;
		}	
		
		return index;
	}			
	
	static void selection(int elite, double[] fitness, int[][][] f, int[][][] f_n) 		//кто круче, тому и места больше
	{
		int i, j, k;
		double rand, tmp;
		Random random = new Random();
		for(i = 0; i < psize; i++){
			System.out.println( "индекс иприспособленности  " +i+1+fitness[i]);
		}
		System.out.println("\n");


		for(i = 0; i < psize; i++){
			if(i != elite){
				//получаем случайно плавающее число от 0 до 100
				rand = (random.nextDouble())*100;
				
				//и смотрим, в чью из особой популяции "попало" это число
				for(k = 0, tmp = 0; tmp < rand;){
					tmp += fitness[k++];
		   
				}
				System.out.println("индекс -> особь | "+ i+(1+ k));
				
				//подставляем в новую популяцию согласно селеции
				for(j = 0; j < size; j++){
					f_n[i][0][j] = f[k - 1][0][j];
					f_n[i][1][j] = f[k - 1][1][j];
					f_n[i][2][j] = f[k - 1][2][j];
					f_n[i][3][j] = f[k - 1][3][j];
				}
			} else{
				//лучший остается на своем месте!
				for(j = 0; j < size; j++){
					f_n[i][0][j] = f[i][0][j];
					f_n[i][1][j] = f[i][1][j];
					f_n[i][2][j] = f[i][2][j];
					f_n[i][3][j] = f[i][3][j];
				}
			}
		}
		System.out.println("\n");
	}	
	
	
	
	static void crossover(int elite, int[][][] f_n) 			//скрещивание
	{
		int i, j, k;
		i = 0;
		while(psize - i > 1){
		
			if(i == elite) i++;
			
			k = i + 1;
			if(k == elite) k++;

			//пары
			for(j = 0; j < 4; j++){
				intersect(f_n[i][j],f_n[k][j]);
			}
			i+=2;
		}
	}								
	static void intersect(int[] one, int[] duo) 				//вспомогательная функция скрещивания непосредственное скрещивание конкретных двух строк one и duo
	{
		int j;
		double rand;
		int num, num2;
		int tmp;
		Random random = new Random();
		rand = random.nextDouble()*100;
		if(rand < P){//кусочное скрещивание

			//2-точечная рекомбинация

			num = random.nextInt(size);
			num2 = random.nextInt(size);
			
			if(num2 > num){
				for(j = num; j <= num2; j++){
					tmp = one[j];
					one[j] = duo[j];
					duo[j] = tmp;
				}		
			} else if(num2 < num){
				for(j = 0; j <= num2; j++){
					tmp = one[j];
					one[j] = duo[j];
					duo[j] = tmp;
				}	
				for(j = num; j < size; j++){
					tmp = one[j];
					one[j] = duo[j];
					duo[j] = tmp;
				}	
				
			}
				
				

		} else{//поэлементное скрещивание
			
			for(j = 0; j < size; j++){
				if(random.nextInt(100) < Q){
					tmp = one[j];
					one[j] = duo[j];
					duo[j] = tmp;
				}
			}	
		}
	}							
	static void mutation(int elite, int[][][] f_n)						//мутация
	{
		int i, j, k;
		Random rand = new Random();
		for(i = 0; i < psize; i++){
				for(j = 0; j < 4; j++){
					for(k = 0; k < size; k++){
						if(	rand.nextDouble() < MUTATION){
							f_n[i][j][k] = (k < HEAD_SIZE) ? (rand.nextInt(24)) : (8+rand.nextInt(16));
						}
					}	

				}
		}
	}								
										

	
	/////////////////////////////////////////////////////
	
	public static int main(String[] args) 
	{
	
	int x1 = 0, y1 = 0, x2 = 0, y2 = 0;	
	Helper.Init();	///создание нулевого поколения
	 	///проверка условия завершения
		//турнир
		//разбока
		//селекция и мутация
		
	
	
		if(POSITIONS){
			x1 =25 + rand.nextInt(750);
			x2 = 25 + rand.nextInt(750);
			y1 = 25 + rand.nextInt(550);
			y2 = 25 + rand.nextInt(550);
		
			generate_battle(x1, y1, x2, y2);
			
			Runtime.getRuntime().exec("cmd /c robocode.bat");
			
		}	
		
		
		
		generate_battle(x2, y2, x1, y1);
		
		
		Runtime.getRuntime().exec("cmd /c robocode.bat"); //запустить просчет
		
		
		fitness[i] = results_scan(); //и посмотреть что вышло
		
		System.out.println("Приспособленность:"+fitness[i]);
	}
		
//////// ПОДВОДИМ ИТОГИ НУЛЕВОГО ПОКОЛЕНИИЯ ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	best_num = find_sum_and_max_fitness(fitness, f);	//получаем индекс лучшей хромосомы
	
	generation++;
	
/////////  ЗАПУСКАЕМ ЭВОЛЮЦИю   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	while(average_fitness < AVERAGE_ENOUGH && max_fitness < MAX_ENOUGH && generation < MAX_COUNT){
	
		selection(best_num, fitness,f,f_new);	//селекция
		crossover(best_num, f_new);				//скрещивание
		mutation(best_num, f_new);				//мутация
		update(f, f_new);
		
		System.out.println("--------------------------------------------------ПОКОЛЕНИЕ %d-------------------------------------------------------------\n"+generation);
	
		for(i = 0; i < psize; i++){//по всем особям поколения
			System.out.println( "Особь #%d: "+i+1);
			
			write_person(f[i]); //записать особь в файл
			
			x1 =25 + rand.nextInt(750);
			x2 = 25 + rand.nextInt(750);
			y1 = 25 + rand.nextInt(550);
			y2 = 25 + rand.nextInt(550);
		
				generate_battle(x1, y1, x2, y2);
			
				Runtime.getRuntime().exec("cmd /c robocode.bat"); //запустить просчет
				
			}	
		
			generate_battle(x2, y2, x1, y1);
		
			Runtime.getRuntime().exec("cmd /c robocode.bat"); //запустить просчет
					
			fitness[i] = results_scan(); //и посмотреть что вышло
		
			System.out.println("%lf\n"+fitness[i]);
			
		}
		

		best_num = find_sum_and_max_fitness(fitness, f);
		generation ++;
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	
	//out.close();
	//clear_it(fitness, f, f_n);
	return 1;

	}
	
	

static class Helper{
	  		
	public static int AVERAGE_ENOUGH=80; 
	public static int MAX_ENOUGH=90;
	public static int POPULATION_COUNT = 8;
	public static double max_fitness = 0;
	public static double average_fitness = 0;
	public static float MUTATION_LIKELIHOOD= 5.0F;
	public static int GENE_MIN = 1; 
	public static int GENE_MAX = 30;
	public static int HEAD_SIZE=10;	//размер головы строки 
	public static int MAX_ARN=4; 		//максимальная арность базовой функции - 4 (у if)
	public static int GENES_COUNT = 50;
	private Chromosome population[]=new Chromosome[Helper.POPULATION_COUNT];
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
	
	private void fillChromosome( Chromosome chromosome ){
		for (int i=0;i<GENES_COUNT;++i){
			chromosome.getGenes()[i]=getRandomGene();			
		}
	}	
	private void createPopulation(){
		for (int i = 0; i<POPULATION_COUNT;++i){
			population[i]=new Chromosome();
			fillChromosome(population[i]);
		}		
				
	}
	private void addList(Specimen specimen)///добавление особей в список
	{
		//////////////////////////////////////////
	}
	
	private static void Init() {
		Specimen specimen;
		createPopulation();
		for(int i=0;i<POPULATION_COUNT;i++)
			for(int j=0;j<POPULATION_COUNT;j++)
				for(int k=0;k<POPULATION_COUNT;k++)
					for(int z=0;z<POPULATION_COUNT;z++)
					{
						specimen.setIndiv(0,Helper.getPop()[i];
						specimen.setIndiv(1,Helper.getPop()[j];
						specimen.setIndiv(2,Helper.getPop()[k];
						specimen.setIndiv(3,Helper.getPop()[z];
					}
		write(speciment);////////////////////реализовать
		generate_battle(int x1,int y1,int x2,int y2);
		Runtime.getRuntime().exec("cmd /c robocode.bat");
		addList(specimen);
  }

	}
	class Specimen{										//служит для объединения хромомом в одной особи
		public float fitness;
		public int indiv[][]= new int[4][30];
		public float getFitness() {
			return this.fitness;
		}
		public void setFitness(float fitness) {
			this.fitness = fitness;
		}
		public int[] getIndiv(int i) {
			return indiv[i];
		}
		public void setIndiv(int i,int[] indiv) {
			this.indiv[i] = indiv;
		}
	}	
}	
	
}	









