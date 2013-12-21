package com.idt.contest.college.winter2014.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Class that represents the menu mode of this application. Menu mode is
 * a traditional command line menu loop that displays menu options and allows 
 * a user to enter input multiple times until the user exists the application.
 */
public class MenuMode extends Mode{
	
	/**
	 * public constructor for MenuMode
	 */
	public MenuMode() {
		super();
	}
	
	/**
	 * Method that starts menu mode and prompts user for input
	 * in order to drive each method that has been contributed.
	 */
	public void execute() {
		
		String userInput;
		int convertedInput;
		scanner = new Scanner(System.in);	
		
		// the top level menu is the class menu, where a user selects a class
		// menu loop to show menu multiple times until user exits application
		boolean showMenu = true;
		while (showMenu) {
			
			// show the actual class menu options
			displayClassMenu();
			
			// request user input
			userInput = scanner.nextLine();
			
			try {
			
				// convert the users input to an integer
				convertedInput = Integer.parseInt(userInput);
								
				if (convertedInput > this.classNameMap.size() || convertedInput < 0) {	
					// the number doesn't correspond with menu options, display error
					System.out.println(FrameworkConstants.INTEGER_INPUT_ERROR);
				}
							
				if (convertedInput == FrameworkConstants.EXIT_CODE) {
					// user entered the EXIT_CODE, exit the application
					System.out.println("\nexiting...");
					showMenu = false;
				} else {
					// user entered a legitimate menu option, process menu
					processClassMenuOption(convertedInput);
				}
				
			} catch (NumberFormatException nfe) {
				// user did not enter a valid integer, display error
				System.out.println(FrameworkConstants.INTEGER_INPUT_ERROR);
			}
		}
	}
	
	
	/**
	 * Method used to display class names top level menu 
	 */
	private void displayClassMenu() {
		System.out.println("\nSELECT A CLASS TO USE: ");
		for (Integer key: this.classNameMap.keySet()) {
			System.out.println(key + " - " + this.classNameMap.get(key));
		}
		System.out.print(FrameworkConstants.EXIT_CODE + " - Exit Application\n\n-> ");
	}
	
	
	/**
	 * Method used to display method names lower level menu
	 */
	private void displayMethodMenu() {
		Method currentMethod;
		System.out.println("\nSELECT A METHOD TO USE: ");
		for (Integer key: this.methodMap.keySet()) {
			currentMethod = this.methodMap.get(key);
			if (currentMethod != null) {
				System.out.println(key + " - " + this.methodMap.get(key).getName());
			}
		}
		System.out.print(FrameworkConstants.EXIT_CODE + " - Back to Class Menu\n\n-> ");
	}
	

	/**
	 * Method that accepts an integer menu option for a class name
	 * and shows the resulting submenu of method options
	 * @param option - integer representing commandline menu option
	 */
	private void processClassMenuOption(int option) {
		
		Class<?> clazz;
		String userInput;
		int convertedInput;
		
		try {
			clazz = Class.forName(this.classNameMap.get(option));
			//Object t = clazz.newInstance();
			buildMethodMap(clazz);
				
			boolean showMenu = true;
			while (showMenu) {
			
				displayMethodMenu();
				
				// request user input
				userInput = scanner.nextLine();
				
				try {
					// convert the users input to an integer
					convertedInput = Integer.parseInt(userInput);
									
					if (convertedInput > this.methodMap.size() || convertedInput < 0) {	
						// the number doesn't correspond with menu options, display error
						System.out.println(FrameworkConstants.INTEGER_INPUT_ERROR);
					}
								
					if (convertedInput == FrameworkConstants.EXIT_CODE) {
						// user entered the EXIT_CODE, return back to the class menu
						showMenu = false;
						
					} else {
						// user entered a legitimate menu option, process menu
						processMethodMenuOption(convertedInput, clazz);
					}
				} catch (NumberFormatException nfe) {
					// user did not enter a valid integer, display error
					System.out.println(FrameworkConstants.INTEGER_INPUT_ERROR);
				}
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println(FrameworkConstants.MENU_OPTION_FAILED + " - " + FrameworkConstants.CLASS_NOT_FOUND_EXCEPTION);
			System.out.println(e.getMessage()+"\n");
		} catch (ClassCastException e) {
			System.out.println(FrameworkConstants.MENU_OPTION_FAILED + " - " + FrameworkConstants.CLASS_CAST_EXCEPTION);
			System.out.println(e.getMessage()+"\n");
		} catch (InstantiationException e) {
			System.out.println(FrameworkConstants.MENU_OPTION_FAILED + " - " + FrameworkConstants.INSTANTIATION_EXCEPTION);
			System.out.println(e.getMessage()+"\n");
		} catch (IllegalAccessException e) {
			System.out.println(FrameworkConstants.MENU_OPTION_FAILED + " - " + FrameworkConstants.ILLEGAL_ACCESS_EXCEPTION);
			System.out.println(e.getMessage()+"\n");
		} catch (IllegalArgumentException e) {
			System.out.println(FrameworkConstants.MENU_OPTION_FAILED + " - " + FrameworkConstants.ILLEGAL_ARGUMENT_EXCEPTION);
			System.out.println(e.getMessage()+"\n");
		} catch (InvocationTargetException e) {
			System.out.println(FrameworkConstants.MENU_OPTION_FAILED + " - " + FrameworkConstants.INVOCATION_TARGET_EXCEPTION);
			System.out.println(e.getMessage()+"\n");
			e.printStackTrace(System.out);
		} catch (NullPointerException e) {}
		
	}
	
	
	/**
	 * Method that accepts an integer menu option for a class name
	 * and shows the resulting submenu of method options
	 * @param option - integer representing commandline menu option
	 * @param clazz - class selected in top level menu that contains methods of interest
	 * @throws ClassCastException - thrown when a class cast does not go as expected
	 * @throws InstantiationException - thrown when an error occurs during instantiation by reflection
	 * @throws IllegalAccessException - thrown when currently executing method does not have access to class definition
	 * @throws IllegalArgumentException - thrown when parameter or class name is inaccurate
	 * @throws InvocationTargetException - thrown when an invoked method or constructor generates an exception
	 */
	private void processMethodMenuOption(int option, Class<?> clazz) throws ClassCastException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String userInput;
		Object result = "no result";
		Method selectedMethod = this.methodMap.get(option);
		List<Object> parameterList = new ArrayList<Object>();
		Class<?>[] parameterTypes = selectedMethod.getParameterTypes();
		
		int order = 0;
		for (Class<?> paramType: parameterTypes) {
			
			if (paramType == int[].class) {
				System.out.println("\nENTER " + FrameworkConstants.ORDER[order++] + " PARAMETER OF TYPE " + FrameworkConstants.INT_ARRAY_INSTRUCTIONS);
			} else {
				System.out.println("\nENTER " + FrameworkConstants.ORDER[order++] + " PARAMETER OF TYPE " + paramType);
			}
			System.out.print("\n-> ");
			
			// request user input
			userInput = scanner.nextLine();
			parameterList.add(classCaster.cast(paramType, userInput));
		}
		
		Object classInstance = clazz.newInstance();
		result = selectedMethod.invoke(classInstance,  parameterList.toArray());
		
		if (this.isArray(selectedMethod.getReturnType())) {
			this.printArray(result);
		} else { 
			System.out.println("\nRESULT: " + result + "\n");
		}
	}

}
