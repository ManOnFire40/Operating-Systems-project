import java.io.*;
import java.util.*;

public class w3OS {
	private static int PID = 0;
	static Object[] mem = new Object[100];
	static int pointerKeyboard = 85; // from 85 to 100;
	static int pNonprivleged = 0;
	static int nonPrivleged = 10; // from 0 to 10;
	static int pPrivleged = 60;
	static int privleged = 84;
	private DriveStatus DStatus;
	static Wsemaphore writeFilesem = new Wsemaphore();
	static Asemaphore assign = new Asemaphore();
	static Psemaphore print = new Psemaphore();
	static Rsemaphore rf = new Rsemaphore();
	static int qt;
	static int f = 0;
	static int seconds = 0;

	//
	static Queue<Proccess> Proccesses = new LinkedList<>(); //Omar
	//idea of this queue that if the process is ready it would be
	//executed else if process is blocked it would be turned into
	//ready and placed at the rear of queue as it would be
	//executed after 1 cycle

	static Queue<Proccess> HighProccesses = new LinkedList<>();
	static Queue<Proccess> MediumProccesses = new LinkedList<>();
	static Queue<Proccess> LowProccesses = new LinkedList<>();

	//check priv ??
	public static Proccess createProcess() throws InterruptedException {
		return new Proccess(PID++, PID, Status.New, randPrio(),true);
	}
	public static Priority randPrio(){
		int r = (int) (Math.random()*3);
		Priority[] p = Priority.values();
		return p[r];
	}


	//counting semaphore Ehab
	private static Proccess search(int pid) {
		for (Proccess p : Proccesses) {
			if (p.getPID() == pid)
				return p;
		}
			return null;
		
	}
	static class Wsemaphore {
		int count;


		public Wsemaphore() {
			count = 1;


		}
		void semPrintWait(int pid) {
			Proccess p = search(pid);
			while (count != 1) {
				if(qt ==0 ) {
					p.setStatus(Status.Blocked);
					System.out.println("Process Id "+pid +" WriteFile_Sem Stuck");
					p.t.suspend();

				}
				qt--;
			}
			System.out.println("Process Id "+pid +" WriteFile_Sem ");
			count -- ;
		}
		// first you need to keep looping on the Wait semaphores till u manage to reach the ready state
		// second you need to handle if proc

		void semPrintPost() {
			System.out.println("OUT WRITE_FILE");
			count++;
		}
	}
	static class Asemaphore {
		int count;



		public Asemaphore() {
			count = 1;
		}

		void semPrintWait(int pid) {
			Proccess p = search(pid);
			while (count != 1) {
				if (qt == 0) {
					p.setStatus(Status.Blocked);
					System.out.println("Process Id "+pid +" Assign_Sem Stuck");
					p.t.suspend();
				}
				qt--;
			}
			System.out.println("Process Id "+pid +" Assign_Sem ");
			count--;

		}

		void semPrintPost() {
			count++;
			System.out.println("OUT Assign");
		}
	}
	static class Psemaphore {
		int count;

		public Psemaphore() {
			count = 1;
		}

		void semPrintWait(int pid) {
			Proccess p = search(pid);
			while (count != 1) {
				if (qt == 0) {
					p.setStatus(Status.Blocked);
					System.out.println("Process Id "+pid +" Print_Sem Stuck");
					p.t.suspend();
				}
				qt--;
			}
			System.out.println("Process Id "+pid +" Print_SEM");
			count--;

		}


		void semPrintPost() {
			System.out.println("OUT PRINT");
			count++;
		}
	}
	static class Rsemaphore {
		int count;

		public Rsemaphore() {
			count = 1;
		}

		void semPrintWait(int pid) throws InterruptedException {
			Proccess p = search(pid);
			while (count != 1) {
				if (qt == 0) {
					p.setStatus(Status.Blocked);
					System.out.println("Process Id "+pid +" Read_Sem Stuck");
					p.t.suspend();
				}
				qt--;
			}
			System.out.println("Process Id "+pid +" Read_Sem ");
			count--;

		}


		void semPrintPost() {
			count++;
		}
	}


	public static void find_var(String name , int pid) {
		for (int i = 0; i < pNonprivleged; i++) {
			if (mem[i] instanceof Pair) {
				if (((Pair) mem[i]).id == pid&&((Pair) mem[i]).first.equals(name)) {
					System.out.println("Remaining Time For Process =  "+qt);
					System.out.println(" Time For Process = "+seconds);
					System.out.println(((Pair) mem[i]).second + " Process_ID = "+pid);
				}
			}
		}
	}

//	public void Terminal() throws IOException {
//		boolean help = false;
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String[] addr = br.readLine().split(" ");
//
//		if (addr[0].equals("readFile")) {
//			readFile(addr[1]);
//		} else if (addr[0].equals("print")) {
//			print(addr[1]);
//		} else if (addr[0].equals("assign")) {
//			assign(addr[1], addr[2]);
//
//		} else if (addr[0].equals("writeFile")) {
//			writeFile(addr[1], addr[2]);
//
//		} else if (addr[0].equals("exit")) {
//			System.exit(0);
//		} else if (addr[0].equals("help")) {
//
//			System.out.println("Manual for using w3OS :D !\n");
//			System.out.println("1- type 'print x' to print content x variable.\n");
//			System.out.println("2- type 'assign x y ' to assign a specific value\n");
//			System.out.println("3- type 'readFile x' to read the data in  a specific file\n");
//			System.out.println("4- type 'writeFile x (data)' to write new  data in  a specific file\n");
//			System.out.println("0- type to exit the Terminal\n");
//		} else {
//			System.out.println("Command not identified, please rewrite the command. Type help for manual.");
////		}
//
//	}

	//Searching for the process after 2 tik
	public static void searchInQ(int pid) throws InterruptedException {
		Iterator<Proccess> it = Proccesses.iterator();
		Proccess p = null;
		while (it.hasNext()){
			 p = it.next();
			if(p.getPID()== pid){
				it.remove();
				p.setStatus(Status.Ready);
				Proccesses.add(p);
				Thread.sleep(1000);
				p.t.suspend();
				break;
			}
		}
		assert p != null;

	}

	public static void readFile(String file ,int pid) throws IOException, InterruptedException {
		rf.semPrintWait(pid);
		Proccess p = search(pid);
		p.setStatus(Status.Running);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String Line = assign("Line",br.readLine(),pid);
		while (Line!= null) {
			p.setStatus(Status.Running);
 			if(qt == 0 ) {f=1;searchInQ(pid);

			 }

			print("Line",pid);
			qt--;
			Line = assign("Line",br.readLine(),pid);
		}
		p.setStatus(Status.Finished);
		br.close();
		rf.semPrintPost();
	}

	public static void print(String variable , int pid ) throws IOException {
		print.semPrintWait(pid);
		find_var(variable,pid);
		print.semPrintPost();
	}
	public static String assign(String variable, String value,int pid) throws IOException {
		assign.semPrintWait(pid);
		Pair p = new Pair(pid,variable, value);
		boolean exists = false;
		for(int i = 0 ; i < pNonprivleged ; i++){
			 if(mem[i] instanceof Pair){
				 if(((Pair) mem[i]).id == pid &&Objects.equals(((Pair) mem[i]).first, variable)){
					exists =true;
					mem[i]=p;
				}
			}
		}
		if(!exists) mem[pNonprivleged++] = p;
		assign.semPrintPost();
		return value;
	}
	public static void writeFile(String fileName, String data, int pid) throws IOException {
		writeFilesem.semPrintWait(pid);
		Proccess p = search(pid);
		p.setStatus(Status.Running);
		try {
			BufferedWriter File = new BufferedWriter(new FileWriter(fileName, true));
			int chunkSize = 20;

			String[] chunks = data.split("(?<=\\G.{" + chunkSize + "})"); // we used regex to split after each 20 letter.
			System.out.println(Arrays.toString(chunks));
			for(int i = 0; i < chunks.length; i++){
				String Line = assign("Line",chunks[i],pid);
				File.write(Line);
				System.out.println(Line.length() + " characters written in file");
				print("Line",pid);
				if (qt == 0){
					searchInQ(pid);
				}
				qt --;
			}
			File.close();

		} catch (IOException e) {
			System.out.println("Error writing to the file \n" + e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		writeFilesem.semPrintPost();
	}




	// Scheduling algo
	// round robin - Omar

	public static void NewToReady(Proccess p) {
		p.setStatus(Status.Ready);
		f = 1;
	}

	public static void BlockedToReady(Proccess p) {
		p.setStatus(Status.Ready);
	}

	public static void Scheduler_RR() throws InterruptedException {
		/*
		 * we need to specify the time it runs for each proc and we will use a variable
		 * for that since it was stated in the milestone description that there's no
		 * input for the method
		 */
		 // for example.
		while (!Proccesses.isEmpty()) {
				f = 0;
				Proccess p = Proccesses.peek();
			qt = 2;
				if(p.getStatus() == Status.Finished){
					System.out.println("Finished " + p.getPID());
					Proccesses.poll();
				} else if (p.getStatus() == Status.Ready){
					Thread.sleep(1000);
					if(p.t.getState() == Thread.State.RUNNABLE){
						p.t.resume();
					} else {
						p.t.start();
					}
				} else if(p.getStatus() == Status.Blocked){
					p.t.resume();
				} else if(p.getStatus() == Status.New){
					NewToReady(p);
				}
				if(p.getStatus() != Status.Finished)
					Proccesses.add(Proccesses.poll());

				Thread.sleep(3000);

		}
			// we need to make sure that block statue will change to ready soon otherwise
			// we also need to make a call on the processes that will be new to set them to
			// ready.

		System.out.println("All proccesses finished. Please rerun the scheduler after running the new processes.");
	}
	//	 	mluti-level queuing. -Omar
	public static void Scheduler_MLQS() {
		qt =-1;
		Proccess k = Proccesses.peek();
		int a = 0;
		while (!Proccesses.isEmpty()) {
			Proccess p = Proccesses.poll();
			if(k == p && a != 0) {
				Proccesses.add(p);
				break;
			}
			a++;


			if (p.getPriority() == Priority.HIGH) {
				HighProccesses.add(p);
			} else if (p.getPriority() == Priority.MEDIUM)
				MediumProccesses.add(p);
			else {
				LowProccesses.add(p);
			}
			Proccesses.add(p);

		}
		System.out.println("Done adding processes to its belonging queue \n based on its priority.");
		while (!HighProccesses.isEmpty() || !MediumProccesses.isEmpty() || !LowProccesses.isEmpty()) {
			Proccess p;
			if (!HighProccesses.isEmpty())
			{
				p = HighProccesses.peek();

			} else if (!MediumProccesses.isEmpty())
			{
				p = MediumProccesses.peek();
			} else {
				p = LowProccesses.peek();
			}
			Scheduler_FCFS();

			while(p.getStatus() != Status.Finished){

			}

		}
	}

	public static  void Scheduler_FCFS () {
		qt = -1 ;
		Proccess p;
		if (!HighProccesses.isEmpty())
		{
			p = HighProccesses.poll();

		} else if (!MediumProccesses.isEmpty())
		{
			p = MediumProccesses.poll();
		} else {
			p = LowProccesses.poll();
		}
		p.t.start();
	};

	public static void main(String[] args) throws IOException, InterruptedException {
		Proccess b3 = createProcess();
		b3.ProcessB();
		Proccess b = createProcess();
		b.ProcessA();
		Proccess b2 = createProcess();
		b2.ProcessA();
		Scheduler_RR();
		//Scheduler_MLQS();

//		a.t.getState();
//		System.out.println(	a.t.getState());
//		System.out.println(	a.t.getState());
//		System.out.println(	a.t.getState());
//		System.out.println(	a.t.getState());
//		System.out.println(	a.t.getState());
//		System.out.println(	a.t.getState());
//
//
//		int n = 5;
//		while (n!=0){
//			System.out.println(n--);
//			Thread.sleep(1000);
//		}
//		a.t.resume();
	}
}
