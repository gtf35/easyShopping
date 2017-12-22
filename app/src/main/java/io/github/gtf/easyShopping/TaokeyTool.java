package io.github.gtf.easyShopping;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TaokeyTool
{
	public static String getTaoKeyTitle(String taoKey)
	{
		
		//taoKey = "【美沫艾莫尔白玫瑰分体纯露免洗面膜 补水保湿提亮肤色 睡眠面贴膜】http://v.cvz5.com/h.EDtTvK 点击链接，再选择浏览器打开；或复制这条信息￥ZzGT0hLFkRC￥后打开👉手淘👈";
		double textLong1 =getLength(taoKey);
		int textLong = (int)textLong1;
		if (textLong < 1)
		{
			textLong = 1;
			taoKey = "To fix a bug";
		}
		String[] tempArray = new String[textLong];
		int i = 0;
		while (i <= textLong - 1)
		{
			tempArray[i] = taoKey.substring(i, i + 1);
			i = i + 1;
		}
		int start = 0;
		int end = 0;
		String finallyString = "";
		int time = 0 ;
		while (time < textLong)
		{
			String tempText = tempArray[time];
			if (tempText.contains("【"))
			{
				start = time + 1;
			}
			if (tempText.contains("】"))
			{
				end = time - 1;
			}
			time = time + 1;
		}

		int a = start ;

		while (a <= end)
		{
			finallyString = finallyString + tempArray[a];
			a = a + 1;
		}
		//System.out.println(finallyString);
		return finallyString;
	}

	public static String getUrl(String key)
	{
		String finallyString = "没有呢";
		Pattern _pattern = Pattern.compile("(http://.*)\\s+");
		Matcher _match   = _pattern.matcher(key);
		if(_match.find()){
			System.out.println(_match.group());
			finallyString = _match.group();
		}

		return finallyString;
	}

	public static String getTaoKeyUrl(String taoKey)
	{
		//taoKey = "【美沫艾莫尔白玫瑰分体纯露免洗面膜 补水保湿提亮肤色 睡眠面贴膜】http://v.cvz5.com/h.EDtTvK 点击链接，再选择浏览器打开；或复制这条信息￥ZzGT0hLFkRC￥后打开👉手淘👈";
		double textLong1 =getLength(taoKey);
		int textLong = (int)textLong1;
		if (textLong < 1)
		{
			textLong = 1;
			taoKey = "To fix a bug";
		}
		String[] tempArray = new String[textLong];
		int i = 0;
		while (i <= textLong - 1)
		{
			tempArray[i] = taoKey.substring(i, i + 1);
			i = i + 1;
		}
		int start = 0;
		int end = 0;
		String finallyString = "";
		int time = 0 ;
		while (time < textLong)
		{
			String tempText = tempArray[time];
			if (tempText.contains("】"))
			{
				start = time + 1;
			}
			if (tempText.contains("点"))
			{
				end = time - 2;
			}
			time = time + 1;
		}

		int a = start ;

		while (a <= end)
		{
			finallyString = finallyString + tempArray[a];
			a = a + 1;
		}
		//System.out.println(finallyString);
		return finallyString;
	}

	public static boolean isLetter(char c)
	{ 
        int k = 0x80; 
        return c / k == 0 ? true : false; 
    }

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str)
	{
		if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("null"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/** 
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1 
     * @param String s 需要得到长度的字符串 
     * @return int 得到的字符串长度 
     */ 
    public static int length(String s)
	{
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++)
		{
            len++;
            if (!isLetter(c[i]))
			{
                len++;
            }
        }
        return len;
    }


    /** 
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5 
     * @param String s 需要得到长度的字符串 
     * @return int 得到的字符串长度 
     */ 
    public static double getLength(String s)
	{
    	double valueLength = 0;  
        String chinese = "[\u4e00-\u9fa5]";  
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1  
        for (int i = 0; i < s.length(); i++)
		{  
            // 获取一个字符  
            String temp = s.substring(i, i + 1);  
            // 判断是否为中文字符  
            if (temp.matches(chinese))
			{  
                // 中文字符长度为1  
                valueLength += 1;  
            }
			else
			{  
                // 其他字符长度为0.5  
                valueLength += 1;  
            }  
        }  
        //进位取整  
        return  Math.ceil(valueLength);  
    }

	public static boolean ifChinese(String s){
		String chinese = "[\u4e00-\u9fa5]";
		boolean a = false;
		for (int i = 0; i < s.length(); i++)
		{  
			// 获取一个字符  
			String temp = s.substring(i, i + 1);  
			// 判断是否为中文字符  
			if (temp.matches(chinese))
			{  
				a = true;
			}
			else
			{  
				a = false;
			} 
		} 
		return a;
	}


}


