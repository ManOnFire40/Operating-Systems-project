//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Random;
//
//public class part1 {
//	 static Proccess p = new Proccess();
//	 static Object[] array = new Object[100];
//	 static int pointerKeyboard = 85; // from 85 to 100;
//	 static int pNonprivleged = 0;
//	 static int nonPrivleged = 10; // from 0 to 10;
//	 static int pPrivleged = 60;
//	 static int privleged = 84; // from 60 to 84;
//
//	 // at start ( from
//
//	public static Events generateRandomEvent() {
//		int r = (int) (Math.random() * 5);
//		Events[] arr = Events.values();
//		return arr[r];
//
//	}
//
//	public static void Exception_Interrupt_handler(Events e) {
//
//		DriveStatus status;
//		int r = (int) (Math.random() * 2);
//		if (r == 1) {
//			status = DriveStatus.busy;
//			p.setStatus(Status.Blocked);
//		}
//		else
//		{
//			status = DriveStatus.idle;
//			p.setStatus(Status.Running);
//		}
//
//		if(e.equals(Events.Attempts_integer_division_by_zero)) {
//			kill();
//		} else if (e.equals(Events.Disk_controller_finishes_reading_data)) {
//			if(status.equals(DriveStatus.busy)) {
//				status = DriveStatus.idle;
//				p.setStatus(Status.Blocked);
//			}
//			p.setStatus(Status.Ready);
//		} else if (e.equals(Events.Attempts_to_access_privileged_memory)) {
//			System.out.println("Allocating more space for you!");
//
//			int rand = (int) (Math.random() * 100);
//			if(rand + nonPrivleged >= privleged) {
//				System.out.println("No enough space!");
//			} else {
//				System.out.println("You have been allocated a new address space");
//				nonPrivleged = r + nonPrivleged;
//			}
//		} else if (e.equals(Events.Requesting_more_heap)) {
//			System.out.println("Checking for space for more heap!");
//
//			int rand = (int) (Math.random() * 100);
//			if(rand + nonPrivleged >= privleged) {
//				System.out.println("No enough space!");
//			} else {
//				nonPrivleged = rand + nonPrivleged;
//			}
//
//		} else {
//			System.out.println("Enter your input to save it in memory.");
//	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	        try {
//	        	if(pointerKeyboard > 100)
//	        		System.out.println("Couldn't Save your input, memory is full!");
//	        	else if(br.ready()) {
//					array[pointerKeyboard++] = br.readLine();
//				}
//			} catch (IOException e1) {
//				System.out.println("There was an error while saving data disk might be busy,  please try again later.");
//			}
//		}
//	}
//
//	public static void kill() {
//		p.setStatus(Status.Terminated);
//		System.out.println("Illegal operation, process terminated!");
//
//	}
//
//
//	public static void main (String[] args) {
//		Events e = generateRandomEvent();
//		Exception_Interrupt_handler(e);
//
//	}
//}
