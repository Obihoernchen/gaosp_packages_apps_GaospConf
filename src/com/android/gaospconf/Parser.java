package com.android.gaospconf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
	private static final String REGEX = "^\\s*([\\w\\.]+)\\s*=\\s*(\\w+)\\s*$";
	private static final Matcher MATCHER = Pattern.compile(REGEX).matcher("");

	public static boolean parse(String setting, String[] result)
	{
		final Matcher match = MATCHER;
		match.reset(setting);
		if(match.matches())
		{
			int count = match.groupCount();
			for(int i = 0; i < count + 1; i++)
			{
				result[i] = match.group(i);
			}
			return true;
		}
		return false;
	}
}