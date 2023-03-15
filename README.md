# Operating-Systems-project


Interrupt handler
An interrupt handler, also known as an interrupt service routine or ISR, is a
piece of software or more specifically a callback function in an operating system or
more specifically in a device driver, whose execution is triggered by the reception
of an interrupt.
Asynchronous events:
(a) User presses on a key on the keyboard.
(b) Disk controller finishes reading data.


German International University
Faculty of Informatics and Computer Science
Dr. Alia El Bolock
Eng. Caroline Emad
Exception handler
An exception handler is a powerful abstraction that can be used to help manage er-
rors and support the construction of reliable operating systems. Using exceptions
to notify system components about exceptional conditions also reduces coupling
of error handling code and increases the modularity of the system.
Synchronous events:
(a) Requesting more heap
(b) Attempts integer division by zero
(c) Attempts to access privileged memory
Expectations
You are expected to have a method called generateRandomEvent() that randomly
picks out of an ENUM that contains the 5 events mentioned above.
For example: Exception B occurs if divide by zero makes it all the way to the
CPU/ALU, then an exception should be generated. That exception will end up
sending a kill signal to the user-level process.
Code flow of this case
1) generateRandomEvent returns exception B
2) A simulation of a process’s state is set to "terminated".
The simulation of a process here could be something as simple as a java class
that has all processes attributes you learned in class. Feel free to add other at-
tributes to the process (that was not mentioned in class) but actually exist in a
real operating system.





Event Action
User presses on a
key on the keyboard.
post the key value into a system
area near the bottom of memory.
Disk controller
finishes reading data.
Disk status is either busy or idle.
Set status accordingly,
Operation: Read/ Write.
Length of reading (in bits).
Requesting more heap Check if possible, then allocate if so.
Attempts integer
division by zero Kill process
Attempts to access
privileged memory Reallocate a new space in the memory.







Processes
Our simulated OS can create different processes and execute them, one at a time.
We have 2 main processes
Process A It should take input from the user: a filename. Then print the content
of this file on the screen.
Process B It should take two inputs from the user: a filename, and some data.
Then write the data to the file.



Program Syntax
For your programs you should use the following syntax:
• print: to print the output on the screen. Example: print x
• assign: to initialize a new variable and assign a value to it. Example: assign
x y, where x is the variable and y is the value assigned. The value could be
an integer number, or a string
• writeFile: to write data to a file. Example: writeFile x y, where x is the
filename and y is the data.
• readFile: to read data from a file. Example: readFile x, where x is the
filename




Expectations
Your Simulated OS should be able to read the provided programs and run them.





Semaphores
A semaphore is a variable used to control access to a common resource by multiple
processes in a concurrent system such as a multi-programming operating system
by using two atomic operations, wait and post that are used for process synchro-
nization and a variable indicating the availability of this resource.
Scheduling
A Scheduling Algorithm is an algorithm used to schedule processes to ensure that
all processes get a chance to execute. As mentioned in the lecture, there are many
different scheduling algorithms to schedule processes.




1.1 Expected Project Hierarchy
1.1.1 Class Operating System
• Shall contain all attributes necessary to handle semaphores and schedul-
ing.
• Shall contain our 4 system calls/ program syntax (you already imple-
mented in milestone one)
1. Each program is a method on its own.
2. No scanners except in assign(x,y)

• Shall contain createProcess() that initializes a new process and sets the
corresponding attributes of a process and BUT NOT start the process.
1. For example: Setting the process state to NEW.
• Shall contain a main method that creates processes A and B (you already
implemented in milestone one)
• Shall contain the methods for the scheduling algorithms.
1.1.2 Class Process
• Shall contain all necessary attributes for a process.
• Shall contain a constructor to initialize all these attributes
• Setters and Getters for all attributes.
• Shall contain the 2 methods from milestone one : Process A and B
For each process A and B: you shall include your semaphore methods to
protect all resources.
1.1.3 Your Enums
• Add all the Enums required for you to handle any of the aforementioned
requirements.
1.1.4 Your Semaphore Classes (multiple classes)
• Use practice assignment 4 as reference
1.2 Semaphores Requirements
You are required to implement 4 semaphores, one for each resource we have:
1. Reading data -> readFile(x)
2. Write into a file on the disk. -> writeFile(x,y)
3. Print data on the screen. -> print(x)
4. Take text input from the user. -> assign(x,y)

For an illustration, to print on the screen, there will be 2 semaphore methods
and a variable:-
1. semPrintWait: any process calls it whenever it wants to print something on
the screen to acquire the key of the resource.
2. semPrintPost: any process calls it whenever it finishes printing to release the
key of the resource.
Note: ONLY ONE process is allowed to use the resource at a time.
Use practice assignment 4 questions as a reference to implement the semaphores.
1.3 Scheduling Requirements
In this milestone, you are asked to create these three scheduling algorithms using
JAVA:
1. Round Robin: Each process is assigned a fixed time slot in a cyclic way.
Quantum is 2. (Has to be implemented using a queue)
2. Multi-level queuing scheduler Each process can have either low, medium,
high priority therefore there are three queues to handle each priority level.
Each queue is scheduled based on FCFS (based on arrival time).
Program Syntax
We need only THREE methods created, one for each scheduling algorithm, these
methods are to follow the following conventions:
• Method names: Scheduler_RR(), Scheduler_MLQS() and Scheduler_FCFS().
• Method parameters/inputs: NONE
• Method return value: NONE

2 processes (A and B) created and running without any clashes (with the help
of your semaphores) and they can be scheduled smoothly (using your scheduling
algorithms).
Include print statements everywhere in your project to trace what is happening
at any moment in the project while it is running.









