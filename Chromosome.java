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
	

												
	static void write_person(int[][] f) 			//������ ����� � ���� ��� ����������� ������������� � �����
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
	    catch (IOException e)    { System.out.println("���� chromos.bs �� �����������\n");  }
	}											
	static void generate_battle(int x1, int y1, int x2, int y2) 		//������� ���� my.battle, � ������� ���������� ��������� ���	
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
//																			
		out.write("robocode.battle.selectedRobots=sample.Hulk,sample.MyFirstRobot\n");
	    }
	    catch (IOException e)    { System.out.println("���� battle.txt �� �����������\n");  }
		
		out.close(battle);*/
	} 
	
	
	static double results_scan() 			//��������� ���� � ����������� ����� � ���������� �������� ������� �����������
	{
		 try
	       {
	         BufferedReader in = new BufferedReader(new FileReader("results.txt"));	 
	         String bbb = in.readLine();
		     
		    }
		 
	      catch(FileNotFoundException e)
	      {
	        System.out.println("���� �� ������");
	      }
			
				return 1;
	}											
	static int find_sum_and_max_fitness(double[] fitness, int[][][] f)
	{
		int i, j;
		int index = -1;
		
		
		max_fitness = 0;
		sum_fitness = 0;
		
		//���������
		for(i = 0; i < psize; i++){
			sum_fitness += fitness[i];
			if(fitness[i] >= max_fitness){
				max_fitness = fitness[i];
				index = i;
			}
		}
		
		average_fitness = sum_fitness/psize;
		
		
		
		
		
		//������������
		for(i = 0; i < psize; i++){
			fitness[i]*=100;
			fitness[i]/=sum_fitness;
		}	
		
		return index;
	}			
	static void selection(int elite, double[] fitness, int[][][] f, int[][][] f_n) 		
		int i, j, k;
		double rand, tmp;
		Random random = new Random();
		for(i = 0; i < psize; i++){
			System.out.println( "������ ������������������  " +i+1+fitness[i]);
		}
		System.out.println("\n");


		for(i = 0; i < psize; i++){
			if(i != elite){
				rand = (random.nextDouble())*100;
				for(k = 0, tmp = 0; tmp < rand;){
					tmp += fitness[k++];
		   
				}
				System.out.println("������ -> ����� | "+ i+(1+ k));
				
				//����������� � ����� ��������� �������� �������
				for(j = 0; j < size; j++){
					f_n[i][0][j] = f[k - 1][0][j];
					f_n[i][1][j] = f[k - 1][1][j];
					f_n[i][2][j] = f[k - 1][2][j];
					f_n[i][3][j] = f[k - 1][3][j];
				}
			} else{
				//������ �������� �� ����� �����!
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
	
	
	
	
	static void crossover(int elite, int[][][] f_n) 			//�����������
	{
		int i, j, k;
		i = 0;
		while(psize - i > 1){
		
			if(i == elite) i++;
			
			k = i + 1;
			if(k == elite) k++;

			//����
			for(j = 0; j < 4; j++){
				intersect(f_n[i][j],f_n[k][j]);
			}
			i+=2;
		}
	}								
	static void intersect(int[] one, int[] duo) 				//��������������� ������� ����������� ���������������� ����������� ���������� ���� ����� one � duo
	{
		int j;
		double rand;
		int num, num2;
		int tmp;
		Random random = new Random();
		rand = random.nextDouble()*100;
		if(rand < P){//�������� �����������

			//2-�������� ������������

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
				
				

		} else{//������������ �����������
			
			for(j = 0; j < size; j++){
				if(random.nextInt(100) < Q){
					tmp = one[j];
					one[j] = duo[j];
					duo[j] = tmp;
				}
			}	
		}
	}							
	static void mutation(int elite, int[][][] f_n)						//�������
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
	static void update(int[][][]f, int[][][]f_n) 						//��������� ���������
	{
		int i, j, k;
		for(i = 0; i < psize; i++)
			for(j = 0; j < 4; j++)
				for(k = 0; k < size; k++)
					f[i][j][k] = f_n[i][j][k];
	}									

	
	/////////////////////////////////////////////////////
	
	public static int main(String[] args) 
	{
	int i;
	double[] fitness;
	int[][] best;
	int[][][] f;
	int[][][] f_new;
	int best_num;
	Random rand = new Random();
	int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

	size = HEAD_SIZE * MAX_ARN + 1;
	
	best = new int[4];
	fitness = new double (psize);
	f = new int(psize);
	f_n = (int ***)malloc(sizeof(int**)*psize);
	init(best, fitness, f, f_new);
	try 
    {
        File file = new File("logger.txt");
        boolean success = file.createNewFile();
    
	
        BufferedWriter out = new BufferedWriter(new FileWriter("logger.txt"));
        
	}
    catch (IOException e)    { System.out.println("���� log.txt �� �����������\n"); }     
    
   
	
//////////////////// ������� ���������  ///////////////////////////////////////////////////////////////////////////////////////
	
	//out.write(loger, "--------------------------------------------------��������� %d-------------------------------------------------------------\n",generation);
	
	for(i = 0; i < psize; i++){//�� ���� ������ ���������
		System.out.println( "����� #%d:");
		System.out.println(i+1);
		
		build_person(f[i]); //������������� ����� �������� ���������
		write_person(f[i]); //�������� �� � ����
		
		if(POSITIONS){
			x1 =25 + rand.nextInt(750);
			x2 = 25 + rand.nextInt(750);
			y1 = 25 + rand.nextInt(550);
			y2 = 25 + rand.nextInt(550);
		
			generate_battle(x1, y1, x2, y2);
			
			Runtime.getRuntime().exec("cmd /c robocode.bat")
			
		}	
		
		
		
		generate_battle(x2, y2, x1, y1);
		
		
		Runtime.getRuntime().exec("cmd /c robocode.bat"); //��������� �������
		
		
		fitness[i] = results_scan(); //� ���������� ��� �����
		
		System.out.println("�����������������:"+fitness[i]);
	}
		
//////// �������� ����� �������� ���������� ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	best_num = find_sum_and_max_fitness(fitness, f);	//�������� ������ ������ ���������
	
	generation++;
	
/////////  ��������� ��������   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	while(average_fitness < AVERAGE_ENOUGH && max_fitness < MAX_ENOUGH && generation < MAX_COUNT){
	
		selection(best_num, fitness,f,f_new);	//��������
		crossover(best_num, f_new);				//�����������
		mutation(best_num, f_new);				//�������
		update(f, f_new);
		
		System.out.println("--------------------------------------------------��������� %d-------------------------------------------------------------\n"+generation);
	
		for(i = 0; i < psize; i++){//�� ���� ������ ���������
			System.out.println( "����� #%d: "+i+1);
			
			write_person(f[i]); //�������� ����� � ����
			
			x1 =25 + rand.nextInt(750);
			x2 = 25 + rand.nextInt(750);
			y1 = 25 + rand.nextInt(550);
			y2 = 25 + rand.nextInt(550);
		
				generate_battle(x1, y1, x2, y2);
			
				//out.close();
				Runtime.getRuntime().exec("cmd /c robocode.bat") //��������� �������
				
			}	
		
			generate_battle(x2, y2, x1, y1);
		
			//out.close();
			Runtime.getRuntime().exec("cmd /c robocode.bat"); //��������� �������
					
			fitness[i] = results_scan(); //� ���������� ��� �����
		
			System.out.println("%lf\n"+fitness[i]);
			
		}
		

		best_num = find_sum_and_max_fitness(fitness, f);
		generation ++;
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	
	//out.close();
	//clear_it(fitness, f, f_n);
	return 1;

	}
	
	

class Helper{
	  		
	public static int AVERAGE_ENOUGH=80; 
	public static int MAX_ENOUGH=90;
	public static int POPULATION_COUNT = 5;
	public static double max_fitness = 0;
	public static double average_fitness = 0;
	public static float MUTATION_LIKELIHOOD= 5.0F;
	public static int GENE_MIN = 1; 
	public static int GENE_MAX = 30;
	public static int HEAD_SIZE=10;	//������ ������ ������ 
	public static int MAX_ARN=4; 		//������������ ������� ������� ������� - 4 (� if)
	public static int GENES_COUNT = 50;
	private Chromosome population[]=new Chromosome[Helper.POPULATION_COUNT];
	
	public int getRandomInt( int min, int max ){
		Random random;
		random = new Random();
		return  random.nextInt( max+1 ) + min ;
	}
	
	public float getRandomFloat( float min, float max ){
		return  (float) (Math.random()*max + min) ;
	}
	
	public  int getRandomGene(){
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
	

  }		
}	
	
	









