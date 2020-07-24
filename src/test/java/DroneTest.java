
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.rapid.exception.ServiceException;
import com.rapid.model.Direction;
import com.rapid.model.Drone;
import com.rapid.model.DroneStatus;
import com.rapid.model.Engine;
import com.rapid.model.EngineStatus;
import com.rapid.model.Gyroscope;
import com.rapid.model.OrientationSensor;


public class DroneTest {
	@Mock
	Drone drone;
	
	
	Engine[] engines;
	
	public DroneTest() {
		
		
		engines = new Engine[4];
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
		drone = new Drone(engines, orientationSensor, gyroscope, droneStatus);
		
	}
	
	
	
	@Test
	public void whenServiceExceptionThrown_thenAssertionSucceeds() {
	    Exception exception = Assertions.assertThrows(ServiceException.class, () -> {
	    	
	    	engines[0] = new Engine(100, EngineStatus.OFF);
			engines[1] = new Engine(100, EngineStatus.ON);
			engines[2] = new Engine(100, EngineStatus.ON);
			engines[3] = new Engine(100, EngineStatus.ON); 
			
	    	drone = new Drone(engines, new OrientationSensor(0.5,  0.0, 0.0), 
					new Gyroscope(10.0, 0, 5.0), DroneStatus.MOVE);
			
			drone.takeOff();
	    });
	 
	    String expectedMessage = "Problem taking off as one or more engines are down";
	    String actualMessage = exception.getMessage();
	 
	    Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void whenServiceExceptionThrown2_thenAssertionSucceeds() {
	    Exception exception = Assertions.assertThrows(ServiceException.class, () -> {
	    	
	    	engines[0] = new Engine(100, EngineStatus.ON);
			engines[1] = new Engine(15, EngineStatus.ON);
			engines[2] = new Engine(100, EngineStatus.ON);
			engines[3] = new Engine(100, EngineStatus.ON); 
			
	    	drone = new Drone(engines, new OrientationSensor(0.5,  0.0, 0.0), 
					new Gyroscope(10.0, 0, 5.0), DroneStatus.MOVE);
			
			drone.takeOff();
	    });
	 
	    String expectedMessage = "Problem with drone as one or more engines indicating low power.";
	    String actualMessage = exception.getMessage();
	 
	    Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}
	
	
	@Test
	public void takeOffTest() throws Exception {
		drone = new Drone(engines, new OrientationSensor(0.5,  0.0, 0.0), 
				new Gyroscope(10.0, 0, 5.0), DroneStatus.MOVE);
		double x1 = drone.getX();
		double y1 = drone.getY();
		double z1 = drone.getZ();
		
		drone.takeOff();
		double x2 = drone.getX();
		double y2 = drone.getY();
		double z2 = drone.getZ();
		
		Assertions.assertEquals(x2, x1 + drone.getGyroscope().getxVelocity());
		Assertions.assertEquals(y2, y1 + drone.getGyroscope().getyVelocity());
		Assertions.assertEquals(z2, z1 + drone.getGyroscope().getzVelocity());
		
		
		
	}
	
	@Test
	public void crashProbabilityTest() throws Exception {
		
		engines[0] = new Engine(100, EngineStatus.ON);
		engines[1] = new Engine(100, EngineStatus.OFF);
		engines[2] = new Engine(100, EngineStatus.OFF);
		engines[3] = new Engine(100, EngineStatus.ON); 
		
		drone = new Drone(engines, new OrientationSensor(0.5,  0.0, 0.0), 
				new Gyroscope(10.0, 0, 5.0), DroneStatus.MOVE);
		
		
		double actualProb = drone.getCrashProbability();
		
		
		Assertions.assertEquals(50.0, actualProb);
		
		
		
	}
	
	@Test
	public void moveForwardTest() throws Exception {
		drone = new Drone(engines, new OrientationSensor(0.0,  0.0, 0.0), 
				new Gyroscope(10.0, 5.0, 5.0), DroneStatus.MOVE);
		double x1 = drone.getX();
		double y1 = drone.getY();
		double z1 = drone.getZ();
		double pitch1 = drone.getPitch();
		
		drone.move(Direction.FORWARD);
		double x2 = drone.getX();
		double y2 = drone.getY();
		double z2 = drone.getZ();
		double pitch2 = drone.getPitch();
		
		Assertions.assertEquals(x2, x1 + drone.getGyroscope().getxVelocity());
		Assertions.assertEquals(y2, y1 + drone.getGyroscope().getyVelocity());
		Assertions.assertEquals(z2, z1 );
		
		Assertions.assertEquals(pitch2, pitch1 );
		
		Assertions.assertEquals(DroneStatus.MOVE, drone.getDroneStatus());
		
		
		
	}
	
	
	@Test
	public void moveBackTest() throws Exception {
		drone = new Drone(engines, new OrientationSensor(0.0,  0.0, 0.0), 
				new Gyroscope(10.0, 5.0, 0), DroneStatus.MOVE);
		double x1 = drone.getX();
		double y1 = drone.getY();
		double z1 = drone.getZ();
		
		drone.move(Direction.BACK);
		double x2 = drone.getX();
		double y2 = drone.getY();
		double z2 = drone.getZ();
		
		Assertions.assertEquals(x2, x1 - drone.getGyroscope().getxVelocity());
		Assertions.assertEquals(y2, y1 - drone.getGyroscope().getyVelocity());
		Assertions.assertEquals(z2, z1 );
		
		Assertions.assertEquals(DroneStatus.MOVE, drone.getDroneStatus());
		
		
		
	}
	
	@Test
	public void moveLeftTest() throws Exception {
		drone = new Drone(engines, new OrientationSensor(0.0,  0.3, 0.0), 
				new Gyroscope(10.0, 5.0, 0), DroneStatus.MOVE);
		double x1 = drone.getX();
		double y1 = drone.getY();
		double z1 = drone.getZ();
		
		drone.move(Direction.LEFT);
		double x2 = drone.getX();
		double y2 = drone.getY();
		double z2 = drone.getZ();
		
		Assertions.assertEquals(x2, x1 + drone.getGyroscope().getxVelocity());
		Assertions.assertEquals(y2, y1 + drone.getGyroscope().getyVelocity());
		Assertions.assertEquals(z2, z1 );
		
		Assertions.assertEquals(DroneStatus.MOVE, drone.getDroneStatus());
		
		
		
	}
	
	@Test
	public void moveRightTest() throws Exception {
		drone = new Drone(engines, new OrientationSensor(0.0,  0.0, 0.3), 
				new Gyroscope(10.0, 5.0, 0), DroneStatus.MOVE);
		double x1 = drone.getX();
		double y1 = drone.getY();
		double z1 = drone.getZ();
		
		drone.move(Direction.RIGHT);
		double x2 = drone.getX();
		double y2 = drone.getY();
		double z2 = drone.getZ();
		
		Assertions.assertEquals(x2, x1 + drone.getGyroscope().getxVelocity());
		Assertions.assertEquals(y2, y1 + drone.getGyroscope().getyVelocity());
		Assertions.assertEquals(z2, z1 );
		
		Assertions.assertEquals(DroneStatus.MOVE, drone.getDroneStatus());
		
		
		
	}
	
	@Test
	public void moveUpTest() throws Exception {
		drone = new Drone(engines, new OrientationSensor(0.4,  0.0, 0.0), 
				new Gyroscope(10.0, 5.0, 2.0), DroneStatus.MOVE);
		double x1 = drone.getX();
		double y1 = drone.getY();
		double z1 = drone.getZ();
		
		drone.move(Direction.UP);
		double x2 = drone.getX();
		double y2 = drone.getY();
		double z2 = drone.getZ();
		
		Assertions.assertEquals(x2, x1 + drone.getGyroscope().getxVelocity());
		Assertions.assertEquals(y2, y1 );
		Assertions.assertEquals(z2, z1 +  drone.getGyroscope().getzVelocity() );
		
		Assertions.assertEquals(DroneStatus.MOVE, drone.getDroneStatus());
		
		
		
	}
	
	@Test
	public void moveDownTest() throws Exception {
		drone = new Drone(engines, new OrientationSensor(0.4,  0.0, 0.0), 
				new Gyroscope(10.0, 5.0, 2.0), DroneStatus.MOVE);
		double x1 = drone.getX();
		double y1 = drone.getY();
		double z1 = drone.getZ();
		
		drone.move(Direction.DOWN);
		double x2 = drone.getX();
		double y2 = drone.getY();
		double z2 = drone.getZ();
		
		Assertions.assertEquals(x2, x1 - drone.getGyroscope().getxVelocity());
		Assertions.assertEquals(y2, y1 );
		Assertions.assertEquals(z2, z1 -  drone.getGyroscope().getzVelocity() );
		
		Assertions.assertEquals(DroneStatus.MOVE, drone.getDroneStatus());
		
		
		
	}
	
	@Test
	public void stabilizeTest() throws Exception {
		drone = new Drone(engines, new OrientationSensor(0.0,  0.0, 0.0), 
				new Gyroscope(0.0, 0.0, 0.0), DroneStatus.MOVE);
		
		
		double pitch1 = drone.getPitch();
		double rollLeft1 = drone.getRollLeft();
		double rollRight1 = drone.getRollRight();
		
		drone.stabilize();
		double pitch2 = drone.getPitch();
		double rollLeft2 = drone.getRollLeft();
		double rollRight2 = drone.getRollRight();
		
		Assertions.assertEquals(pitch2, pitch1);
		Assertions.assertEquals(rollLeft2, rollLeft1 );
		Assertions.assertEquals(rollRight2, rollRight1 );
		
		Assertions.assertEquals(DroneStatus.HOVERING, drone.getDroneStatus());
		
		
		
	}
	
	@Test
	public void landTest() throws Exception {
		drone = new Drone(engines, new OrientationSensor(0.0,  0.0, 0.0), 
				new Gyroscope(3.0, 3.0, 3.0), DroneStatus.MOVE);
		
		
		double x1 = drone.getX();
		double y1 = drone.getY();
		double z1 = drone.getZ();
		
		drone.land();
		double x2 = drone.getX();
		double y2 = drone.getY();
		double z2 = drone.getZ();
		
		Assertions.assertEquals(x2, x1 + drone.getGyroscope().getxVelocity());
		Assertions.assertEquals(y2, y1 + drone.getGyroscope().getxVelocity() );
		Assertions.assertEquals(z2, z1 -  drone.getGyroscope().getzVelocity() );
		
		Assertions.assertEquals(DroneStatus.OFF, drone.getDroneStatus());
		
		
		
	}
	
	
	@Test
	void notNullTest() {
		Assertions.assertNotNull(drone);
	}
}
