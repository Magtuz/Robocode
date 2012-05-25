package EMK;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import robocode.*;
import robocode.util.Utils;
public class Predator extends AdvancedRobot {
	private double x, y, ahead, angl, wall, dh, a, GH, vekt, dist, e, E;
	static class Event{
	    double x, y, ahead, angl, wall, dh, a, GH, vekt, dist, e, E;
		Event(double x, double y, double ahead, double angl, double wall, double dh, double a, double GH, double vekt, double dist, double e, double E) {
			this.x=x;
			this.y=y;
			this.ahead=ahead;
			this.angl=angl;
			this.wall=wall;
			this.dh=dh;
			this.a=a;
			this.GH=GH;
			this.vekt=vekt;
			this.dist=dist;
			this.e=e;
			this.E=E;
		}
	}
	
	
	static class Tree {
		static class Node {
			  int value;
			  Node left;
			  Node right;
		    public Node(int value) {
		      this.value = value;
		    }
		  }

		  public static void insert(Node node, int value) {	//чем меньше число, тем выше приоритет операции
			 if(node == null || node.value == 0)
			 {
				 node = new Node(value);
				 return;
			 }else
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

		  public static double assembly_fun(Node node) {
		    if (node != null) {
		      	switch(node.value){
				case 0:
					return ((assembly_fun(node.left))*(assembly_fun(node.right)));
				case 1:
					return ((assembly_fun(node.left))/(assembly_fun(node.right)));
				case 2:
					return ((assembly_fun(node.left))+(assembly_fun(node.right)));
				case 3:
					return ((assembly_fun(node.left))+(assembly_fun(node.right)));
				case 4:
					return (Math.sqrt(assembly_fun(node.left)));							//корень
				case 5:
					return (Math.log(assembly_fun(node.left)));								//натуральный логарифм
				case 6:
					return ((assembly_fun(node.left))*(-1));								//значение с минусом
				case 7:
					return (Math.min(assembly_fun(node.left),assembly_fun(node.right)));	//минималььное из двух
				case 8:
					return (Math.max(assembly_fun(node.left),assembly_fun(node.right)));	//максимальное из двух	
				case 9:
					return (Math.atan(assembly_fun(node.left)));							//значение тангенса в радианах
				case 10:
					return (Math.exp(assembly_fun(node.left)));
			}
			switch(node.value){
				case 11:
					return event.x;
				case 12:
					return event.y;
				case 13:
					return event.wall;
				case 14:
					return event.vekt;
				case 15:
					return event.e;
				case 16:
					return event.dh;
				case 17:
					return event.dist;
				case 18:
					return event.angl;
				case 19:
					return event.GH;
				case 20:
					return event.ahead;
				case 21:
					return event.E;
				case 22:
					return Math.E ;
				case 23:
					return Math.PI;
				case 24:
					return -0.5 ;
				case 25:
					return -1 ;
				case 26:
					return 0.5;
				case 27:
					return 1;
				case 28:
					return 2;
				case 29:
					return 0;
			}	
								
		}
			return 0;
	}	      	
		      	
		      	
		      	
		       	
	}
	
	private double ShotAngle; 
    private double ShotEnergy;
    private double MoveAngle;
    private double MoveDistance;
    private Tree.Node tree_sa;
    private Tree.Node tree_ma;
    private Tree.Node tree_se;
    private Tree.Node tree_md;
    public static	Event event;
    
    public void run() {
    	
    	int len=30;
    	int[] fun_shotangle= new int[len];
    	int[] fun_moveangle = new int[len];
    	int[] fun_shotenergy = new int[len];
    	int[] fun_movedistance = new int[len];
		    try {
		    	//открываем хромосомы
		        BufferedReader read = new BufferedReader(new FileReader("C:\\Users\\Admin.Admin-ПК\\workspace\\Predator\\bin\\EMK\\base\\chromos.bs"));

				//считываем четыре строки
				String[] str1 = read.readLine().split(" ");
				String[] str2 = read.readLine().split(" ");
				String[] str3 = read.readLine().split(" ");
				String[] str4 = read.readLine().split(" ");
				System.out.println(str1);
				for(int i = 0; i < len; i++){						//конвертируем в массив чисел
					fun_shotangle[i] = Integer.parseInt(str1[i]);
					fun_moveangle[i] = Integer.parseInt(str2[i]);
					fun_shotenergy[i] = Integer.parseInt(str3[i]);
					fun_movedistance[i] = Integer.parseInt(str4[i]);
				}

		    } 
		    catch (IOException exc) {
		    	System.out.println("Ошибка считывания");
		    }
		    catch (NumberFormatException exc) {
		    	
		    	System.out.println("Ошибка! Считано не число");
			}			
		    tree_sa=new Tree.Node(fun_shotangle[0]);
		    tree_ma=new Tree.Node(fun_moveangle[0]);
		    tree_se=new Tree.Node(fun_shotenergy[0]);
		    tree_md=new Tree.Node(fun_movedistance[0]);
		    for(int i = 1; i < len; i++){						//сборка дерева
		    	Tree.insert(tree_sa,fun_shotangle[i]); 
		 		Tree.insert(tree_ma,fun_moveangle[i]);
		 		Tree.insert(tree_se,fun_shotenergy[i]);
		 		Tree.insert(tree_md,fun_movedistance[i]);
			}
		    
		while(true) {
			

			setTurnRadarRightRadians(2*Math.PI);
			ahead = getDistanceRemaining();
            angl = getTurnRemainingRadians();
			wall = Wall_dist();
			GH = getGunHeadingRadians();
            E = getEnergy();

			event=new Event(x, y, ahead, angl, wall, dh, a, GH, vekt, dist, e, E);
			
			ShotAngle = Tree.assembly_fun(tree_sa);
			ShotEnergy = Tree.assembly_fun(tree_ma);
			MoveAngle = Tree.assembly_fun(tree_se);
			MoveDistance = Tree.assembly_fun(tree_md);
			setTurnGunRightRadians(ShotAngle);
			if (getGunHeat() == 0 && ShotEnergy > 0) {
				setFire(ShotEnergy);
			}
			setAhead(MoveDistance);
			setTurnRight(MoveAngle);
			execute();
		
        }
    }


	
	private double Wall_dist() {
        return Math.min(Math.min( getX(),getBattleFieldWidth() - getX()),Math.min(getY(),getBattleFieldHeight() - getY()));
    }
	public void onScannedRobot(ScannedRobotEvent event) {	
				a = Utils.normalRelativeAngle(getHeadingRadians() + event.getBearingRadians());
				e = event.getEnergy();
		        dist = event.getDistance();
		        x = dist * Math.sin(a);
		        y = dist * Math.cos(a);
		        vekt = event.getHeadingRadians();
		       	dh=-Utils.normalRelativeAngle(getGunHeadingRadians()-a);

		    }

		    public void onHitByWall(HitByBulletEvent e) {
		        turnLeft(180);
		    }
	
}	