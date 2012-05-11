import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;



public class Genetik
{	
	public
	static int size; //����� ���������
	static int generation = 0;
	static double max_fitness = 0;
	static double average_fitness = 0;
	static double sum_fitness = 0;
	static int P=40; 			//% ��� ����������� ����� ��������, � �� ������������
	static int Q = 50; 			//% ����������� ��������� � ������������ �����������
	static int MUTATION = 4; 		//% - ����������� ������� � ���������� ����
	static boolean POSITIONS =false; // ��� ���� ����: FALSE - ������ ����� ��� ��������� ��������� �������� ������. 
							// TRUE - ����� ���� ������������ ��������� ��������� �������, ������ �������� n ������� �� ����� ��������, �������� ������� � �������� ��� n �������

	static int HEAD_SIZE=10;	//������ ������ ������ (������ ��� ������������ karva-��������)
	static int MAX_ARN=4; 		//������������ ������� ������� ������� - 4 (� if)
	static int psize=51;

	static int MAX_COUNT=50;	//���� �� 50 ��������� ������ �� �����, �� �� ������ ������
	static int AVERAGE_ENOUGH=80; //������� ����������������� ������ ��������� ������ 50? ����������
	static int MAX_ENOUGH=90;	//������������ ����������������� ����� ��������� ������ 70? ����������
	static void init(int[][] best, double[] fitness, int[][][] f, int[][][] f_n) 
	{
		//int i, j;
		best = new int[4][size];
		f=new int[psize][4][size];
	}
	static void build_person(int[][] f) 						//��������� ����� ��� ��������� ���������
	{
		int i, j;
		Random rand = new Random();
		for(i = 0; i < 4; i++){
			for(j = 0; j < HEAD_SIZE; j++){
				f[i][j] = rand.nextInt(24);
			}
			for(; j < size; j++){
				f[i][j] = 8 + rand.nextInt(16);
			}	
		}
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
//																			####||########################################
//																			####|| ����� ����� �������� �����     ########
//																			####\/########################################
		out.write("robocode.battle.selectedRobots=sample.Hulk,sample.MyFirstRobot\n");
	    }
	    catch (IOException e)    { System.out.println("���� battle.txt �� �����������\n");  }
		/*if(POSITIONS){
			out.write("robocode.battle.initialPositions=(%d,%d,?),(%d,%d,?)\n", x1, y1, x2, y2);
		}
		out.close(battle);*/
	} 
	
	
	static double results_scan() 			//��������� ���� � ����������� ����� � ���������� �������� ������� �����������
	{
		
				int my_score, enemy_score, my_win, win, enemy_win, my_score2, enemy_score2, tmp, tmp2;
				char* name = (char*)malloc(40);

				if((results = fopen ("results.txt","r")) == NULL){
					printf("���� results.txt �� �����������\n");exit(1);
				}
				 try
		         {
		         BufferedReader in = new BufferedReader(new FileReader("results.txt"));
		 
		         String bbb = in.readLine();
		         aaa.close();
		         }
		 
		         catch(FileNotFoundException e)
		         {
		             System.out.println("���� �� ������");
		         }
				
					fclose(results);

					out.write(+my_score +enemy_score);
					out.write(+my_score2+ enemy_score2);

					my_score += my_score2;
					enemy_score += enemy_score2;
				}
				
				out.write("total:"+my_score+ enemy_score+(100*(double)my_score/(my_score + enemy_score)));

				free(name);
				*/
				return 100;//*(double)my_score/(my_score + enemy_score);
	}												//����������� �� ����� ����������� ��� ������� ������� �����������������
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
	}			//������� ��������� ���������
	
	/*
	�������� - ���������� �� ��������� ���� ��� ����������.

	elite - ����� ������ �����, ��� �������� �� ����� �����
	fitness[����� �����] - ������ ������������������ ������
	f[����� �����][����� ���������][����� �������] - ������ ������
	f_n - �� �� �����, ������ ����� ���������
	*/
	static void selection(int elite, double[] fitness, int[][][] f, int[][][] f_n) 		//��� �����, ���� � ����� ������
	{
		int i, j, k;
		double rand, tmp;
		Random random = new Random();
		for(i = 0; i < psize; i++){
			System.out.println( "������ ������������������  " +i+1+fitness[i]);
		}
		System.out.println("\n");


		for(i = 0; i < psize; i++){
			if(i != elite){
				//�������� �������� ��������� ����� �� 0 �� 100
				rand = (random.nextDouble())*100;
				
				//� �������, � ��� �� ������ ��������� "������" ��� �����
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
	
	
	
	/*
	��������� - ����������� ������ ���������

	elite - ����� ������ �����, ��� �������� �� ����� �����
	f_n - ������ ������ ���������
	*/
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
				crossover_small(f_n[i][j],f_n[k][j]);
			}
			i+=2;
		}
	}								
	static void crossover_small(int[] one, int[] duo) 				//��������������� ������� ����������� ���������������� ����������� ���������� ���� ����� one � duo
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
			
			//system("sh my2.sh");
			
		}	
		
		
		
		generate_battle(x2, y2, x1, y1);
		
		
		//system("sh my.sh"); //��������� �������
		
		
		fitness[i] = results_scan(); //� ���������� ��� �����
		
		System.out.println("�����������������:"+fitness[i]);
	}
		
//////// �������� ����� �������� ���������� ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	best_num = find_sum_and_max_fitness(fitness, f);	//�������� ������ ������ ���������
	
	generation++;
	
/////////  ��������� ����� ��������   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
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
				//system("sh my2.sh"); //��������� �������
				//if((loger = fopen ("log.txt","a")) == NULL){printf("���� log.txt �� �����������\n");exit(1);}
			}	
		
			generate_battle(x2, y2, x1, y1);
		
			//out.close();
			//system("sh my.sh"); //��������� �������
			
		
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
	
}	









