package com.toys.wholesaletoyservice.rmi.clientengine;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.toys.wholesaletoyservice.compute.Compute;
import com.toys.wholesaletoyservice.rmi.clienttasks.AddToyPrice;
import com.toys.wholesaletoyservice.rmi.clienttasks.CalculateCost;
import com.toys.wholesaletoyservice.rmi.clienttasks.CalculateToyCost;
import com.toys.wholesaletoyservice.rmi.clienttasks.DeleteToyPrice;
import com.toys.wholesaletoyservice.rmi.clienttasks.UpdateToyPrice;

/**
 * 
 * @author user
 * 
 * ToyComputeTaskRegistry
 * this class:
 * 	1. looks for the toy compute engine
 * 	2. creates a client task
 * 	3. runs the client task on the toy compute engine
 */
public class ToyComputeTaskRegistry {
	public static void main(String args[]) {
		if (System.getSecurityManager() == null) {
//			System.setProperty("java.security.policy", "C:\\Users\\wgicheru\\eclipse-workspace\\wholesaletoyservice\\security.policy");
			String securityproperty = System.getProperty("java.security.policy");
			System.out.println("adding new security manager "+securityproperty);
			System.setSecurityManager(new SecurityManager());
		}
		/**
		 * invoke a method to allow interaction with the client tasks, also set
		 * the values to be used in client tasks
		 */

		try {
			// name of toy compute engine server
			String name = "Compute";
			Registry registry = LocateRegistry.getRegistry(1099);
			Compute comp = (Compute) registry.lookup(name);
			String taskname = "printreceipt";
			String taskjob = "12 toys";
			String taskresult = "";
			switch (taskname) {
			case "addtoy":
				AddToyPrice newtoyentity = new AddToyPrice(taskjob);
				taskresult = comp.executeTask(newtoyentity);
				break;
			case "getCost":
				CalculateToyCost totaltoycost = new CalculateToyCost(taskjob);
				taskresult = comp.executeTask(totaltoycost);
				break;
			case "printreceipt":
				CalculateCost purchasereceipt = new CalculateCost(taskjob);
				taskresult = comp.executeTask(purchasereceipt);
				break;
			case "deletetoy":
				DeleteToyPrice removetoyentity = new DeleteToyPrice(taskjob);
				taskresult = comp.executeTask(removetoyentity);
				break;
			case "updateprice":
				UpdateToyPrice updatetoyentity = new UpdateToyPrice(taskjob);
				taskresult = comp.executeTask(updatetoyentity);
				break;
			default:
				taskresult = "invalid selection";
				break;
			}
			System.out.println("Result from the server "+taskresult);
		} catch (Exception e) {
			System.err.println("Error sending client task");
			e.printStackTrace();
		}
	}
	

}
