import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import robocode.*;
public class Predator extends AdvancedRobot {
	
	
	
	static class Tree {
		  static class Node {
			  int value;
			  Node left;
			  Node right;
		    public Node(int value) {
		      this.value = value;
		    }
		  }

		  public void insert(Node node, int value) {	//чем меньше число, тем выше приоритет операции
		    if (value < node.value) {
		      if (node.left != null) {
		        insert(node.left, value);
		        
		      } else {
		        node.left = new Node(value);
		      }
		    } else if (value > node.value) {
		      if (node.right != null) {
		        insert(node.right, value);
		      } else {
		        node.right = new Node(value);
		      }
		    }
		  }

		  public void assembly_fun(Node node) {
		    if (node != null) {
		    	assembly_fun(node.left);
		      	assembly_fun(node.right);
		    }
		  }
	}
	
	private double ShotAngle; 
    private double ShotEnergy;
    private double MoveAngle;
    private double MoveDistance; 
    
    public void run() {
    	static int len=30;
    	static int[] fun_shotangle= new int[len];
    	static int[] fun_moveangle = new int[len];
    	static int[] fun_shotenergy = new int[len];
    	static int[] fun_movedistance = new int[len];
		    try {
		    	//открываем хромоомы
		        BufferedReader read = new BufferedReader(new FileReader(getDataFile("chromosome.bs")));

				//считываем четыре строки
				String[] str1 = read.readLine().split(" ");
				String[] str2 = read.readLine().split(" ");
				String[] str3 = read.readLine().split(" ");
				String[] str4 = read.readLine().split(" ");
			
				for(int i = 0; i < len; i++){						//конвертируем в массив чисел
					fun_shotangle[i] = Integer.parseInt(str1[i]);
					fun_moveangle[i] = Integer.parseInt(str2[i]);
					fun_shotenergy[i] = Integer.parseInt(str3[i]);
					fun_movedistance[i] = Integer.parseInt(str4[i]);
				}

		    } catch (IOException exc) {
		    	
		    }
		    
		    } catch (NumberFormatException exc) {
		    	
				
			}			
			
			
					
	}
		while(true) {
			

			setTurnRadarRightRadians(2*Math.PI);
		

			
		
        }
    }
}
