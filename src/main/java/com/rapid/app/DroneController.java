package com.rapid.app;

import com.rapid.exception.ServiceException;
import com.rapid.model.Direction;
import com.rapid.model.Drone;
import com.rapid.model.DroneStatus;
import com.rapid.model.Engine;
import com.rapid.model.EngineStatus;
import com.rapid.model.Gyroscope;
import com.rapid.model.OrientationSensor;

public class DroneController {

	public static void main(String[] args) throws ServiceException {
		Engine[] engines = new Engine[4];
	
		//1) Initially the drone is at rest. the power indicator shows all the engines have maximum power
		//Initializing an array of 4 engines powered on with maximum power as shown on the indicator
		engines[0] = new Engine(100, EngineStatus.ON);
		engines[1] = new Engine(100, EngineStatus.ON);
		engines[2] = new Engine(100, EngineStatus.ON);
		engines[3] = new Engine(100, EngineStatus.ON); 
		
		//Creating an orientation sensor object, setting pitch, left and right roll to 0 initially
		OrientationSensor orientationSensor = new OrientationSensor(0.0, 0.00, 0.00);
		
		//Creating a gyroscope object, setting xVel, yVel and zVelo to 0.0 initially
		Gyroscope gyroscope = new Gyroscope(0.00, 0.00, 0.00);
		
		//Initially the drone is off
		DroneStatus droneStatus = DroneStatus.OFF;  //Assuming drone is at rest but all engines are started
		
		//Creating drone object
		Drone drone = new Drone(engines, orientationSensor, gyroscope, droneStatus);
		
		//2) DRONE FAILS TO TAKE OFF when drone is off
		orientationSensor.setPitch(0.5);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(0.00);
		gyroscope.setzVelocity(5);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.OFF);
		
		
		try {
			drone.takeOff();
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		
		//3) DRONE FAILS TO TAKE OFF when one or more engines are down
		orientationSensor.setPitch(0.5);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(0.00);
		gyroscope.setzVelocity(5);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
		
		
		
		engines[0] = new Engine(100, EngineStatus.ON);
		engines[1] = new Engine(100, EngineStatus.OFF);
		engines[2] = new Engine(100, EngineStatus.ON);
		engines[3] = new Engine(100, EngineStatus.ON); 
		
		drone.setEngines(engines);
		
		
		try {
			drone.takeOff();
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
				
		
				
		//4) Probability of a crash when one or more engines are down		
		System.out.println("Crash probability: " + drone.getCrashProbability());
		
		//5) TAKE OFF SUCCESS
		orientationSensor.setPitch(0.5);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(0.00);
		gyroscope.setzVelocity(5);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
		
		engines[0] = new Engine(100, EngineStatus.ON);
		engines[1] = new Engine(100, EngineStatus.ON);
		engines[2] = new Engine(100, EngineStatus.ON);
		engines[3] = new Engine(100, EngineStatus.ON); 
		
		drone.setEngines(engines);
		
		
		try {
			drone.takeOff();
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		
		//6) MOVE FORWARD
		orientationSensor.setPitch(0.0);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(5);
		gyroscope.setzVelocity(5);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
		
		try {
			drone.move(Direction.FORWARD);
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			
			drone.land();
		}
		
		//6) MOVE FORWARD fails as one or more engine's power is below threshold
		orientationSensor.setPitch(0.0);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(5);
		gyroscope.setzVelocity(5);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
		
		engines[0] = new Engine(100, EngineStatus.ON);
		engines[1] = new Engine(15, EngineStatus.ON);
		engines[2] = new Engine(100, EngineStatus.ON);
		engines[3] = new Engine(100, EngineStatus.ON); 
		
		try {
			drone.move(Direction.FORWARD);
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			drone.land();
		}
		
		//7) MOVE BACK
		orientationSensor.setPitch(0.0);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(5);
		gyroscope.setzVelocity(0.0);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
		
		try {
			drone.move(Direction.BACK);
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			
			drone.land();
		}
				
		//8) MOVE LEFT
		orientationSensor.setPitch(0.0);
		orientationSensor.setRollLeft(0.3);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(5);
		gyroscope.setzVelocity(0.0);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
		
		try {
			drone.move(Direction.LEFT);
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			
			drone.land();
		}
		
		//9) MOVE RIGHT
		orientationSensor.setPitch(0.0);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.3);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(5);
		gyroscope.setzVelocity(0.0);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
		
		try {
			drone.move(Direction.RIGHT);
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			
			drone.land();
		}
		
		//10) MOVE UP
		orientationSensor.setPitch(0.4);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(5);
		gyroscope.setzVelocity(2.0);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
		
		try {
			drone.move(Direction.UP);
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			
			drone.land();
		}
		
		//11) MOVE DOWN
		orientationSensor.setPitch(0.4);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(10);
		gyroscope.setyVelocity(5);
		gyroscope.setzVelocity(2.0);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
		
		try {
			drone.move(Direction.DOWN);
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			
			drone.land();
		}
				
				
		//12) STABILIZE (HOVER)
		orientationSensor.setPitch(0.0);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(0.0);
		gyroscope.setyVelocity(0.0);
		gyroscope.setzVelocity(0.0);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.HOVERING);
	
		
		try {
			drone.stabilize();
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			
			drone.land();
		}
		
		//13) TAP with hand
		
		
		try {
			drone.tap();
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			
			drone.land();
		}
		
		//13) LAND
		orientationSensor.setPitch(0.0);
		orientationSensor.setRollLeft(0.0);
		orientationSensor.setRollRight(0.0);
		gyroscope.setxVelocity(0.0);
		gyroscope.setyVelocity(0.0);
		gyroscope.setzVelocity(2.0);
		
		drone.setOrientationSensor(orientationSensor);
		drone.setGyroscope(gyroscope);
		drone.setDroneStatus(DroneStatus.MOVE);
	
		
		try {
			drone.land();
			System.out.println("***** Drone's Data *****\n" + drone.toString());
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		
		//15) STATUS
		
		System.out.println("***** Drone's Status *****"  );
		
		System.out.println("status: " + drone.status());
		
		
		

	}
}
