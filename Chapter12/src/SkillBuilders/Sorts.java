package SkillBuilders;

import java.util.ArrayList;

public class Sorts 
{
	public static void selectionSort(ArrayList<Double> items)
	{
		Double element1, element2;
		
		for(int index = 0; index < items.size(); index++)
		{
			for(int subIndex = index; subIndex < items.size(); subIndex++)
			{
				element1 = items.get(index);
				element2 = items.get(subIndex);
				
				if(element2.compareTo(element1) < 0)
				{
					items.set(index, element2);
					items.set(subIndex, element1);
				}
			}
		}
	}
	
}
