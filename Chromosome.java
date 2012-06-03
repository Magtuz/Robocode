import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
//import java.util.Collections;
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
	
										
	static void generate_battle() 		//создаем файл battle, в который записываем параметры боя	
	{
		
		BufferedWriter out;
		try 
	    {  	    
		out = new BufferedWriter(new FileWriter("battles/bat.battle"));
		out.write("robocode.battleField.width=800\n");
		out.write("robocode.battleField.height=600\n");
		out.write("robocode.battle.numRounds=3\n");
		out.write("robocode.battle.gunCoolingRate=0.1\n");
		out.write("robocode.battle.rules.inactivityTime=450\n");																			
		out.write("robocode.battle.selectedRobots=EMK.Predator*,sample.Walls\n");//Dlink.Annarobot*
		System.out.println("Записали battle\n");
		out.close();
	    }
	    catch (IOException e)    { System.out.println("файл battle.txt не открывается\n");  }
	} 
	
	
	
			
	
	
	public static int generation=0;
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) 
	{
	
	int max_fitness=0;
	Specimen specimen,best;
	specimen= new Specimen();
	best= new Specimen();
	Helper.iniPopulation();
	if(!Helper.scanGen()){
		Helper.Init();					//создание нулевого поколения
	}	
	System.out.println("ПОКОЛЕНИЕ инициаллизировано ");
	Helper.selection();			//турнир
	best=Helper.find_max_fitness(Helper.Hlist); //находим лучшую особь
	System.out.println("Селекция ");
	System.out.println("Размер "+ Helper.Hlist.size());
	Helper.destroy();				//разбока
	System.out.println("Разрушено ");
	Helper.crossandmut();			//скрещивание и мутация
			
			
	generation++;
	
//////////////  ЗАПУСК ЭВОЛЮЦИИ   ///////////////////////////////////	
	
	while( max_fitness < Helper.MAX_ENOUGH && generation < Helper.MAX_COUNT){
		
		for(int i=0;i<Helper.POPULATION_COUNT;i++)
			for(int j=0;j<Helper.POPULATION_COUNT;j++)
				for(int k=0;k<Helper.POPULATION_COUNT;k++)
					for(int z=0;z<Helper.POPULATION_COUNT;z++){
						specimen= new Specimen();
						specimen.setIndiv(0,Helper.getShotAngle(i));
						specimen.setIndiv(1,Helper.getMoveAngle(j));
						specimen.setIndiv(2,Helper.getShotEnergy(k));
						specimen.setIndiv(3,Helper.getShotEnergy(z));
						specimen.writetofile();	
						generate_battle();
						
								//Runtime.getRuntime().exec("cmd /c start c:/robocode/robo.bat");	//запуск робокода для теста робота
								//Runtime.getRuntime().exec("cmd /c  c:/robocode/robo.bat");
							runProgramAndWait();	
						specimen.scan_result();								//узнаем значение приспособленности
						Helper.Hlist.add(specimen); 						//добавление особи в файл
					}
		for(int f=0;f<Helper.Hlist.size();f++)
			System.out.println("итого:"+Helper.Hlist.get(f).getFitness());
		//Helper.Hlist.add(best);	//выбираем особь с лучшей приспособленностью с предыдущего поколения и добавляем в новое чтобы гены не затерялись  
		max_fitness=Helper.maxfitness();//нужно узнать лучшую и среднию приспособленность на данный момент
		best=Helper.find_max_fitness(Helper.Hlist); //находим лучшую особь
		Helper.selection();			//турнир
		
		Helper.destroy();				//разбока
		Helper.crossandmut();
		System.out.println("ПОКОЛЕНИЕ " + (++generation));
		System.out.println("Лучшая приспособленность " + max_fitness + " Приспособленность лучшего " + best.getFitness() );
	
						
	}
		

	best.writetofile();//нужно записать лучшего в файлж	
}
	

static class Helper{
	  		
	public static int AVERAGE_ENOUGH=80; 
	public static int MAX_ENOUGH=90;
	static int MAX_COUNT=2;	
	public static int POPULATION_COUNT = 2 ;
	public static double max_fitness = 0;
	public static double average_fitness = 0;
	public static float MUTATION_LIKELIHOOD= 20.0F;
	public static int GENE_MIN = 0; 
	public static int GENE_MAX = 29;
	public static int GENES_COUNT = 30;
	public static int CountSpec=16;
	public static ArrayList<Specimen> Hlist = new ArrayList<Specimen>();
	public static ArrayList<Specimen> Tmplist = new ArrayList<Specimen>();
	public static ArrayList<Specimen> Nlist = new ArrayList<Specimen>();
	private static Chromosome popShotAngle[]=new Chromosome[Helper.POPULATION_COUNT];
	private static Chromosome popMoveAngle[]=new Chromosome[Helper.POPULATION_COUNT];
	private static Chromosome popShotEnergy[]=new Chromosome[Helper.POPULATION_COUNT];
	private static Chromosome popMoveDistance[]=new Chromosome[Helper.POPULATION_COUNT];
	
	public static int[] getShotAngle(int i) {
		return popShotAngle[i].getGenes();
	}
	
	public static void setShotAngle(int i,int[] genes) {
		popShotAngle[i].setGenes(genes);
	}
	
	public static int[] getMoveAngle(int i) {
		return popMoveAngle[i].getGenes();
	}
	
	public static void setMoveAngle(int i,int[] genes) {
		popMoveAngle[i].setGenes(genes);
	}
	
	public static int[] getShotEnergy(int i) {
		return popShotEnergy[i].getGenes();
	}
	
	public static void setShotEnergy(int i,int[] genes) {
		popShotEnergy[i].setGenes(genes);
	}
	
	public int[] getMoveDistance(int i) {
		return popMoveDistance[i].getGenes();
	}
	
	public static void setMoveDistance(int i,int[] genes) {
		popMoveDistance[i].setGenes(genes);
	}
	
	public static int getRandomInt( int min, int max ){
		Random random;
		random = new Random();
		return  random.nextInt( max+1 ) + min ;
	}
	
	/*public static void permutation()			//помещает особь с лучшей приспособленностью в конец, её мы не мутируем
	{	Specimen tmp;
		int max=0;
		int j=0;
		for(int i=0;i<Hlist.size();i++)
			if(Hlist.get(i).getFitness()>max){
				max=Hlist.get(i).fitness;
				j=i;
			}
		tmp=Hlist.get(Hlist.size()-1);
		Hlist.set(Hlist.size()-1,Hlist.get(j));
		Hlist.set(j, tmp);
	}*/
	
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
	private static void  iniPopulation(){		//создание популяциии 0-го поколения
		for (int i = 0; i<POPULATION_COUNT;++i){
			popShotAngle[i]=new Chromosome();
			popMoveAngle[i]=new Chromosome();
			popShotEnergy[i]=new Chromosome();
			popMoveDistance[i]=new Chromosome();
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
						specimen =new Specimen();
						specimen.setIndiv(0,Helper.getShotAngle(i));
						specimen.setIndiv(1,Helper.getMoveAngle(j));
						specimen.setIndiv(2,Helper.getShotEnergy(k));
						specimen.setIndiv(3,Helper.getShotEnergy(z));
						specimen.writetofile();	
						generate_battle();
						
							 //Runtime.getRuntime().exec("cmd /c start c:/robocode/robo.bat");
							//Runtime.getRuntime().exec("cmd /c server.bat", null, new File(" c:/robocode/robo.bat"));
							runProgramAndWait();
						specimen.scan_result();//узнаем значение приспособленности
						Hlist.add(specimen);
					}
	}
	
	
	static void selection() 		// используем турнирный метод селекции сначала делим все особи на пары по 5 выбираем лучшую.
	{	int k=0;
		int i;
		int j=POPULATION_COUNT-1;
		System.out.println("Размер "+ Helper.Hlist.size());
		while(k<3)
		{	/*if(k==0){
				for(i=0;i<(Hlist.size()-(POPULATION_COUNT+1));i+=POPULATION_COUNT){	
					while(j>=0){
						Tmplist.add(Hlist.get(i+j));
						j--;
					}
					Nlist.add(find_max_fitness(Tmplist));
					Tmplist.clear();
					j=POPULATION_COUNT-1;
				}
				for(i=(Hlist.size()-(POPULATION_COUNT+1));i<Hlist.size();i++)	
						Tmplist.add(Hlist.get(i));
					Nlist.add(find_max_fitness(Tmplist));
					Tmplist.clear();
			}else{*/
				
				for(i=0;i<Hlist.size();i+=POPULATION_COUNT){	
					while(j>=0){
						Tmplist.add(Hlist.get(i+j));
						j--;
					}
					Nlist.add(find_max_fitness(Tmplist));
					Tmplist.clear();
					j=POPULATION_COUNT-1;		
				}
			//}	
			Hlist.clear();
			for(int l=0;l<Nlist.size();l++)
			Hlist.add(Nlist.get(l));
			Nlist.clear();
			++k;
			System.out.println("Размер "+ Helper.Hlist.size());
		}
			
	}
	static Specimen find_max_fitness(ArrayList<Specimen> list) //возвращает особь с самой большой приспособленностью
	{	int max=0;
		int j=0;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getFitness()>max){
				max=list.get(i).getFitness();
				j=i;
			}
		}	
		return list.get(j);		
	}
	
	public static void destroy(){								//разбирает особи на хромосомы
		for(int i=0;i<POPULATION_COUNT;i++){
			setShotAngle(i,Hlist.get(i).getIndiv(0));
			setMoveAngle(i,Hlist.get(i).getIndiv(1));
			setShotEnergy(i,Hlist.get(i).getIndiv(2));
			setMoveDistance(i,Hlist.get(i).getIndiv(3));
		}
		Hlist.clear();							//чистим список перед новым созданием особей
	}	
	static void crossandmut(){										//скрещиваем хромосомы попарно и мутируем их
		for(int i=0;i<POPULATION_COUNT-1;i++){
			popShotAngle[i].doubleCrossover(popShotAngle[i+1]);
			popMoveAngle[i].doubleCrossover(popMoveAngle[i+1]);
			popShotEnergy[i].doubleCrossover(popShotEnergy[i+1]);
			popMoveDistance[i].doubleCrossover(popMoveDistance[i+1]);
			i++;
		}
		for(int i=0;i<POPULATION_COUNT;i++){
			popShotAngle[i].mutation();
			popMoveAngle[i].mutation();
			popShotEnergy[i].mutation();
			popMoveDistance[i].mutation();
		}	
	}
		static int maxfitness(){
			int max=0;
			int j=0;
			for(int i=0;i<Hlist.size();i++){
				if((Hlist.get(i).getFitness())>max){
					max=Hlist.get(i).getFitness();
					j=i;
				}	
			}
			
			return Hlist.get(j).getFitness();
			}
		
		static boolean scanGen(){
			Specimen specimen ;
			String[] bufer = new String[120];
			String[] str1 = new String[GENES_COUNT] ;
			String[] str2 = new String[GENES_COUNT] ;
			String[] str3 = new String[GENES_COUNT] ;
			String[] str4 = new String[GENES_COUNT];
			int[] in1=new int[GENES_COUNT];
			int[] in2=new int[GENES_COUNT];
			int[] in3=new int[GENES_COUNT];
			int[] in4=new int[GENES_COUNT];
			String line;
			try {
		    	//открываем хромосомы
				File file = new File("EMK/base/best.bs");
				if(file.length()==0){
					return false;
				}	
				FileReader r=new FileReader(file);
		       // BufferedReader read = new BufferedReader(new FileReader("C:\\Users\\Admin.Admin-ПК\\workspace\\Predator\\bin\\EMK\\base\\best.bs"));
				BufferedReader read = new BufferedReader(r);
				//read.mark((int) file.length());
				
				
					line=read.readLine();//.split(" ");
					while(line!= null){
						//
						bufer=line.split(" ");
			       // if((bufer=read.readLine().split(" "))==null ){
			        //	return false;
			       // }	
			       // String[] bufer=read.readLine().split(" ");//считываем строку с 4 свойствами
			        
			        int j=0;
			        
			        for(int i = 0; i < GENES_COUNT; i++){
			        	str1[j]=bufer[i];
			        	j++;
			        }
			        j=0;
			        for(int i = GENES_COUNT; i < GENES_COUNT*2; i++){
			        	str2[j]=bufer[i];
			        	j++;
			        }
			        j=0;
			        for(int i = GENES_COUNT*2; i < GENES_COUNT*3; i++){
			        	str3[j]=bufer[i];
			        	j++;
			        }
			        j=0;
			        for(int i = GENES_COUNT*3; i < GENES_COUNT*4; i++){
			        	str4[j]=bufer[i];
			        	j++;
			        }
					//System.out.println(str1);
					for(int i = 0; i < GENES_COUNT; i++){						//конвертируем в массив чисел
						in1[i] = Integer.parseInt(str1[i]);
						in2[i] = Integer.parseInt(str2[i]);
						in3[i] = Integer.parseInt(str3[i]);
						in4[i] = Integer.parseInt(str4[i]);
					}
						specimen=new Specimen();
						specimen.setIndiv(0, in1) ;
						specimen.setIndiv(1, in2) ;
						specimen.setIndiv(2, in3) ;
						specimen.setIndiv(3, in4) ;
					Helper.Hlist.add(specimen);
					//bufer=read.readLine().split(" ");
					line=read.readLine();
					}
				if(Helper.Hlist.size()!=CountSpec){
					int count=Helper.Hlist.size();
					for(int z = count ;z<(CountSpec);z+=count)
						for(int k=0;k<count;k++)
							Helper.Hlist.add(Helper.Hlist.get(k));
				}
					
				//}while(Helper.Hlist.size()!=CountSpec);		
		        read.close();
			} 
		    catch (IOException exc) {
		    	System.out.println("Ошибка считывания");
		    	return false;
		    }
		    catch (NumberFormatException exc) {
		    	
		    	System.out.println("Ошибка! Считано не число");
		    	return false;
			}
		
			/*for(int c=0;c<CountSpec;c++){
				Helper.Hlist.add(specimen);
			}*/
			return true;
		}
	}

	 static boolean runProgramAndWait(){
		Process process;
		
		String[] command = { "cmd", "/C", "Start/Wait", "robo.bat" };//system.hostname!!!
		java.lang.Runtime runtime;
	
		try{
			runtime = Runtime.getRuntime();
			process = runtime.exec(command);
			process.waitFor();
			return true;
		}catch (InterruptedException e){
			return false;
		}
		catch (Exception e){
         return false;
		}
	}

	
	static class Specimen{										//служит для объединения хромосом в одной особи
		public int fitness;
		public int indiv[][]= new int[4][29];
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
				out = new BufferedWriter(new FileWriter("EMK/base/best.bs"));
				for(i = 0; i < 4; i++){
					for(j = 0; j < 30; j++){
						out.write((indiv[i][j])+" " );
					}
					//out.newLine();
				}
				//out.write(this.fitness);
				System.out.println("записали особь\n"); 
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
				fs= new FileInputStream("results.txt"); 
				br = new BufferedReader(new InputStreamReader(fs));
				for(i = 0; i < 2;i++) 
					br.readLine();
				line = br.readLine();
		    
			if((line.indexOf("Predator"))!=-1)
			{
				i=line.indexOf('%');
				if(i!=0){
					result=line.substring(i-2, i);
						if(result.indexOf("(")!=-1)							//если приспособленность = 0 нужно считать на символ меньше 
							result=line.substring(i-1, i);
					System.out.println("приспособленность " +(Integer.parseInt(result))+ " поколение " + generation);
					this.setFitness(Integer.parseInt(result));
				}
				fs.close();
				br.close();	
			}else{
					line = br.readLine();
					if(line.indexOf('%')!=-1){
						i=line.indexOf('%');
						if(i!=0){
							result=line.substring(i-2, i);
						if(result.indexOf("(")!=-1)
							result=line.substring(i-1, i);
						if(result.indexOf("00")!=-1)
							result=line.substring(i-3, i);
						System.out.println("приспособленность " +(Integer.parseInt(result))+ " поколение " + generation);
						this.setFitness(Integer.parseInt(result));
					}else	System.out.println("Непонятно, почему-то здесь ошибочка ");
				}
				fs.close();
				br.close();
			}
		    }
		    catch(FileNotFoundException e)
		    {
		    	System.out.println("Файл result не найден");
		    }
			 catch (IOException e) { 
			    	System.out.println("файл result.txt не открывается\n");  
			 }
			
			
	}						
		
	}	
}	
	
	









