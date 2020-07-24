package com.rapid.model;

import java.util.Arrays;
import java.util.stream.IntStream;

import javax.sql.rowset.serial.SerialException;

import org.ietf.jgss.Oid;

import com.rapid.exception.ServiceException;

public class Drone {
	
	private double x;  //x-coordinate with respect to the a reference point
	private double y;  //y-coordinate with respect to the a reference point
	private double z;  //z-coordinate with respect to the a reference point
	
	private double pitch;  //value from 0 to 1 with 0 being no pitch and 1 being max pitch
	private double rollLeft;   //value from 0 to 1 with 0 being no roll and 1 being max roll
	private double rollRight;   //value from 0 to 1 with 0 being no roll and 1 being max roll
	
	
	private Engine[] engines; 
	private Gyroscope gyroscope;
	private OrientationSensor orientationSensor;
	private DroneStatus droneStatus;
	
	public Drone(Engine[] engines, OrientationSensor orientationSensor, Gyroscope gyroscope, DroneStatus droneStatus) {
	
		this.engines = engines;
		this.orientationSensor = orientationSensor;
		this.gyroscope = gyroscope;
		this.droneStatus = droneStatus;
		
	}
	
	
	
	//take the drone in the air
	public void takeOff() throws ServiceException {
		
		/*2) Take off
		 *  a) x-coordinate of the drone will increment by xVel provided by the gyroscope
		 *  b) y-coordinate of the drone will be 0
		 *  c) z-coordinate of the drone will increment by zVel provided by the gyroscope
		 *  d) pitch will increase during the take-off
		 *  f) roll is 0 during the take-off
		 * 
		 * 
		 * 
		 */
		
		System.out.println("\n*** Getting ready for take-off ***");
		
		if(droneStatus == DroneStatus.OFF) {
			throw new ServiceException("Problem taking off as Drone is off");
		} else if(enginesNotWorking() > 0) {
			throw new ServiceException("Problem taking off as one or more engines are down");
		} else if(enginesPoweredBelowThreshold() > 0) {
  			throw new ServiceException("Problem with drone as one or more engines indicating low power.");
  		} 
		
		this.setDroneStatus(DroneStatus.MOVE);
		x += gyroscope.getxVelocity();
		y += gyroscope.getyVelocity();
		z += gyroscope.getzVelocity();
		
		pitch = orientationSensor.getPitch();
		
	}
	
	public String status() {
		return this.droneStatus.value();
	}
	
	
	public void move(Direction direction)  throws ServiceException {
		switch (direction)
	    {
	      case FORWARD:
	    	  	System.out.println("\n*** Moving forward ***");
	    	  	/*  a) x-coordinate is increasing by xVel provided by the gyroscope
		   		 *  b) y-coordinate is increasing by yVel provided by the gyroscope
		   		 *  c) z-coordinate is constant
		   		 *  d) pitch is 0
		   		 *  f) roll is 0
		   		 */
	  		
		  		if(enginesNotWorking() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines are down. Preparing to land.");
		  		} else if(enginesPoweredBelowThreshold() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines indicating low power. Preparing to land.");
		  		}
		  		
		  		this.setDroneStatus(DroneStatus.MOVE);
		  		x += gyroscope.getxVelocity();
		  		y += gyroscope.getyVelocity();	
		  		pitch = orientationSensor.getPitch();
		  		
	    	  break;
	      case BACK:  
	    	  	System.out.println("\n*** Moving backward ***");
	    		/*  
	    		 *  a) x-coordinate is decreasing by xVel provided by the gyroscope
		   		 *  b) y-coordinate is decreasing by yVel provided by the gyroscope
		   		 *  c) z-coordinate is constant
		   		 *  d) pitch is 0
		   		 *  f) roll is 0
		   		 */
	    	  	
	    	  	if(enginesNotWorking() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines are down. Preparing to land.");
		  		} else if(enginesPoweredBelowThreshold() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines indicating low power. Preparing to land.");
		  		}
		  		
		  		this.setDroneStatus(DroneStatus.MOVE);
		  		x -= gyroscope.getxVelocity();
		  		y -= gyroscope.getyVelocity();
		  	
		  		
		  		pitch = orientationSensor.getPitch();
		  		
	          break;

	      case LEFT:
	    	  	System.out.println("\n*** Moving Left ***");
	    		
	    	  	/*  a) x-coordinate is increasing by xVel provided by the gyroscope
		   		 *  b) y-coordinate is increasing by yVel provided by the gyroscope
		   		 *  c) z-coordinate is constant
		   		 *  d) pitch is 0
		   		 *  f) roll to the left
		   		 */
	  		
	    	  	if(enginesNotWorking() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines are down. Preparing to land.");
		  		} else if(enginesPoweredBelowThreshold() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines indicating low power. Preparing to land.");
		  		}
		  		
		  		
		  		this.setDroneStatus(DroneStatus.MOVE);
		  		x += gyroscope.getxVelocity();
		  		y += gyroscope.getyVelocity();
		  		rollLeft += orientationSensor.getRollLeft();
		  		pitch = orientationSensor.getPitch();
	    	 
	    	  break;
	      case RIGHT:
	    	  	System.out.println("\n*** Moving Right ***");
	    	  	
	    		
	    	  	/*  a) x-coordinate is increasing by xVel provided by the gyroscope
		   		 *  b) y-coordinate is increasing by yVel provided by the gyroscope
		   		 *  c) z-coordinate is constant
		   		 *  d) pitch is 0
		   		 *  f) roll to the right
		   		 */
		  		
	    	  	if(enginesNotWorking() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines are down. Preparing to land.");
		  		} else if(enginesPoweredBelowThreshold() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines indicating low power. Preparing to land.");
		  		} 
		  		
		  		this.setDroneStatus(DroneStatus.MOVE);
		  		x += gyroscope.getxVelocity();
		  		y += gyroscope.getyVelocity();
		  		rollRight += orientationSensor.getRollRight();
		  		pitch = orientationSensor.getPitch();
		  		
	    	  break;
	    	  
	     
	      case UP:    
	    	  	System.out.println("\n*** Moving UP ***");
	    	  	
	    	  	/*  a) x-coordinate is increasing by xVel provided by the gyroscope
		   		 *  b) y-coordinate is increasing by yVel provided by the gyroscope
		   		 *  c) z-coordinate is constant
		   		 *  d) pitch is 0
		   		 *  f) roll to the right
		   		 */
		  		
	    	  	if(enginesNotWorking() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines are down. Preparing to land.");
		  		} else if(enginesPoweredBelowThreshold() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines indicating low power. Preparing to land.");
		  		}
		  		
		  		this.setDroneStatus(DroneStatus.MOVE);
		  		x += gyroscope.getxVelocity();
		  		z += gyroscope.getzVelocity();
		  		rollRight = orientationSensor.getRollRight();
		  		rollLeft = orientationSensor.getRollLeft();
		  		
		  		pitch = orientationSensor.getPitch();
	          break;

	      case DOWN:
	    	  System.out.println("\n*** Moving DOWN ***");
	    	  
	    		/*  a) x-coordinate is decreasing by xVel provided by the gyroscope
		   		 *  b) y-coordinate is constant 
		   		 *  c) z-coordinate is decreasing by xVel provided by the gyroscope
		   		 *  d) pitch is decreasing
		   		 *  f) roll left and right are 0
		   		 */
		  		
	    	  if(enginesNotWorking() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines are down. Preparing to land.");
		  		} else if(enginesPoweredBelowThreshold() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines indicating low power. Preparing to land.");
		  		} 
		  		
		  		this.setDroneStatus(DroneStatus.MOVE);
		  		x -= gyroscope.getxVelocity();
		  		z -= gyroscope.getzVelocity();
		  		
		  		
		  		rollRight = orientationSensor.getRollRight();
		  		rollLeft = orientationSensor.getRollLeft();
		  		
		  		pitch = orientationSensor.getPitch();
	          break;

	      default:        
	    	  System.out.println("\n*** Moving forward ***");
		  		
	    	  if(enginesNotWorking() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines are down. Preparing to land.");
		  		} else if(enginesPoweredBelowThreshold() > 0) {
		  			throw new ServiceException("Problem with drone as one or more engines indicating low power. Preparing to land.");
		  		} 
		  		
		  		this.setDroneStatus(DroneStatus.MOVE);
		  		x += gyroscope.getxVelocity();
		  		y += gyroscope.getyVelocity();	
		  		pitch = orientationSensor.getPitch();
		  		break;
	    	  
	    }
	}
	
	//makes the drone hover
	public void stabilize()  throws ServiceException {
		/* Stabilize
		 *  a) x-coordinate of the drone is unchanged as xVel provided by the gyroscope is 0
		 *  b) y-coordinate of the drone is unchanged as yVel provided by the gyroscope is 0
		 *  c) z-coordinate of the drone is unchanged as zVel provided by the gyroscope is 0
		 *  d) pitch is unchanged
		 *  f) roll is unchanged
		 * 
		 * 
		 * 
		 */
		
		System.out.println("\n*** Drone is Hovering ***");
		
		if(enginesNotWorking() > 0) {
			throw new ServiceException("Problem with as one or more engines are down. Preparing to land.");
		}
		
		//x, y, z coordinates unchanged
		
		pitch = orientationSensor.getPitch();
		rollLeft = orientationSensor.getRollLeft();
		rollRight = orientationSensor.getRollRight();
		
		
		this.setDroneStatus(DroneStatus.HOVERING);
		
		
	}
	
	//stabilize and goes down at reduced speed
	public void land()  throws ServiceException {
		/* Landing
		 *  a) x-coordinate of the drone is unchanged as xVel provided by the gyroscope is 0
		 *  b) y-coordinate of the drone is unchanged as yVel provided by the gyroscope is 0
		 *  c) z-coordinate of the drone decreases by zVel provided by the gyroscope until it becomes 0
		 *  d) pitch is unchanged
		 *  f) roll is unchanged
		 * 
		 * 
		 * 
		 */
		
		System.out.println("\n*** Getting ready for landing ***");
		
		if(enginesNotWorking() > 0) {
			throw new ServiceException("Problem landing as one or more engines are down");
		}
		
		stabilize();
		
		this.setDroneStatus(DroneStatus.OFF);
		//resetting all to zeros
		x = gyroscope.getxVelocity();
		y = gyroscope.getyVelocity();
		z -= gyroscope.getzVelocity();
		
		
		pitch = orientationSensor.getPitch();
		rollLeft = orientationSensor.getRollLeft();
		rollRight = orientationSensor.getRollRight();
		
	}
	
	//tap the drone
	public void tap()  throws ServiceException {
		/* Tap
		 *  a) Let say x-coordinate changes after the tap 
		 *  b) Let say y-coordinate changes after the tap as well
		 *  c) Let say z-coordinate decreases after the tap
		 *  d) pitch is changed
		 *  f) roll is changes
		 * 
		 * 
		 * 
		 */
		
		System.out.println("\n*** Drone has been tapped ***");
		
		if(enginesNotWorking() > 0) {
			throw new ServiceException("Problem with as one or more engines are down. Preparing to land.");
		}
		
		stabilize();
		
		this.setDroneStatus(DroneStatus.HOVERING);
		
		
	}
	
	
	public int enginesNotWorking() {
		long count = Arrays.stream(engines).filter(e -> e.getEngineStatus().equals(EngineStatus.OFF)).count();
		
		return Integer.valueOf(count + "");
	}
	
	public int enginesPoweredBelowThreshold() {
		long count = Arrays.stream(engines).filter(e -> e.getPower() < 20).count();
		
		return Integer.valueOf(count + "");
	}
	
	public double getCrashProbability() {
		
			long outOfOrder = Arrays.stream(engines).filter(e -> e.getEngineStatus().equals(EngineStatus.OFF)).count();
			
			Double countAsDouble = Double.valueOf(outOfOrder);
			Double lengthAsDouble = Double.valueOf(engines.length);
					
			double probability = (countAsDouble/lengthAsDouble) * 100 ;
			
			//System.out.println("probabiliy of crash: " + probability + " %");
		
			return probability;
		
	}
	
	
	
	public Engine[] getEngines() {
		return engines;
	}
	public void setEngines(Engine[] engines) {
		this.engines = engines;
	}
	public Gyroscope getGyroscope() {
		return gyroscope;
	}
	public void setGyroscope(Gyroscope gyroscope) {
		this.gyroscope = gyroscope;
	}
	public OrientationSensor getOrientationSensor() {
		return orientationSensor;
	}
	public void setOrientationSensor(OrientationSensor orientationSensor) {
		this.orientationSensor = orientationSensor;
	}
	public DroneStatus getDroneStatus() {
		return droneStatus;
	}
	public void setDroneStatus(DroneStatus droneStatus) {
		this.droneStatus = droneStatus;
	}
	
	
	//Returning the string representation of Drone object
	@Override
	public String toString() {
		return String.format("%s%n %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s%n%s%s%n%s%s%s%n",
				"Drone's Internal Coordinates:",
				"(x,y,z) -> (", x, ",", y, ",", z, ")",
				"\n(Pitch,RollLeft,RollRight) -> (", pitch, ",", rollLeft, ",", rollRight, ")",
				"\nGyroscope Data:", gyroscope.toString(),
				"Orientation Sensor Data:", orientationSensor.toString(), 
				"Drone Status:", droneStatus.value() 
				
				);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	public double getRollLeft() {
		return rollLeft;
	}

	public void setRollLeft(double rollLeft) {
		this.rollLeft = rollLeft;
	}

	public double getRollRight() {
		return rollRight;
	}

	public void setRollRight(double rollRight) {
		this.rollRight = rollRight;
	}

	
	
	
	
	
	

}
