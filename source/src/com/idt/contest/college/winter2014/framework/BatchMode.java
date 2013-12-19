package com.idt.contest.college.winter2014.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that represents the batch processing mode of this application. Batch
 * mode is the mode that allows you to supply an argument at runtime describing
 * a data file path, which describes which classes and methods to execute as a script
 */
public class BatchMode extends Mode {

	
	/**
	 * member variable to hold file path of the batch script file
	 */
	String filepath;
	
	
	/**
	 * public constructor for BatchMode
	 */
	public BatchMode(String _filepath) {
		super();
		this.filepath = _filepath;
	}
	
	
	/**
	 * Method to execute batch mode execution
	 */
	public void execute() {

		try {
			File fileToRead = new File(this.filepath);
			this.scanner = new Scanner(fileToRead);
			
			while (this.scanner.hasNextLine()) {	
				processLine(this.scanner.nextLine());
			}

		} catch (FileNotFoundException e) {
			System.out.println(FrameworkConstants.FILE_NOT_FOUND_EXCEPTION);
		}
	}
	
	
	/**
	 * Method that processes a single line from the batch script
	 * @param line - String line of text parsed from the batch script
	 */
	private void processLine(String line) {
		String className, methodName;
		String[] parameterValues;
		String[] lineContents = line.split(FrameworkConstants.COMMA_DELIMITER);
		 
		if (lineContents.length >= FrameworkConstants.PARAMETER_INDEX) {
				
			className = lineContents[FrameworkConstants.CLASS_INDEX];
			methodName = lineContents[FrameworkConstants.METHOD_INDEX];
			
			if (lineContents.length > FrameworkConstants.PARAMETER_INDEX) {
				parameterValues = new String[lineContents.length - FrameworkConstants.PARAMETER_INDEX];
				for (int i = FrameworkConstants.PARAMETER_INDEX; i < lineContents.length; i++) {
					parameterValues[i - FrameworkConstants.PARAMETER_INDEX] = lineContents[i];
				}
				
			} else {
				parameterValues = new String[0];
			}
			
			// by now we should have Class, Method, and Parameters (if there are any)
			invokeClassMethod(className, methodName, parameterValues);
	
		} else {
			// the line contents array does not have enough fields to contain even
			// the two basic fields:  class and method
			System.out.println(FrameworkConstants.FILE_NOT_FOUND_EXCEPTION);
		}
	}
	
	
	/**
	 * Method to invoke a particular method on a particular class with particular parameter values
	 * @param className - String representation of the class 
	 * @param methodName - String representation of the method
	 * @param parameterValues - String array representation of the parameter values
	 */
	private void invokeClassMethod(String className, String methodName, String[] parameterValues) {
		Class<?> clazz = null;
		Object result = "no result";
		Class<?>[] parameterTypes = null;
		List<Object> parameterList = new ArrayList<Object>();
		Method selectedMethod = null;

		try {
			clazz = Class.forName(className);
			
			Method[] allMethods = clazz.getDeclaredMethods();
			for (Method m : allMethods) {
				if (m != null && m.getName().equals(methodName)) {
					selectedMethod = m;
				}
			}
			
			if (selectedMethod != null) {
				parameterTypes = selectedMethod.getParameterTypes();
			
				int index = 0;
				for (Class<?> paramType: parameterTypes) {
					parameterList.add(classCaster.cast(paramType, parameterValues[index++]));
				}
				
				Object classInstance = clazz.newInstance();
				System.out.print("INVOKE: " + className + "/" + methodName);
				if (parameterList.size() > 0) {
					System.out.print(" with parameter values [");
					for (int paramIndex = 0; paramIndex < parameterList.size(); paramIndex++) {
						if (paramIndex > 0)
							System.out.print(",");
						System.out.print(parameterList.get(paramIndex));
					}
					System.out.print("]\n");
				}
				result = selectedMethod.invoke(classInstance,  parameterList.toArray());
						
				if (this.isArray(selectedMethod.getReturnType())) {
					this.printArray(result);
				} else { 
					System.out.println("\nRESULT: " + result + "\n");
				}
					
			} else {
				System.out.println(FrameworkConstants.METHOD_SELECTION_ERROR);
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
		}
	}
	
}
